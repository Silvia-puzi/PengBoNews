package com.example.administrator.pengbonews.net;

import com.example.administrator.pengbonews.Bean.NewsDetailSubject;

import rx.Observable;
import rx.functions.Func1;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.net
 *  @文件名:   NewsDetailLoader
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/10 19:43
 *  @描述：    TODO
 */
public class NewsDetailLoader extends ObjectLoader {
    private static final String TAG = "NewsDetailLoader";


    private NewsDetailService mNewsDetailService;

    public NewsDetailLoader(){
        mNewsDetailService = RetrofitServiceManager.getInstance().create4(NewsDetailService.class);
    }

    /*
    获取信息
     */
    public Observable<NewsDetailSubject> getTodaySubject(int id){
        return observe(mNewsDetailService.getNewsDetail(id))
                .map(new Func1<NewsDetailSubject, NewsDetailSubject>() {
                    @Override
                    public NewsDetailSubject call(NewsDetailSubject detailSubject) {
                        return detailSubject;
                    }
                });
    }
}
