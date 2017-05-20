package com.example.administrator.pengbonews.net;

import com.example.administrator.pengbonews.Bean.TodaySubject;

import retrofit2.http.GET;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.net
 *  @文件名:   TodayService
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/10 12:46
 *  @描述：    TODO
 */
public interface TodayService {
    public static final String TAG = "TodayService";

    //获取今日新闻

    //获取今日新闻
    @GET("/nc/article/list/T1467284926140/0-20.html")
    rx.Observable<TodaySubject> getToday();

}
