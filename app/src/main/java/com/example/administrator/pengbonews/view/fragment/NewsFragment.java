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
import com.example.administrator.pengbonews.adapter.NewsAdapter;
import com.example.administrator.pengbonews.databinding.FragmentNewsBinding;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.view.activity
 *  @文件名:   NewsFragment
 *  @创建者:   Administrator
 *  @创建时间:  2017/4/19 23:19
 *  @描述：    TODO
 */
public class NewsFragment extends Fragment {
    private static final String TAG = "NewsFragment";
    private FragmentNewsBinding binding;
    private TabLayout newsTablayout;
    private ViewPager newsViewpager;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<Fragment> mFragmentList = new ArrayList<>();//fragment集合


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_news,container,false);
        newsTablayout = binding.newsTablayout;
        newsViewpager = binding.newsViewpager;
        mTitleList.add("军事");
        mTitleList.add("体育");
        mTitleList.add("科技");
        mTitleList.add("教育");
        mTitleList.add("娱乐");
        mTitleList.add("财经");
        mTitleList.add("股票");
        mTitleList.add("旅游");
        mTitleList.add("女人");

        for (int i = 0 ; i < mTitleList.size() ; i ++){
            newsTablayout.addTab(newsTablayout.newTab().setText(mTitleList.get(i)));
        }
        newsTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        NewsAdapter adapter = new NewsAdapter(getFragmentManager());
        adapter.getList(mFragmentList,mTitleList);
        newsViewpager.setAdapter(adapter);
        newsTablayout.setupWithViewPager(newsViewpager);
        return binding.getRoot();
    }

}
