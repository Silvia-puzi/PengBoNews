package com.example.administrator.pengbonews.adapter;

import android.support.v7.widget.RecyclerView;

import com.example.administrator.pengbonews.databinding.ItemFavorBinding;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.adapter
 *  @文件名:   FavorBindingHolder
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/18 16:44
 *  @描述：    TODO
 */
public class FavorBindingHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "FootBindingHolder";

    private ItemFavorBinding mLoadmoreBinding;

    public FavorBindingHolder(ItemFavorBinding binding) {
        super(binding.getRoot());
        this.mLoadmoreBinding = binding;
    }

    public ItemFavorBinding getBinding(){
        return mLoadmoreBinding;
    }

    public void setBinding(ItemFavorBinding binding){
        this.mLoadmoreBinding = binding;
    }
}