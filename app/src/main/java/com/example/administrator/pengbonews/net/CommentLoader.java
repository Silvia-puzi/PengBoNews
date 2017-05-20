package com.example.administrator.pengbonews.net;

import com.example.administrator.pengbonews.Bean.CommentSubject;

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
public class CommentLoader extends ObjectLoader {
    private static final String TAG = "VideoLoader";

    private CommentService mVideoService;

    public CommentLoader(){
        mVideoService = RetrofitServiceManager.getInstance().create5(CommentService.class);
    }

    /*
    获取信息
     */
    public Observable<CommentSubject> getVideoSubject(){
        return observe(mVideoService.getComment())
                .map(new Func1<CommentSubject, CommentSubject>() {
                    @Override
                    public CommentSubject call(CommentSubject videoSubject) {
                        return videoSubject;
                    }
                });
    }
}
