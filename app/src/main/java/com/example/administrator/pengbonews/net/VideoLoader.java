package com.example.administrator.pengbonews.net;

import com.example.administrator.pengbonews.Bean.VideoSubject;

import rx.Observable;
import rx.functions.Func1;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.net
 *  @文件名:   VideoLoader
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/5 18:25
 *  @描述：    TODO
 */
public class VideoLoader extends ObjectLoader {
    private static final String TAG = "VideoLoader";

    private VideoService mVideoService;

    public VideoLoader(){
        mVideoService = RetrofitServiceManager.getInstance().create2(VideoService.class);
    }

    /*
    获取信息
     */
    public Observable<VideoSubject> getVideoSubject(String channel,String subtab,String size ,int offset ,String others){
        return observe(mVideoService.getVideos(channel,subtab,size,offset,others))
                .map(new Func1<VideoSubject, VideoSubject>() {
                    @Override
                    public VideoSubject call(VideoSubject videoSubject) {
                        return videoSubject;
                    }
                });
    }
}
