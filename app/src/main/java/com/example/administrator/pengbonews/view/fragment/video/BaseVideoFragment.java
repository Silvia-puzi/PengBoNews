package com.example.administrator.pengbonews.view.fragment.video;

import android.databinding.DataBindingUtil;
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

import com.example.administrator.pengbonews.Bean.VideoSubject;
import com.example.administrator.pengbonews.R;
import com.example.administrator.pengbonews.adapter.VideoAdapter;
import com.example.administrator.pengbonews.databinding.FragmentRecomBinding;
import com.example.administrator.pengbonews.net.RetrofitServiceManager;
import com.example.administrator.pengbonews.net.VideoLoader;
import com.example.administrator.pengbonews.net.VideoService;
import com.example.administrator.pengbonews.utils.Constants;
import com.example.administrator.pengbonews.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.view.fragment.video
 *  @文件名:   BaseVideoFragment
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/9 23:59
 *  @描述：    TODO
 */
public abstract class BaseVideoFragment extends Fragment {
    private static final String TAG = "BaseVideoFragment";

    private FragmentRecomBinding mFragmentVideoBinding;
    private RecyclerView videoRecycler;
    private SwipeRefreshLayout swipeRefreshLayout;
    private VideoService mVideoService;
    private VideoLoader mVideoLoader;
    private VideoAdapter mVideoAdapter;
    private LinearLayoutManager layoutManager;
    private List<VideoSubject.视频Bean> list = new ArrayList<>();
    private Handler handler = new Handler();
    private boolean isLoading;
    private int page = 1;

    private String subtab = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        subtab = setSubtab();
        initView(inflater, container);
        initData();
        return mFragmentVideoBinding.getRoot();
    }

    protected abstract String setSubtab();


    private void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getNetData(Constants.VIDEO_CHANNEL,subtab,Constants.VIDEO_SIZE,0,Constants.VIDEO_OTHERS);
            }
        }, 1500);
    }

    private void initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mFragmentVideoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recom, container, false);
        videoRecycler = mFragmentVideoBinding.recomRecycler;
        swipeRefreshLayout = mFragmentVideoBinding.recomSwipe;
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
                        getNetData(Constants.VIDEO_CHANNEL,subtab,Constants.VIDEO_SIZE,0,Constants.VIDEO_OTHERS);
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
        mVideoAdapter = new VideoAdapter(this.getActivity(),list);
        videoRecycler.setAdapter(mVideoAdapter);
        videoRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == mVideoAdapter.getItemCount()){
                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    if (isRefreshing){
                        mVideoAdapter.notifyItemRemoved(mVideoAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading){
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                page ++;
                                getNetData(Constants.VIDEO_CHANNEL,subtab,Constants.VIDEO_SIZE,page,Constants.VIDEO_OTHERS);
                                isLoading = false;
                            }
                        },1000);
                    }
                }
            }
        });
    }



    private void getNetData(String channel, String subtab, String size , int offset , String others) {
        //获取接口实例
        mVideoService = RetrofitServiceManager.getInstance().create2(VideoService.class);
        mVideoLoader = new VideoLoader();
        net(channel,subtab,size,offset,others);

    }

    private void net(String channel, String subtab, String size , int offset , String others) {
        mVideoLoader.getVideoSubject(channel,subtab,size,offset,others)
                .subscribe(new Action1<VideoSubject>() {
                    @Override
                    public void call(VideoSubject videoSubject) {
                        list.addAll(videoSubject.get视频());
                        mVideoAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        mVideoAdapter.notifyItemRemoved(mVideoAdapter.getItemCount());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.toast(getActivity(),throwable.getMessage());
                    }
                });
    }

}

