package com.example.administrator.pengbonews.utils;

import android.content.Context;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.utils
 *  @文件名:   PixelUtil
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/11 22:47
 *  @描述：    TODO
 */
public class PixelUtil {
    private static final String TAG = "PixelUtil";
    public static int dip2px(Context context, int dip){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip*density+0.5f);
    }
    public static int px2dip(Context context,int px){
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(px/density+0.5f);
    }

}
