package com.example.administrator.pengbonews.adapter;

import android.support.v7.widget.RecyclerView;

import com.example.administrator.pengbonews.databinding.TodayNormalBinding;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.adapter
 *  @文件名:   TodayNormalHolder
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/10 15:21
 *  @描述：    TODO
 */
public class TodayNormalHolder extends RecyclerView.ViewHolder{
    private static final String TAG = "TodayNormalHolder";


    private TodayNormalBinding mTodayNormalBinding;

    public TodayNormalHolder(TodayNormalBinding binding) {
        super(binding.getRoot());
        this.mTodayNormalBinding = binding;
    }

    public TodayNormalBinding getBinding(){
        return mTodayNormalBinding;
    }

    public void setBinding(TodayNormalBinding binding){
        this.mTodayNormalBinding = binding;
    }
}
