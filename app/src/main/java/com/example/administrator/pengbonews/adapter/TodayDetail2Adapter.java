package com.example.administrator.pengbonews.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.pengbonews.Bean.Today2Subject;

import java.util.List;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.adapter
 *  @文件名:   TodayDetail2Adapter
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/13 23:30
 *  @描述：    TODO
 */
public class TodayDetail2Adapter extends PagerAdapter{
    private static final String TAG = "TodayDetail2Adapter";
    private Context mContext;
    private List<Today2Subject.PhotosBean> mList;

    public TodayDetail2Adapter(Context context, List<Today2Subject.PhotosBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        Glide.with(mContext).load(mList.get(position).getImgurl()).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
