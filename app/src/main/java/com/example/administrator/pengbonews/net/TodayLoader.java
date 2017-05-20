package com.example.administrator.pengbonews.net;

import com.example.administrator.pengbonews.Bean.TodaySubject;

import rx.Observable;
import rx.functions.Func1;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.net
 *  @文件名:   TodayLoader
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/10 12:45
 *  @描述：    TODO
 */
public class TodayLoader extends ObjectLoader {
    private static final String TAG = "TodayLoader";


    private TodayService mTodayService;

    public TodayLoader(){
        mTodayService = RetrofitServiceManager.getInstance().create3(TodayService.class);
    }

    /*
    获取信息
     */
    public Observable<TodaySubject> getTodaySubject(){
        return observe(mTodayService.getToday())
                .map(new Func1<TodaySubject, TodaySubject>() {
                    @Override
                    public TodaySubject call(TodaySubject videoSubject) {
                        return videoSubject;
                    }
                });
    }
}
