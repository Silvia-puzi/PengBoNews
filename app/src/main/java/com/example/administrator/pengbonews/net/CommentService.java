package com.example.administrator.pengbonews.net;

import com.example.administrator.pengbonews.Bean.CommentSubject;

import retrofit2.http.GET;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.net
 *  @文件名:   TodayService
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/10 12:46
 *  @描述：    TODO
 */
public interface CommentService {
    public static final String TAG = "TodayService";

    //获取今日新闻

    //获取今日新闻
    @GET("/api/v1/products/a2869674571f77b5a0867c3d71db5856/threads/CHJ1MAQ3008535RB/comments/ext/danmu/0/200/2/0/1")
    rx.Observable<CommentSubject> getComment();

}
