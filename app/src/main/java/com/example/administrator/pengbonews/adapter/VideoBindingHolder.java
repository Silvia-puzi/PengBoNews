package com.example.administrator.pengbonews.adapter;

import android.support.v7.widget.RecyclerView;

import com.example.administrator.pengbonews.databinding.ItemVideoBinding;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.adapter
 *  @文件名:   VideoBindingHolder
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/6 19:15
 *  @描述：    TODO
 */
public class VideoBindingHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "VideoBindingHolder";
    private ItemVideoBinding mItemVideoBinding;

    public VideoBindingHolder(ItemVideoBinding binding) {
        super(binding.getRoot());
        this.mItemVideoBinding = binding;
    }

    public ItemVideoBinding getBinding(){
        return mItemVideoBinding;
    }

    public void setBinding(ItemVideoBinding binding){
        this.mItemVideoBinding = binding;
    }
}

