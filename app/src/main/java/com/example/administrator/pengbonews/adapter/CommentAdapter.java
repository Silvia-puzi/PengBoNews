package com.example.administrator.pengbonews.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.pengbonews.Bean.CommentSubject;
import com.example.administrator.pengbonews.R;
import com.example.administrator.pengbonews.databinding.ItemVideoDetailCommentBinding;
import com.example.administrator.pengbonews.utils.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.adapter
 *  @文件名:   CommentAdapter
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/11 18:02
 *  @描述：    TODO
 */
public class CommentAdapter extends RecyclerView.Adapter {
    private static final String TAG = "CommentAdapter";
    private Context mContext;
    private List<CommentSubject> list = new ArrayList<>();
    private final int TYPE_CONTENT = 0;

    public CommentAdapter(Context context , List<CommentSubject> list){
        this.mContext = context;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT){
            ItemVideoDetailCommentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_video_detail_comment, parent, false);
            return new CommentHolder(binding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommentHolder){
            //拿到控件
            CircleImageView itemVideoImg = ((CommentHolder) holder).getBinding().itemVideoImg;
            TextView itemVideoName = ((CommentHolder) holder).getBinding().itemVideoName;
            TextView itemVideoComment = ((CommentHolder) holder).getBinding().itemVideoComment;
            TextView itemVideoTime = ((CommentHolder) holder).getBinding().itemVideoTime;
//                       String s = list.get(0).getCommentIds().get((position));
//
//            Log.e(TAG, "onBindViewHolder: "+s );
//            HashMap<String, CommentSubject.CommentsBean.CommentUser> user = list.get(0).getCommentsInfo().getUser();
//            Log.e(TAG, "onBindViewHolder: "+user.get(s) );
//            CommentSubject.CommentsBean.CommentUser commentUser = user.get(s);
//
//            if (commentUser.getUserInfo().getAvatar()!=null || commentUser.getUserInfo().getAvatar() != ""){
                Glide.with(mContext).load("http://cms-bucket.nosdn.127.net/d7d7045d4ff746b6bc6bfe5d5dc6f1a020161214184024.jpg").into(itemVideoImg);
//            }
            itemVideoName.setText("地中海强者");
            itemVideoComment.setText("为什么两个人突然乱了手脚，语无伦次的感觉，明显就是紧张，如果不是刚开始应该是很惊讶，而不是紧张");
            itemVideoTime.setText("2017-04-09 04:27:47");

        }

    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
            return TYPE_CONTENT;
    }


    public class CommentHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "NewsBindingHolder";
        private ItemVideoDetailCommentBinding mItemNewsBinding;

        public CommentHolder(ItemVideoDetailCommentBinding binding) {
            super(binding.getRoot());
            this.mItemNewsBinding = binding;
        }

        public ItemVideoDetailCommentBinding getBinding(){
            return mItemNewsBinding;
        }

        public void setBinding(ItemVideoDetailCommentBinding binding){
            this.mItemNewsBinding = binding;
        }
    }

}
