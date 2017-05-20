package com.example.administrator.pengbonews.net;

/*
 *  @项目名：  WebDemo 
 *  @包名：    com.example.administrator.webdemo.next
 *  @文件名:   ObjectLoader
 *  @创建者:   Administrator
 *  @创建时间:  2017/4/22 21:39
 */

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 * 将一些重复的操作提出来，放到父类以免Loader 里每个接口都有重复代码
 * Created by zhouwei on 16/11/10.
 *
 */
public class ObjectLoader {
    /**
     *
     * @param observable
     * @param <T>
     * @return
     */
    protected  <T> Observable<T> observe(Observable<T> observable){
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
