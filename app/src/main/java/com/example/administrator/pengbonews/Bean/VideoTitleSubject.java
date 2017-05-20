package com.example.administrator.pengbonews.Bean;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.Bean
 *  @文件名:   VideoTitleSubject
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/8 0:02
 *  @描述：    TODO
 */
public class VideoTitleSubject {
    private static final String TAG = "VideoTitleSubject";

    /**
     * categorys :
     * cname : 推荐
     * ename : Video_Recom
     */

    private String categorys;
    private String cname;
    private String ename;

    public String getCategorys() {
        return categorys;
    }

    public void setCategorys(String categorys) {
        this.categorys = categorys;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }
}
