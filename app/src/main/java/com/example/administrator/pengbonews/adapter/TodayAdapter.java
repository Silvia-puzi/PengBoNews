package com.example.administrator.pengbonews.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.administrator.pengbonews.Bean.TodaySubject;
import com.example.administrator.pengbonews.R;
import com.example.administrator.pengbonews.databinding.LoadmoreBinding;
import com.example.administrator.pengbonews.databinding.TodayNormalBinding;
import com.example.administrator.pengbonews.databinding.TodayPicBinding;

import java.util.List;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.view.fragment
 *  @文件名:   TodayAdapter
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/10 13:20
 *  @描述：    TODO
 */
public class TodayAdapter extends RecyclerView.Adapter{
    private static final String TAG = "TodayAdapter";
    private Context mContext;
    private List<TodaySubject.T1467284926140Bean> mList;
    private final int TYPE_NORMAL = 0;
    private final int TYPE_PIC = 1;
    private final int TYPE_FOOTER = 2;
    private final int TYPE_TITLE = 3;

    public TodayAdapter(Context context , List<TodaySubject.T1467284926140Bean> list){
        this.mContext = context;
        this.mList = list;
    }
    public interface onItemClickListener{
        void onItemClick(View view , int position);
    }

    private VideoAdapter.onItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(VideoAdapter.onItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_PIC){
            TodayPicBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.today_pic, parent, false);
            return new TodayPicHolder(binding);
        }else if (viewType == TYPE_NORMAL){
            TodayNormalBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.today_normal, parent, false);
            return new TodayNormalHolder(binding);
        }else if (viewType == TYPE_FOOTER){
            LoadmoreBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.loadmore, parent, false);
            return new FootBindingHolder(binding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TodayPicHolder){
            //拿到控件
            TodaySubject.T1467284926140Bean bean = mList.get(position);
            TodayPicBinding binding = ((TodayPicHolder) holder).getBinding();
            binding.picTitle.setText(bean.getTitle());
            binding.picSource.setText(bean.getSource());
            binding.picReply.setText(bean.getReplyCount()+"跟帖");
            Glide.with(mContext).load(bean.getImgsrc()).into(binding.picImg1);
            Glide.with(mContext).load(bean.getImgextra().get(0).getImgsrc()).into(binding.picImg2);
            Glide.with(mContext).load(bean.getImgextra().get(1).getImgsrc()).into(binding.picImg3);
        }else if (holder instanceof TodayNormalHolder){
            TodaySubject.T1467284926140Bean bean = mList.get(position);
            TodayNormalBinding binding = ((TodayNormalHolder) holder).getBinding();
            binding.normalTitle.setText(bean.getTitle());
            Glide.with(mContext).load(bean.getImgsrc()).into(binding.normalImg);
            binding.normalReply.setText(bean.getReplyCount()+"跟帖");
            binding.normalSource.setText(bean.getSource());
        }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TodaySubject.T1467284926140Bean bean = mList.get(position);
                    Intent intent;
                    if(bean.getSkipID() != null && bean.getSkipType().equals("photoset")){
                        intent = new Intent(mContext, TodayActivity2.class);
                        String skipID = bean.getSkipID();
                        String substring = skipID.substring(4);
                        String mReplace = substring.replace("|", "/");
                        intent.putExtra("todaySkipId",mReplace);
                    }else {
                        intent = new Intent(mContext,TodayActivity.class);
                        intent.putExtra("todayId",bean.getPostid());

                    }
                    intent.putExtra("todayimg",bean.getImgsrc());
                    mContext.startActivity(intent);
                }
            });



    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 0 : mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()){
            return TYPE_FOOTER;
        }else if (mList.get(position).getImgextra() != null){
            return TYPE_PIC;
        }else if (mList.get(position).getImgextra() == null){
            return TYPE_NORMAL;
        }
        return 0;
    }
}
