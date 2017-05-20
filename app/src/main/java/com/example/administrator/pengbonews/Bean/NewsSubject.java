package com.example.administrator.pengbonews.Bean;

import java.util.List;

/*
 *  @项目名：  WebDemo 
 *  @包名：    com.example.administrator.webdemo
 *  @文件名:   NewsSubject
 *  @创建者:   Administrator
 *  @创建时间:  2017/4/21 20:47
 *  @描述：    TODO
 */
public class NewsSubject {
    private static final String TAG = "NewsSubject";

    /**
     * size : 10
     * list : [{"imgurl":"http://cms-bucket.nosdn.127.net/catchpic/0/09/09cf1a56fc5576b178484413ba18e821.jpg","has_content":true,"docurl":"http://war.163.com/17/0421/17/CIIJ6O91000181KT.html","id":6239,"time":"2017-04-21 17:52:17","title":"美媒:朝或改装潜艇可连发潜射导弹 韩称说法不实","channelname":"war"},{"imgurl":"http://cms-bucket.nosdn.127.net/catchpic/9/93/93e07cea7f72e9e559ee726c86d59b9f.jpg","has_content":true,"docurl":"http://war.163.com/17/0421/17/CIIJ0KMC000181KT.html","id":6240,"time":"2017-04-21 17:48:57","title":"美援助越南1艘海警船 越战结束后首次对越＂军售＂","channelname":"war"},{"imgurl":"http://cms-bucket.nosdn.127.net/17dba6c76b4e496f8ad75b8f331eb25b20170421164436.jpeg","has_content":true,"docurl":"http://war.163.com/17/0421/16/CIIFAQEI000181KT.html","id":6212,"time":"2017-04-21 16:44:36","title":"外媒:菲律宾国防部长今日登上南海中业岛","channelname":"war"},{"imgurl":"http://cms-bucket.nosdn.127.net/catchpic/2/2f/2faa9e4db0d63b8ae6a36585e7b8dd91.jpg","has_content":true,"docurl":"http://war.163.com/17/0421/16/CIIENAHR000181KT.html","id":6200,"time":"2017-04-21 16:33:57","title":"台军被曝出现男男性侵丑闻 细节不可描述","channelname":"war"},{"imgurl":"http://cms-bucket.nosdn.127.net/catchpic/7/7b/7bc526a0a9ef4a2354174b713a6aee1f.jpg","has_content":true,"docurl":"http://war.163.com/17/0421/14/CII97FUD000181KT.html","id":6158,"time":"2017-04-21 14:57:55","title":"朝鲜首次披露＂特战军＂ 美韩斩首行动还能见效？","channelname":"war"},{"imgurl":"http://cms-bucket.nosdn.127.net/catchpic/d/d3/d3bd355db7566abe504a7ccfcd8c5ca8.jpg","has_content":true,"docurl":"http://war.163.com/17/0421/14/CII92PJM000181KT.html","id":6159,"time":"2017-04-21 14:55:21","title":"伊政府军缩小摩苏尔包围圈 老城区仍遇顽抗","channelname":"war"},{"imgurl":"http://cms-bucket.nosdn.127.net/d4bce6443a9c4ce28fae1e88d72f11c320170421144411.jpeg","has_content":true,"docurl":"http://war.163.com/17/0421/14/CII8EUGM000181KT.html","id":6124,"time":"2017-04-21 14:44:31","title":"漂泊七十七载　台湾抗战老兵终圆归乡梦","channelname":"war"},{"imgurl":"http://cms-bucket.nosdn.127.net/catchpic/d/d2/d2f9d90cb1893e8f917aaa36672593a0.jpg","has_content":true,"docurl":"http://war.163.com/17/0421/14/CII84C8E000181KT.html","id":6125,"time":"2017-04-21 14:38:45","title":"俄罗斯总统普京表示应维护二战历史的真实性","channelname":"war"},{"imgurl":"http://cms-bucket.nosdn.127.net/catchpic/e/eb/eb1ec1e233fbf018fa6a236ffc3435bb.jpg","has_content":true,"docurl":"http://war.163.com/17/0421/14/CII7U92T000181KT.html","id":6116,"time":"2017-04-21 14:35:25","title":"韩媒:韩美正式开始部署＂萨德＂ 启动基地设计环评","channelname":"war"},{"imgurl":"http://cms-bucket.nosdn.127.net/catchpic/0/00/00dec04081c0ce6f3d5ddb2eaf121b6e.jpg","has_content":true,"docurl":"http://war.163.com/17/0421/14/CII7I8C4000181KT.html","id":6117,"time":"2017-04-21 14:28:51","title":"俄媒:中企购瑞典潜艇港口 瑞典海军为啥松了口气","channelname":"war"}]
     */

    private int size;
    /**
     * imgurl : http://cms-bucket.nosdn.127.net/catchpic/0/09/09cf1a56fc5576b178484413ba18e821.jpg
     * has_content : true
     * docurl : http://war.163.com/17/0421/17/CIIJ6O91000181KT.html
     * id : 6239
     * time : 2017-04-21 17:52:17
     * title : 美媒:朝或改装潜艇可连发潜射导弹 韩称说法不实
     * channelname : war
     */

    private List<ListBean> list;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String imgurl;
        private boolean has_content;
        private String docurl;
        private int id;
        private String time;
        private String title;
        private String channelname;

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public boolean isHas_content() {
            return has_content;
        }

        public void setHas_content(boolean has_content) {
            this.has_content = has_content;
        }

        public String getDocurl() {
            return docurl;
        }

        public void setDocurl(String docurl) {
            this.docurl = docurl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getChannelname() {
            return channelname;
        }

        public void setChannelname(String channelname) {
            this.channelname = channelname;
        }
    }
}
