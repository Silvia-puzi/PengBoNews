package com.example.administrator.pengbonews.net;

import com.example.administrator.pengbonews.Bean.VideoSubject;

import retrofit2.http.GET;
import retrofit2.http.Query;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.net
 *  @文件名:   VideoService
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/5 18:28
 *  @描述：    TODO
 */
public interface VideoService {
    public static final String TAG = "VideoService";

    //获取军事新闻
    @GET("/recommend/getChanListNews")
    rx.Observable<VideoSubject> getVideos(
            @Query("channel") String channel, @Query("subtab") String subtab,
            @Query("size") String size , @Query("offset") int offset , @Query("others") String others);
}
