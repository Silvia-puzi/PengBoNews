package com.example.administrator.pengbonews.view.impl;

/*
 *  @项目名：  PBNews 
 *  @包名：    com.example.administrator.pbnews.view.impl
 *  @文件名:   ImplSplash
 *  @创建者:   Administrator
 *  @创建时间:  2017/4/13 13:52
 *  @描述：    splash界面的接口类
 */
public interface ImplSplash {
    public static final String TAG = "ImplSplash";

    /*
    第一次打开进入viewpager的引导界面,点击按钮进入主界面
     */
    public interface View{
         void initDot();
    }


    public interface Presenter{

    }
}
