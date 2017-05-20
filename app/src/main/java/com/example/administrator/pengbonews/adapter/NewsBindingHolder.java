package com.example.administrator.pengbonews.adapter;

import android.support.v7.widget.RecyclerView;

import com.example.administrator.pengbonews.databinding.ItemNewsBinding;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.adapter
 *  @文件名:   NewsBindingHolder
 *  @创建者:   Administrator
 *  @创建时间:  2017/4/23 21:23
 *  @描述：    TODO
 */
public class NewsBindingHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "NewsBindingHolder";
    private ItemNewsBinding mItemNewsBinding;

    public NewsBindingHolder(ItemNewsBinding binding) {
        super(binding.getRoot());
        this.mItemNewsBinding = binding;
    }

    public ItemNewsBinding getBinding(){
        return mItemNewsBinding;
    }

    public void setBinding(ItemNewsBinding binding){
        this.mItemNewsBinding = binding;
    }
}
