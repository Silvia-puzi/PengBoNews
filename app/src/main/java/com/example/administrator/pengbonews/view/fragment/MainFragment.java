package com.example.administrator.pengbonews.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.pengbonews.R;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.view.fragment
 *  @文件名:   MainFragment
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/14 0:37
 *  @描述：    TODO
 */
public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }
}
