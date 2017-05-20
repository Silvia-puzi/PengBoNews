package com.example.administrator.pengbonews.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.Bean
 *  @文件名:   TextNews
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/12 17:13
 *  @描述：    TODO
 */
@Entity
public class TextNews {
    private static final String TAG = "TextNews";
    @Id
    private String title;
    private String time;
    private String imgurl;
    private int newsId;
    @Transient
    private int tempUsageCount;
    @Generated(hash = 2018810739)
    public TextNews(String title, String time, String imgurl, int newsId) {
        this.title = title;
        this.time = time;
        this.imgurl = imgurl;
        this.newsId = newsId;
    }
    @Generated(hash = 1969039975)
    public TextNews() {
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getImgurl() {
        return this.imgurl;
    }
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
    public int getNewsId() {
        return this.newsId;
    }
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

}

