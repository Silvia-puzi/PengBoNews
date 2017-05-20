package com.example.administrator.pengbonews.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.entity
 *  @文件名:   Favorite
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/12 22:38
 *  @描述：    TODO
 */
@Entity
public class Favorite {
    private static final String TAG = "Favorite";
    @Id
    private String id;//新闻id
    private String type;//新闻类型
    private String title;//新闻标题
    private String source;//新闻来源
    private String time;//新闻发布时间
    private String img;//新闻图片

    private String videoUrl;//视频路径
    private String videoIcon;//视频发布人头像
    private String mCommentId;//评论
    @Transient
    private int tempUsageCount;
    @Generated(hash = 624916079)
    public Favorite(String id, String type, String title, String source,
            String time, String img, String videoUrl, String videoIcon,
            String mCommentId) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.source = source;
        this.time = time;
        this.img = img;
        this.videoUrl = videoUrl;
        this.videoIcon = videoIcon;
        this.mCommentId = mCommentId;
    }
    @Generated(hash = 459811785)
    public Favorite() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSource() {
        return this.source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getImg() {
        return this.img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getVideoUrl() {
        return this.videoUrl;
    }
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    public String getVideoIcon() {
        return this.videoIcon;
    }
    public void setVideoIcon(String videoIcon) {
        this.videoIcon = videoIcon;
    }
    public String getMCommentId() {
        return this.mCommentId;
    }
    public void setMCommentId(String mCommentId) {
        this.mCommentId = mCommentId;
    }
    
}
