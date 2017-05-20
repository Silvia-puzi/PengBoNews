package com.example.administrator.pengbonews.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.pengbonews.Bean.TodaySubject;
import com.example.administrator.pengbonews.R;
import com.example.administrator.pengbonews.adapter.TodayAdapter;
import com.example.administrator.pengbonews.databinding.FragmentWechatBinding;
import com.example.administrator.pengbonews.net.RetrofitServiceManager;
import com.example.administrator.pengbonews.net.TodayLoader;
import com.example.administrator.pengbonews.net.TodayService;
import com.example.administrator.pengbonews.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.view.activity
 *  @文件名:   WechatFragment
 *  @创建者:   Administrator
 *  @创建时间:  2017/4/19 23:30
 *  @描述：    TODO
 */
public class WechatFragment extends Fragment {
    private static final String TAG = "WechatFragment";
    FragmentWechatBinding fragmentWechatBinding;
    private RecyclerView videoRecycler;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TodayService mTodayService;
    private TodayLoader mTodayLoader;
    private TodayAdapter mTodayAdapter;
    private LinearLayoutManager layoutManager;
    private List<TodaySubject.T1467284926140Bean> list = new ArrayList<>();
    private Handler handler = new Handler();
    private boolean isLoading;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView(inflater, container);
        initData();
        return fragmentWechatBinding.getRoot();
    }



    private void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getNetData();
            }
        }, 1500);
    }

    private void initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        fragmentWechatBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_wechat, container, false);
        videoRecycler = fragmentWechatBinding.todayRecycler;
        swipeRefreshLayout = fragmentWechatBinding.todaySwipe;
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
                        getNetData();
                    }
                },2000);
            }
        });


        //设置布局管理器
        layoutManager = new LinearLayoutManager(getActivity());
        videoRecycler.setLayoutManager(layoutManager);
        //设为垂直布局
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置adapter
        mTodayAdapter = new TodayAdapter(this.getActivity(),list);
        videoRecycler.setAdapter(mTodayAdapter);
        videoRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == mTodayAdapter.getItemCount()){
                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    if (isRefreshing){
                        mTodayAdapter.notifyItemRemoved(mTodayAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading){
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getNetData();
                                isLoading = false;
                            }
                        },1000);
                    }
                }
            }
        });
    }



    private void getNetData() {
        //获取接口实例
        mTodayService = RetrofitServiceManager.getInstance().create3(TodayService.class);
        mTodayLoader = new TodayLoader();
        net();

    }

    private void net() {
        mTodayLoader.getTodaySubject()
                .subscribe(new Action1<TodaySubject>() {
                    @Override
                    public void call(TodaySubject videoSubject) {
                        videoSubject.getT1467284926140();
                        Log.e(TAG, "callaaaaaaaaaaaaaaaaa: "+videoSubject.getT1467284926140());//videoSubject.getT1467284926140()
                        list.addAll(videoSubject.getT1467284926140());
                        mTodayAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        mTodayAdapter.notifyItemRemoved(mTodayAdapter.getItemCount());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: "+list +"  ");
                        throwable.printStackTrace();
                        ToastUtil.toast(getActivity(),throwable.getMessage());
                    }
                });
    }

}
