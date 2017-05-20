package com.example.administrator.pengbonews.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.administrator.pengbonews.Bean.Today2Subject;
import com.example.administrator.pengbonews.MyApplication;
import com.example.administrator.pengbonews.R;
import com.example.administrator.pengbonews.databinding.ActivityToday2Binding;
import com.example.administrator.pengbonews.entity.Favorite;
import com.example.administrator.pengbonews.gen.FavoriteDao;
import com.example.administrator.pengbonews.net.RetrofitServiceManager;
import com.example.administrator.pengbonews.net.TodayDetail2Loader;
import com.example.administrator.pengbonews.net.TodayDetail2Service;
import com.example.administrator.pengbonews.utils.ToastUtil;
import com.example.administrator.pengbonews.view.activity.MainActivity;
import com.example.administrator.pengbonews.view.fragment.MainFragment;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.adapter
 *  @文件名:   TodayActivity2
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/13 10:24
 *  @描述：    TODO
 */
public class TodayActivity2 extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener, OnMenuItemClickListener {
    private static final String TAG = "TodayActivity2";
    private ActivityToday2Binding mBinding;
    private TodayDetail2Service mTodayDetail2Service;
    private TodayDetail2Loader mTodayDetail2Loader;
    private ViewPager mToday2Viewpager;
    private TextView mToday2Content;
    private TextView mToday2Page;
    private TextView mToday2Title;
    private List<Today2Subject.PhotosBean> mPhotos;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private FragmentManager fragmentManager;
    private FavoriteDao mFavoriteDao;
    private String mTodayimg;
    private String mSource;
    private String mDatatime;
    private String mSetname;
    private String mTodaySkipId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_today2);

        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        Intent intent = getIntent();
        mTodaySkipId = intent.getStringExtra("todaySkipId");
        mTodayimg = intent.getStringExtra("todayimg");
        fragmentManager = getSupportFragmentManager();
        initView();
        getNetData(mTodaySkipId);

        initMenuFragment();
        addFragment(new MainFragment(), true, R.id.today2_container);
        mFavoriteDao = MyApplication.getInstances().getDaoSession().getFavoriteDao();
    }

    private void initView() {
        mToday2Viewpager = mBinding.today2Viewpager;
        mToday2Content = mBinding.today2Content;
        mToday2Page = mBinding.today2Page;
        mToday2Title = mBinding.today2Title;
        mToday2Viewpager.addOnPageChangeListener(this);
        mBinding.today2Back.setOnClickListener(this);
        mBinding.today2More.setOnClickListener(this);
    }

    private void getNetData(String id) {
        //获取接口实例
        mTodayDetail2Service = RetrofitServiceManager.getInstance().create7(TodayDetail2Service.class);
        mTodayDetail2Loader = new TodayDetail2Loader();
        net(id);
    }

    private void net(String id) {
        mTodayDetail2Loader.getToday2Subject(id).subscribe(new Action1<Today2Subject>() {
            @Override
            public void call(Today2Subject today2Subject) {
                mSetname = today2Subject.getSetname();
                mSource = today2Subject.getSource();
                mDatatime = today2Subject.getDatatime();
                mToday2Title.setText(mSetname);
                mPhotos = today2Subject.getPhotos();
                TodayDetail2Adapter todayDetail2Adapter = new TodayDetail2Adapter(TodayActivity2.this, mPhotos);
                mToday2Viewpager.setAdapter(todayDetail2Adapter);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ToastUtil.toast(TodayActivity2.this,throwable.getMessage());
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mToday2Page.setText((position + 1) + " / " + mPhotos.size());
        mToday2Content.setText(mPhotos.get(position).getNote());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.today2_back:
                this.finish();
                break;
            case R.id.today2_more:
                //弹出菜单框
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
    }

    private void addFragment(MainFragment fragment, boolean b, int containerId) {
        invalidateOptionsMenu();
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(containerId, fragment, backStackName)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (b)
                transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject page = new MenuObject("返回主页");
        page.setResource(R.drawable.page);


        MenuObject like = new MenuObject("收藏");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.star_favour);
        like.setBitmap(b);

        MenuObject share = new MenuObject("分享");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.share));
        share.setDrawable(bd);

        menuObjects.add(page);
        menuObjects.add(like);
        menuObjects.add(share);
        return menuObjects;

    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Intent intent;
        switch (position){
            case 0:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                //跳搜索
                break;
            case 1:
                //判断表里是否有
                Favorite fa = new Favorite(mTodaySkipId,"today2",mSetname,mSource,mDatatime,mTodayimg,"0","0","0");
                if (isFavor()){
                    mFavoriteDao.deleteByKey(mTodaySkipId);
                    ToastUtil.toast(this,"取消收藏");
                }else{
                    mFavoriteDao.insert(fa);
                    ToastUtil.toast(this,"收藏成功");
                }
                break;
            case 2:
                intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "This is my Share text.");
                // 创建一个选择器
                startActivity(Intent.createChooser(intent, "分享到"));
                //跳分享
                break;
        }
    }

    private boolean isFavor() {
        List<Favorite> favorites = mFavoriteDao.loadAll();
        for (int i = 0; i < favorites.size(); i++) {
            if (favorites.get(i).getId().equals(mTodaySkipId)){
                return true;
            }
        }
        return false;
    }
}
