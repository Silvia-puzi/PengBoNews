package com.example.administrator.pengbonews.net;

import com.example.administrator.pengbonews.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 *  @项目名：  WebDemo 
 *  @包名：    com.example.administrator.webdemo.next
 *  @文件名:   RetrofitServiceManager
 *  @创建者:   Administrator
 *  @创建时间:  2017/4/22 20:39
 *  @描述：    每一个请求，都需要一个接口，
 *  里面定义了请求方法和请求参数等等，
 *  而获取接口实例需要通过一个Retrofit实例,这一步都是相同的，
 *  因此，我们可以把这些相同的部分抽取出来
 *
 *
 *
 *  创建了一个RetrofitServiceManager类，该类采用单例模式，
 *  在私有的构造方法中，生成了Retrofit 实例，
 *  并配置了OkHttpClient和一些公共配置。
 *  提供了一个create（）方法，生成接口实例，接收Class范型，
 *  因此项目中所有的接口实例Service都可以用这个来生成
 */
public class RetrofitServiceManager {
//    https://api.douban.com/v2/movie/
    private static final String TAG = "RetrofitServiceManager";
    private static final int DEFAULT_TIME_OUT = 5;//超时时间5s
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private Retrofit mRetrofit;
    private Retrofit mRetrofitVideo;
    private Retrofit mRetrofitVideoTitle;
    private Retrofit mRetrofitToday;
    private Retrofit mRetrofitNewsDetail;
    private Retrofit mRetrofitComment;
    private Retrofit mRetrofitTodayDetail1;
    private Retrofit mRetrofitTodayDetail2;
    private RetrofitServiceManager(){
        //创建okhttpclient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间

        //添加公共拦截器
        HttpCommonInterceptor interceptor = new HttpCommonInterceptor.Builder()
                .addHeaderParams("platform","android")
                .addHeaderParams("userToken","123")
                .addHeaderParams("userId","123")
                .build();
        builder.addInterceptor(interceptor);
        //创建retrofit
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build();

        mRetrofitVideo = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.VIDEO_URL)
                .build();

        mRetrofitToday = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.TODAY_URL)
                .build();

        mRetrofitNewsDetail= new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build();

        mRetrofitComment= new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.COMMENT_URL)
                .build();

        mRetrofitTodayDetail1 = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.TODAY_URL)
                .build();

        mRetrofitTodayDetail2 = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.TODAY_URL)
                .build();

    }

    private static class SingletonHolder{
        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
    }

    /*
    获取retrofitservicemanager
     */
  public static RetrofitServiceManager getInstance() {
      return SingletonHolder.INSTANCE;
  }

    /*
    获取对应Service
     */
    public <T> T create(Class<T> service){
        return mRetrofit.create(service);
    }

    public <T> T create2(Class<T> service){
        return mRetrofitVideo.create(service);
    }

    public <T> T create3(Class<T> service){
        return mRetrofitToday.create(service);
    }

    public <T> T create4(Class<T> service){
        return mRetrofitNewsDetail.create(service);
    }

    public <T> T create5(Class<T> service){
        return mRetrofitComment.create(service);
    }

    public <T> T create6(Class<T> service){
        return mRetrofitTodayDetail1.create(service);
    }

    public <T> T create7(Class<T> service){
        return mRetrofitTodayDetail2.create(service);
    }






}

