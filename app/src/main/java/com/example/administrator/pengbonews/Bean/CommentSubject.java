package com.example.administrator.pengbonews.Bean;

import java.util.HashMap;
import java.util.List;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.Bean
 *  @文件名:   CommentSubject
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/11 18:08
 *  @描述：    TODO
 */
public class CommentSubject {
    private static final String TAG = "CommentSubject";

    /**
     * 77782017 : {"against":0,"anonymous":false,"buildLevel":1,"commentId":77782017,"content":"屎太浓之扫射西瓜","createTime":"2017-05-03 12:48:26","deviceInfo":{"deviceName":""},"ext":{"type":"danmu","value":"17916.855556"},"favCount":0,"ip":"121.234.*.*","isDel":false,"postId":"CHJ1MAQ3008535RB_77782017","productKey":"a2869674571f77b5a0867c3d71db5856","shareCount":0,"siteName":"网易","source":"ph","unionState":false,"user":{"avatar":"","id":"c2F0YW45OTg3QDE2My5jb20=","location":"江苏省盐城市","nickname":"七翼天神","redNameInfo":[],"userId":111533803},"vote":0}
     */

    private CommentsBean comments;
    public CommentsBean getCommentsInfo() {
        return comments;
    }

    public void setCommentsInfo(CommentsBean threadInfo) {
        this.comments = threadInfo;
    }
    /**
     * againstLock : 0
     * rcount : 18
     * tcount : 18
     * url : http://v.163.com/paike/VCH7PC5OR/VCHJ1MAQ3.html
     */

    private ThreadInfoBean threadInfo;
    /**
     * commentIds : ["77782017"]
     * threadInfo : {"againstLock":"0","rcount":18,"tcount":18,"url":"http://v.163.com/paike/VCH7PC5OR/VCHJ1MAQ3.html"}
     */

    private List<String> commentIds;

    public ThreadInfoBean getThreadInfo() {
        return threadInfo;
    }

    public void setThreadInfo(ThreadInfoBean threadInfo) {
        this.threadInfo = threadInfo;
    }

    public List<String> getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(List<String> commentIds) {
        this.commentIds = commentIds;
    }

    public static class ThreadInfoBean {
        private String againstLock;
        private int rcount;
        private int tcount;
        private String url;

        public String getAgainstLock() {
            return againstLock;
        }

        public void setAgainstLock(String againstLock) {
            this.againstLock = againstLock;
        }

        public int getRcount() {
            return rcount;
        }

        public void setRcount(int rcount) {
            this.rcount = rcount;
        }

        public int getTcount() {
            return tcount;
        }

        public void setTcount(int tcount) {
            this.tcount = tcount;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class CommentsBean {
        private HashMap<String,CommentUser> mUser;

        public HashMap<String,CommentUser> getUser() {
            return mUser;
        }

        public void setUser(HashMap<String,CommentUser> user) {
            mUser = user;
        }

        public static class CommentUser {
            private String content;
            private String createTime;
            private UserInfo mUserInfo;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public UserInfo getUserInfo() {
                return mUserInfo;
            }

            public void setUserInfo(UserInfo userInfo) {
                mUserInfo = userInfo;
            }

            public static class UserInfo {
                private String avatar;
                private String nickname;

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }
            }
        }
    }
}
