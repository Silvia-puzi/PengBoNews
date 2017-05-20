package com.example.administrator.pengbonews.net;

import com.example.administrator.pengbonews.Bean.Today2Subject;

import retrofit2.http.GET;
import retrofit2.http.Path;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.net
 *  @文件名:   TodayService
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/10 12:46
 *  @描述：    TODO
 */
public interface TodayDetail2Service {
    public static final String TAG = "TodayService";

    //获取今日新闻

    //获取今日新闻
    @GET("/photo/api/set/{id}.json")
    rx.Observable<Today2Subject> getToday(@Path("id") String id);

}
