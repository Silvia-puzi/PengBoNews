package com.example.administrator.pengbonews.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.pengbonews.Bean.VideoSubject;
import com.example.administrator.pengbonews.R;
import com.example.administrator.pengbonews.databinding.ItemVideoBinding;
import com.example.administrator.pengbonews.databinding.LoadmoreBinding;
import com.example.administrator.pengbonews.utils.VideoTimeUtils;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  PengBoNews
 *  @包名：    com.example.administrator.pengbonews.adapter
 *  @文件名:   VideoAdapter
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/5 21:21
 *  @描述：    TODO
 */
public class VideoAdapter extends RecyclerView.Adapter {
    private static final String TAG = "VideoAdapter";
    private Context mContext;
    private List<VideoSubject.视频Bean> list = new ArrayList<>();
    private final int TYPE_CONTENT = 0;
    private final int TYPE_FOOTER = 1;

    public VideoAdapter(Context context , List<VideoSubject.视频Bean> list){
        this.mContext = context;
        this.list = list;
    }

    public interface onItemClickListener{
        void onItemClick(View view , int position);
    }

    private onItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT){
            ItemVideoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_video, parent, false);
            return new VideoBindingHolder(binding);
        }else if (viewType == TYPE_FOOTER){
            LoadmoreBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.loadmore, parent, false);
            return new FootBindingHolder(binding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VideoBindingHolder){
            //拿到控件
            ImageView videoItemImg = ((VideoBindingHolder) holder).getBinding().videoImg;
            TextView videoTimes = ((VideoBindingHolder) holder).getBinding().videoTimes;
            TextView videoDuration = ((VideoBindingHolder) holder).getBinding().videoDuration;
            TextView videoTitle = ((VideoBindingHolder) holder).getBinding().videoTitle;
            ImageView videoUserimg = ((VideoBindingHolder) holder).getBinding().videoUserimg;
            TextView videoUsername = ((VideoBindingHolder) holder).getBinding().videoUsername;

            final VideoSubject.视频Bean bean = list.get(position);
            Glide.with(mContext).load(bean.getCover()).into(videoItemImg);
            Glide.with(mContext).load(bean.getVideoTopic().getTopic_icons()).into(videoUserimg);
            videoTimes.setText("  / "+bean.getPlayCount()+"次播放");
            videoDuration.setText(VideoTimeUtils.transTime(bean.getLength()));
            videoTitle.setText(bean.getTitle());
            videoUsername.setText(bean.getTopicName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(mContext, VideoActivity.class);
                    intent.putExtra("title",bean.getTitle());//string
                    intent.putExtra("commentId",bean.getReplyid());//string
                    intent.putExtra("videoUrl",bean.getMp4_url());//string
                    intent.putExtra("videoDuration",bean.getLength());//int
                    intent.putExtra("videoIcon",bean.getTopicImg());//string
                    intent.putExtra("name",bean.getTopicName());
                    intent.putExtra("videoImg",bean.getCover());
                    intent.putExtra("videoTime",bean.getPtime());
                    mContext.startActivity(intent);

                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()){
            return TYPE_FOOTER;
        }else {
            return TYPE_CONTENT;
        }
    }
}
