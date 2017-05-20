package com.example.administrator.pengbonews.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.view.activity
 *  @文件名:   BaseActivity
 *  @创建者:   Administrator
 *  @创建时间:  2017/3/26 12:20
 *  @描述：    activity 的基类
 */
public abstract class BaseActivity extends AppCompatActivity{
    private static final String TAG = "BaseActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = layout();
        setContentView(layout);
    }

    protected abstract Activity getActivityTag();

    protected abstract int layout();
}
