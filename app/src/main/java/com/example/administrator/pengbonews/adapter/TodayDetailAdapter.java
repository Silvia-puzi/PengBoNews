package com.example.administrator.pengbonews.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.administrator.pengbonews.Bean.Today1Subject;
import com.example.administrator.pengbonews.R;
import com.example.administrator.pengbonews.databinding.ItemTodayDetailBinding;

import java.util.List;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.adapter
 *  @文件名:   TodayDetailAdapter
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/13 12:05
 *  @描述：    TODO
 */
public class TodayDetailAdapter extends RecyclerView.Adapter {
    private static final String TAG = "TodayDetailAdapter";

    private Context mContext;
    private List<Today1Subject> mList;

    public TodayDetailAdapter(Context context, List<Today1Subject> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTodayDetailBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_today_detail, parent, false);
        return new TodayDetailHolder(binding);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Log.e(TAG, "onBindViewHolder: "+"没有相关推荐吗" );
        ItemTodayDetailBinding binding = ((TodayDetailHolder) holder).getBinding();
        final Today1Subject bean = mList.get(position);
        Glide.with(mContext).load(bean.getImgsrc()).into(binding.itemTodayImg);
        binding.itemTodayTitle.setText(bean.getTitle());
        binding.itemTodaySource.setText(bean.getSource());
        binding.itemTodayTime.setText(bean.getPtime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,TodayActivity.class);
                    intent.putExtra("todayId",bean.getId());
                    intent.putExtra("todayimg",bean.getImgsrc());
                    mContext.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
