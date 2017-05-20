package com.example.administrator.pengbonews.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/*
 *  @项目名：  PBNews 
 *  @包名：    com.example.administrator.pbnews.view.activity
 *  @文件名:   ViewAdapter
 *  @创建者:   Administrator
 *  @创建时间:  2017/4/16 22:57
 *  @描述：    TODO
 */
public class ViewAdapter extends PagerAdapter{
    private static final String TAG = "ViewAdapter";
    private final Context context;
    private int[] imgArr;

    public ViewAdapter(Context context,int[] arr){
        this.context = context;
        this.imgArr = arr;
    }

    @Override
    public int getCount() {
        return imgArr.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view =  new ImageView(this.context);
        view.setImageResource(imgArr[position]);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(view);
        return view;
    }


}
