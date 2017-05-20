package com.example.administrator.pengbonews.net;

import com.example.administrator.pengbonews.Bean.NewsSubject;

import rx.Observable;
import rx.functions.Func1;

/*
 *  @项目名：  WebDemo 
 *  @包名：    com.example.administrator.webdemo.next
 *  @文件名:   NewsLoader
 *  @创建者:   Administrator
 *  @创建时间:  2017/4/22 21:27
 *  @描述：    TODO
 */
public class NewsLoader extends ObjectLoader{
    private static final String TAG = "NewsLoader";
    private NewsService mNewsService;

    public NewsLoader(){
        mNewsService = RetrofitServiceManager.getInstance().create(NewsService.class);
    }

    /*
    获取信息
     */
    public Observable<NewsSubject> getNewsSubject(String type,int page , int limit){
        return observe(mNewsService.getNews(type,page,limit))
                .map(new Func1<NewsSubject, NewsSubject>() {
                    @Override
                    public NewsSubject call(NewsSubject newsSubject) {
                        return newsSubject;
                    }
                });
    }
}
