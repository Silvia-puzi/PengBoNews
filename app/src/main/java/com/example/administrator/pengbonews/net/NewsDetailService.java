package com.example.administrator.pengbonews.net;

import com.example.administrator.pengbonews.Bean.NewsDetailSubject;

import retrofit2.http.GET;
import retrofit2.http.Query;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.net
 *  @文件名:   NewsDetailService
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/10 19:43
 *  @描述：    TODO
 */
public interface NewsDetailService {
    public static final String TAG = "NewsDetailService";

    //获取新闻详情
    @GET("/detail/api")
    rx.Observable<NewsDetailSubject> getNewsDetail(@Query("simpleId") int id);
}
