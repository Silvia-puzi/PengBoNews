package com.example.administrator.pengbonews.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.pengbonews.R;
import com.example.administrator.pengbonews.adapter.VideoMainAdapter;
import com.example.administrator.pengbonews.databinding.FragmentVideoBinding;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.view.activity
 *  @文件名:   VideoFragment
 *  @创建者:   Administrator
 *  @创建时间:  2017/4/19 23:34
 *  @描述：    TODO
 */
public class VideoFragment extends Fragment {
    private static final String TAG = "VideoFragment";
    private FragmentVideoBinding mFragmentVideoBinding;
    private List<String> titleList = new ArrayList<>();
    private List<String> titleUrlList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();//fragment集合


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentVideoBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_video,container,false);
        TabLayout videoTab = mFragmentVideoBinding.videoTab;
        ViewPager videoViewpager = mFragmentVideoBinding.videoViewpager;
        titleList.add("推荐");
        titleList.add("搞笑");
        titleList.add("现场");
        titleList.add("八卦");
        titleList.add("萌物");
        titleList.add("影视");
        titleList.add("涨姿势");
        titleList.add("美女");
        titleList.add("小品");
        titleList.add("猎奇");
        titleList.add("音乐");
        titleList.add("军武");
        titleList.add("黑科技");
        titleList.add("二次元");

        for (int i = 0 ; i < titleList.size() ; i ++){
            videoTab.addTab(videoTab.newTab().setText(titleList.get(i)));
        }
        videoTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        VideoMainAdapter adapter = new VideoMainAdapter(getFragmentManager());
        adapter.getList(mFragmentList,titleList,titleUrlList);
        videoViewpager.setAdapter(adapter);
        videoTab.setupWithViewPager(videoViewpager);
        return mFragmentVideoBinding.getRoot();
    }

}