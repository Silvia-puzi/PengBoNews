package com.example.administrator.pengbonews.utils;

import android.content.Context;
import android.widget.Toast;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.utils
 *  @文件名:   ToastUtil
 *  @创建者:   Administrator
 *  @创建时间:  2017/4/23 22:26
 *  @描述：    TODO
 */
public class ToastUtil {

    private static Toast toast;

    public static void toast(Context context, String msg){
        if(toast==null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }else {
            toast.setText(msg);
        }
        toast.show();
    }
}
