package com.example.administrator.pengbonews.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.pengbonews.R;
import com.example.administrator.pengbonews.databinding.ItemFavorBinding;
import com.example.administrator.pengbonews.entity.Favorite;
import com.example.administrator.pengbonews.view.activity.NewsActivity;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.adapter
 *  @文件名:   FavorAdapter
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/18 16:24
 *  @描述：    TODO
 */
public class FavorAdapter extends RecyclerView.Adapter{
    private static final String TAG = "FavorAdapter";
    private Context mContext;
    private List<Favorite> mList = new ArrayList<>();

    public FavorAdapter(Context context,List<Favorite> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFavorBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_favor, parent, false);
        return new FavorBindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemFavorBinding binding = ((FavorBindingHolder) holder).getBinding();
        ImageView favorImg = binding.favorImg;
        TextView favorTitle = binding.favorTitle;
        TextView favorSource = binding.favorSource;
        TextView favorTime = binding.favorTime;


        Favorite favorite = mList.get(position);
        String img = favorite.getImg();
        Log.e(TAG, "favor: "+ favorite.getId()+img +"..."+favorite.getType());

        Glide.with(mContext).load(img).into(favorImg);
        favorTitle.setText(favorite.getTitle());
        favorSource.setText(favorite.getSource());
        favorTime.setText(favorite.getTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Favorite favorite1 = mList.get(position);
                String type = favorite1.getType();
                String id = favorite1.getId();
                String img = favorite1.getImg();
                Intent intent;
                if (type.equals("news")){
                    intent = new Intent(mContext, NewsActivity.class);
                    int i = Integer.parseInt(id);
                    intent.putExtra("id",i);
                    intent.putExtra("imgUrl",img);
                }else if (type.equals("today1")){
                    intent = new Intent(mContext,TodayActivity.class);
                    intent.putExtra("todayId",id);
                }else if (type.equals("today2")){
                    intent = new Intent(mContext,TodayActivity2.class);
                    intent.putExtra("todaySkipId",id);
                }else{
                    intent = new Intent(mContext,VideoActivity.class);
                    intent.putExtra("title",favorite1.getTitle());
                    intent.putExtra("commentId",favorite1.getTitle());//
                    intent.putExtra("videoUrl",favorite1.getVideoUrl());
                    intent.putExtra("videoIcon",favorite1.getVideoIcon());
                    intent.putExtra("videoImg",favorite1.getImg());
                    intent.putExtra("videoTime",favorite1.getTime());
                    intent.putExtra("name",favorite1.getSource());
                }

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
