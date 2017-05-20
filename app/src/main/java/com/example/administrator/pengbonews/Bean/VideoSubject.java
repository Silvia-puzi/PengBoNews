package com.example.administrator.pengbonews.Bean;

import java.util.List;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.Bean
 *  @文件名:   VideoSubject
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/5 18:26
 *  @描述：    TODO
 */
public class VideoSubject {
    private static final String TAG = "VideoSubject";

    /**
     * cover : http://vimg2.ws.126.net/image/snapshot/2017/5/U/7/VCJ12RKU7.jpg
     * danmu : 1
     * description :
     * length : 52
     * m3u8_url : http://flv2.bn.netease.com/videolib3/1705/08/EhmDT3551/SD/movie_index.m3u8
     * mp4_url : http://flv2.bn.netease.com/videolib3/1705/08/EhmDT3551/SD/EhmDT3551-mobile.mp4
     * playCount : 558
     * playersize : 1
     * program : base
     * prompt : 成功为您推荐20条新视频
     * ptime : 2017-05-08 22:26:53.0
     * replyBoard : video_bbs
     * replyCount : 0
     * replyid : CJ120699008535RB
     * sectiontitle : http://vimg2.ws.126.net/image/snapshot/2016/12/2/2/VC8CN7222.jpg
     * sizeHD : 0
     * sizeSD : 3900
     * sizeSHD : 0
     * title : 美食达人教你做一道超好吃的家常粉蒸凤爪
     * topicDesc : 本着一颗吃货的心，搜罗世界各地的美食。带领大家不用出门就能看到各种风味的美食以及制作方法等，每个吃货都有一种勇于探索的精神。
     * topicImg : http://vimg2.ws.126.net/image/snapshot/2016/12/2/2/VC8CN7222.jpg
     * topicName : 舌尖上的吃货
     * topicSid : VC8CN721K
     * vid : VCJ120699
     * videoTopic : {"alias":"搜罗世界各地美食及美食故事","ename":"T1482810855672","tid":"T1482810855672","tname":"舌尖上的吃货","topic_icons":"http://dingyue.nosdn.127.net/57oIOlWARwbb08OiGU3tFMzv96NGD8UFHzp6bqLEtexH51487729206437.jpg"}
     * videosource : 新媒体
     */

    private List<视频Bean> 视频;

    public List<视频Bean> get视频() {
        return 视频;
    }

    public void set视频(List<视频Bean> 视频) {
        this.视频 = 视频;
    }

    public static class 视频Bean {
        private String cover;
        private int danmu;
        private String description;
        private int length;
        private String m3u8_url;
        private String mp4_url;
        private int playCount;
        private int playersize;
        private String program;
        private String prompt;
        private String ptime;
        private String replyBoard;
        private int replyCount;
        private String replyid;
        private String sectiontitle;
        private int sizeHD;
        private int sizeSD;
        private int sizeSHD;
        private String title;
        private String topicDesc;
        private String topicImg;
        private String topicName;
        private String topicSid;
        private String vid;
        /**
         * alias : 搜罗世界各地美食及美食故事
         * ename : T1482810855672
         * tid : T1482810855672
         * tname : 舌尖上的吃货
         * topic_icons : http://dingyue.nosdn.127.net/57oIOlWARwbb08OiGU3tFMzv96NGD8UFHzp6bqLEtexH51487729206437.jpg
         */

        private VideoTopicBean videoTopic;
        private String videosource;

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getDanmu() {
            return danmu;
        }

        public void setDanmu(int danmu) {
            this.danmu = danmu;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public String getM3u8_url() {
            return m3u8_url;
        }

        public void setM3u8_url(String m3u8_url) {
            this.m3u8_url = m3u8_url;
        }

        public String getMp4_url() {
            return mp4_url;
        }

        public void setMp4_url(String mp4_url) {
            this.mp4_url = mp4_url;
        }

        public int getPlayCount() {
            return playCount;
        }

        public void setPlayCount(int playCount) {
            this.playCount = playCount;
        }

        public int getPlayersize() {
            return playersize;
        }

        public void setPlayersize(int playersize) {
            this.playersize = playersize;
        }

        public String getProgram() {
            return program;
        }

        public void setProgram(String program) {
            this.program = program;
        }

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public String getReplyBoard() {
            return replyBoard;
        }

        public void setReplyBoard(String replyBoard) {
            this.replyBoard = replyBoard;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public String getReplyid() {
            return replyid;
        }

        public void setReplyid(String replyid) {
            this.replyid = replyid;
        }

        public String getSectiontitle() {
            return sectiontitle;
        }

        public void setSectiontitle(String sectiontitle) {
            this.sectiontitle = sectiontitle;
        }

        public int getSizeHD() {
            return sizeHD;
        }

        public void setSizeHD(int sizeHD) {
            this.sizeHD = sizeHD;
        }

        public int getSizeSD() {
            return sizeSD;
        }

        public void setSizeSD(int sizeSD) {
            this.sizeSD = sizeSD;
        }

        public int getSizeSHD() {
            return sizeSHD;
        }

        public void setSizeSHD(int sizeSHD) {
            this.sizeSHD = sizeSHD;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTopicDesc() {
            return topicDesc;
        }

        public void setTopicDesc(String topicDesc) {
            this.topicDesc = topicDesc;
        }

        public String getTopicImg() {
            return topicImg;
        }

        public void setTopicImg(String topicImg) {
            this.topicImg = topicImg;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        public String getTopicSid() {
            return topicSid;
        }

        public void setTopicSid(String topicSid) {
            this.topicSid = topicSid;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public VideoTopicBean getVideoTopic() {
            return videoTopic;
        }

        public void setVideoTopic(VideoTopicBean videoTopic) {
            this.videoTopic = videoTopic;
        }

        public String getVideosource() {
            return videosource;
        }

        public void setVideosource(String videosource) {
            this.videosource = videosource;
        }

        public static class VideoTopicBean {
            private String alias;
            private String ename;
            private String tid;
            private String tname;
            private String topic_icons;

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public String getEname() {
                return ename;
            }

            public void setEname(String ename) {
                this.ename = ename;
            }

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }

            public String getTname() {
                return tname;
            }

            public void setTname(String tname) {
                this.tname = tname;
            }

            public String getTopic_icons() {
                return topic_icons;
            }

            public void setTopic_icons(String topic_icons) {
                this.topic_icons = topic_icons;
            }
        }
    }
}
