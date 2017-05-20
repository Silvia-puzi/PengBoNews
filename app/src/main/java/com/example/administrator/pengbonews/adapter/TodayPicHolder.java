package com.example.administrator.pengbonews.adapter;

import android.support.v7.widget.RecyclerView;

import com.example.administrator.pengbonews.databinding.TodayPicBinding;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.adapter
 *  @文件名:   TodayPicHolder
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/10 15:21
 *  @描述：    TODO
 */
public class TodayPicHolder extends RecyclerView.ViewHolder{
    private static final String TAG = "TodayPicHolder";

    private TodayPicBinding mTodayPicBinding;

    public TodayPicHolder(TodayPicBinding binding) {
        super(binding.getRoot());
        this.mTodayPicBinding = binding;
    }

    public TodayPicBinding getBinding(){
        return mTodayPicBinding;
    }

    public void setBinding(TodayPicBinding binding){
        this.mTodayPicBinding = binding;
    }
}
