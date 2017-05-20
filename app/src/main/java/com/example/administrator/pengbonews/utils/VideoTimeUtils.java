package com.example.administrator.pengbonews.utils;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.utils
 *  @文件名:   VideoTimeUtils
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/6 23:15
 *  @描述：    TODO
 */
public class VideoTimeUtils {
    private static final String TAG = "VideoTimeUtils";

    public static String transTime(int duration){
        StringBuffer  buffer = new StringBuffer();
        double time = duration;
        int min = (int) (time / 60);
        if (min < 10){
            buffer.append("0").append(min);
        }else {
            buffer.append(min);
        }
        buffer.append(":");
        int sec = (int) (((time / 60) - min) * 60);
        if (sec < 10){
            buffer.append("0").append(sec);
        }else{
            buffer.append(sec);
        }
        return buffer.toString();
    }
}
