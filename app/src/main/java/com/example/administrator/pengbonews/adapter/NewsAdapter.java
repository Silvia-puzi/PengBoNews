package com.example.administrator.pengbonews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.administrator.pengbonews.view.fragment.FragmentFactory;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.view.activity
 *  @文件名:   NewsAdapter
 *  @创建者:   Administrator
 *  @创建时间:  2017/4/20 19:50
 *  @描述：    TODO
 */
public class NewsAdapter extends FragmentPagerAdapter{
    private static final String TAG = "NewsAdapter";
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();

    public NewsAdapter(FragmentManager fm) {
        super(fm);
    }

    public void getList(List<Fragment> list , List<String> titleLlist){
        this.mFragmentList = list;
        this.mTitleList = titleLlist;
    }

    @Override
    public Fragment getItem(int position) {
        Log.e(TAG, "getItem: "+position );
        return FragmentFactory.createFragment(position);
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
