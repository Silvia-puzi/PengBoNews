package com.example.administrator.pengbonews.net;

import com.example.administrator.pengbonews.Bean.Today2Subject;

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
public class TodayDetail2Loader extends ObjectLoader {
    private static final String TAG = "TodayLoader";


    private TodayDetail2Service mTodayService;

    public TodayDetail2Loader(){
        mTodayService = RetrofitServiceManager.getInstance().create7(TodayDetail2Service.class);
    }

    /*
    获取信息
     */
    public Observable<Today2Subject> getToday2Subject(String id){
        return observe(mTodayService.getToday(id))
                .map(new Func1<Today2Subject, Today2Subject>() {
                    @Override
                    public Today2Subject call(Today2Subject videoSubject) {
                        return videoSubject;
                    }
                });
    }
}
