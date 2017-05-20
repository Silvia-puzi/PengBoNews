package com.example.administrator.pengbonews.net;

import com.example.administrator.pengbonews.Bean.Today1Subject;

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
public class TodayDetail1Loader extends ObjectLoader {
    private static final String TAG = "TodayLoader";


    private TodayDetail1Service mTodayService;

    public TodayDetail1Loader(){
        mTodayService = RetrofitServiceManager.getInstance().create3(TodayDetail1Service.class);
    }

    /*
    获取信息
     */
    public Observable<Today1Subject> getToday1Subject(String id){
        return observe(mTodayService.getToday(id))
                .map(new Func1<Today1Subject, Today1Subject>() {
                    @Override
                    public Today1Subject call(Today1Subject videoSubject) {
                        return videoSubject;
                    }
                });
    }
}
