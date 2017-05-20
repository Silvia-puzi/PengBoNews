package com.example.administrator.pengbonews.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.example.administrator.pengbonews.MyApplication;
import com.example.administrator.pengbonews.R;
import com.example.administrator.pengbonews.adapter.FavorAdapter;
import com.example.administrator.pengbonews.databinding.ActivityFavoriteBinding;
import com.example.administrator.pengbonews.entity.Favorite;
import com.example.administrator.pengbonews.gen.FavoriteDao;

import java.util.List;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.view.activity
 *  @文件名:   FavoritActivity
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/18 15:57
 *  @描述：    TODO
 */
public class FavoritActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "FavoritActivity";
    private ActivityFavoriteBinding mBinding;
    private FavoriteDao mFavoriteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_favorite);
        mBinding.favorBack.setOnClickListener(this);
        mFavoriteDao = MyApplication.getInstances().getDaoSession().getFavoriteDao();
        List<Favorite> favorites = mFavoriteDao.loadAll();

        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        RecyclerView favorRecy = mBinding.favorRecy;
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        favorRecy.setLayoutManager(linearLayoutManager);
        //设为垂直布局
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);

        FavorAdapter adapter = new FavorAdapter(this,favorites);
        favorRecy.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        this.finish();
    }
}
