package com.example.administrator.pengbonews.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.pengbonews.view.fragment.FragmentFactory;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.adapter
 *  @文件名:   VideoMainAdapter
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/8 16:24
 *  @描述：    TODO
 */
public class VideoMainAdapter extends FragmentPagerAdapter {
    private static final String TAG = "VideoMainAdapter";
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();
    private List<String> mTitleUrlList = new ArrayList<>();
    private Activity mActivity;
    private FragmentManager mFragmentManager;

    public VideoMainAdapter(FragmentManager fm) {
        super(fm);
        this.mFragmentManager = fm;
    }

    public void getList(List<Fragment> list , List<String> titleLlist , List<String> titleUrlList){
        this.mFragmentList = list;
        this.mTitleList = titleLlist;
        this.mTitleUrlList = titleUrlList;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.createVideoFragment(position);
    }

    @Override
    public int getCount() {
        return mTitleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
