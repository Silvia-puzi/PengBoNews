package com.example.administrator.pengbonews.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.pengbonews.Bean.NewsSubject;
import com.example.administrator.pengbonews.R;
import com.example.administrator.pengbonews.databinding.ItemNewsBinding;
import com.example.administrator.pengbonews.databinding.LoadmoreBinding;
import com.example.administrator.pengbonews.entity.TextNews;
import com.example.administrator.pengbonews.gen.TextNewsDao;
import com.example.administrator.pengbonews.view.activity.NewsActivity;

import java.util.List;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.view.fragment
 *  @文件名:   WarAdapter
 *  @创建者:   Administrator
 *  @创建时间:  2017/4/23 20:43
 *  @描述：    TODO
 */
public class WarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = "WarAdapter";
    private List<NewsSubject.ListBean> mData;
    private List<TextNews> mDatabaseData;
    private Context mContext;
    private final int TYPE_CONTENT = 0;
    private final int TYPE_FOOTER = 1;
    private TextNewsDao mTextNewsDao;
    private TextNews mTextNews;
    private boolean isNoNet;

    public WarAdapter(Context context, List<NewsSubject.ListBean> list,List<TextNews> dbData,boolean isNoNet){
        this.mContext = context;
        this.mData = list;
        this.mDatabaseData = dbData;
        this.isNoNet = isNoNet;
    }


    public interface onItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private onItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT){
            ItemNewsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_news, parent, false);
            return new NewsBindingHolder(binding);
        } else if(viewType == TYPE_FOOTER){
            LoadmoreBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.loadmore, parent, false);
            return new FootBindingHolder(binding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsBindingHolder){
            ImageView newsItemImg = ((NewsBindingHolder) holder).getBinding().newsItemImg;
            ItemNewsBinding binding = ((NewsBindingHolder) holder).getBinding();
                final NewsSubject.ListBean listBean = mData.get(position);
                //加载图片glide
                Glide.with(mContext).load(listBean.getImgurl()).into(newsItemImg);
                ((NewsBindingHolder) holder).getBinding().newsItemTitle.setText(listBean.getTitle());
                ((NewsBindingHolder) holder).getBinding().newsItemTime.setText(listBean.getTime());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = holder.getLayoutPosition();
                        int id = listBean.getId();
                        String imgurl = listBean.getImgurl();
                        Intent intent = new Intent(mContext, NewsActivity.class);
                        intent.putExtra("id",id);
                        intent.putExtra("imgUrl",imgurl);
                        mContext.startActivity(intent);
                    }
                });
            }


//        }
    }


    @Override
    public int getItemCount() {
        return mData.size() == 0 ? 0 : mData.size() + 1  ;
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
