package com.example.administrator.pengbonews.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.pengbonews.Bean.NewsSubject;
import com.example.administrator.pengbonews.R;
import com.example.administrator.pengbonews.adapter.WarAdapter;
import com.example.administrator.pengbonews.databinding.FragmentWarBinding;
import com.example.administrator.pengbonews.entity.TextNews;
import com.example.administrator.pengbonews.net.NewsLoader;
import com.example.administrator.pengbonews.net.NewsService;
import com.example.administrator.pengbonews.net.RetrofitServiceManager;
import com.example.administrator.pengbonews.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.view.fragment
 *  @文件名:   BaseNewsFragment
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/12 19:46
 *  @描述：    TODO
 */
public abstract class BaseNewsFragment extends Fragment{
    private static final String TAG = "BaseNewsFragment";
    private FragmentWarBinding mFragmentWarBinding;
    private RecyclerView warRecycler;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NewsService newsService;
    private NewsLoader newsLoader;
    private WarAdapter warAdapter;
    private LinearLayoutManager layoutManager;
    private List<NewsSubject.ListBean> list = new ArrayList<>();
    private Handler handler = new Handler();
    private boolean isLoading;
    private int page = 1;
    private List<TextNews> mTextNewses = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView(inflater, container);
        initData();
        return mFragmentWarBinding.getRoot();
    }

    protected abstract String getNewString();

    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    private boolean isNoNetWorkConnect(){
        NetworkInfo.State wifiState = null;
        NetworkInfo.State mobileState = null;

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context
                .CONNECTIVITY_SERVICE);
        wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

        //没有网络连接
        if(wifiState != null && mobileState != null
                && NetworkInfo.State.CONNECTED != wifiState
                && NetworkInfo.State.CONNECTED != mobileState){
            return true;
        }
        return false;
    }

    private void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getNetData(getNewString(),1,10);
            }
        }, 1500);
    }

    private void initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mFragmentWarBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_war, container, false);
        warRecycler = mFragmentWarBinding.warRecycler;
        swipeRefreshLayout = mFragmentWarBinding.warSwipe;
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        getNetData(getNewString(),1,10);
                    }
                },2000);
            }
        });


        //设置布局管理器
        layoutManager = new LinearLayoutManager(this.getActivity());
        warRecycler.setLayoutManager(layoutManager);
        //设为垂直布局
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置adapter
        //判断有无网络
        warAdapter = new WarAdapter(this.getActivity(),list,mTextNewses,isNoNetWorkConnect());
//        warAdapter = new WarAdapter(this.getActivity(),list);

        warRecycler.setAdapter(warAdapter);
        warRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == warAdapter.getItemCount()){
                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    if (isRefreshing){
                        warAdapter.notifyItemRemoved(warAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading){
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                page ++;
                                getNetData(getNewString(),page,10);
                                isLoading = false;
                            }
                        },1000);
                    }
                }
            }
        });
    }



    private void getNetData(String type ,int page ,int limit) {
//        if (isNoNetWorkConnect()){
//            ToastUtil.toast(getContext(),"当前没有可用网络！");
//            TextNewsDao textNewsDao = MyApplication.getInstances().getDaoSession().getTextNewsDao();
//            mTextNewses = textNewsDao.loadAll();
//        }else{
        //获取接口实例
        newsService = RetrofitServiceManager.getInstance().create(NewsService.class);
        newsLoader = new NewsLoader();
        net(type,page,limit);
//        }


    }

    private void net(String type , int page , int limit) {
        newsLoader.getNewsSubject(type,page,limit).subscribe(new Action1<NewsSubject>() {
            @Override
            public void call(NewsSubject newsSubject) {
                list.addAll(newsSubject.getList());
                warAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                warAdapter.notifyItemRemoved(warAdapter.getItemCount());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ToastUtil.toast(getActivity(),throwable.getMessage());
            }
        });
    }
}
