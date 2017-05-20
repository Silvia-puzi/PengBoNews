package com.example.administrator.pengbonews.adapter;

import android.support.v7.widget.RecyclerView;

import com.example.administrator.pengbonews.databinding.LoadmoreBinding;

/*
 *  @项目名：  PengBoNews
 *  @包名：    com.example.administrator.pengbonews.adapter
 *  @文件名:   FootBindingHolder
 *  @创建者:   Administrator
 *  @创建时间:  2017/4/25 23:49
 *  @描述：    TODO
 */
public class FootBindingHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "FootBindingHolder";

    private LoadmoreBinding mLoadmoreBinding;

    public FootBindingHolder(LoadmoreBinding binding) {
        super(binding.getRoot());
        this.mLoadmoreBinding = binding;
    }

    public LoadmoreBinding getBinding(){
        return mLoadmoreBinding;
    }

    public void setBinding(LoadmoreBinding binding){
        this.mLoadmoreBinding = binding;
    }
}
