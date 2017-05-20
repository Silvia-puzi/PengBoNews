package com.example.administrator.pengbonews.net;

/*
 *  @项目名：  WebDemo 
 *  @包名：    com.example.administrator.webdemo
 *  @文件名:   NewsService
 *  @创建者:   Administrator
 *  @创建时间:  2017/4/21 17:23
 *  @描述：    定义了一个方法getMsg,使用get请求方式，
 *  加上@GET 标签，标签后面是这个接口的
 *  尾址,完整的地址应该是 baseUrl+尾址 ，
 *  参数 使用@Query标签，
 *  如果参数多的话可以用@QueryMap标签，接收一个Map
 */

import com.example.administrator.pengbonews.Bean.NewsSubject;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {
    //获取军事新闻
    @GET("/news/api")
    rx.Observable<NewsSubject> getNews(@Query("type") String type, @Query("page") int page, @Query("limit") int limit);

//    @FormUrlEncoded
//    @POST()
}
