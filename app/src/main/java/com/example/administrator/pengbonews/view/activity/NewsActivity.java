package com.example.administrator.pengbonews.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.pengbonews.Bean.NewsDetailSubject;
import com.example.administrator.pengbonews.MyApplication;
import com.example.administrator.pengbonews.R;
import com.example.administrator.pengbonews.databinding.ActivityNewsBinding;
import com.example.administrator.pengbonews.databinding.ContentNewsBinding;
import com.example.administrator.pengbonews.entity.Favorite;
import com.example.administrator.pengbonews.gen.FavoriteDao;
import com.example.administrator.pengbonews.net.NewsDetailLoader;
import com.example.administrator.pengbonews.net.NewsDetailService;
import com.example.administrator.pengbonews.net.RetrofitServiceManager;
import com.example.administrator.pengbonews.utils.SPUtils;
import com.example.administrator.pengbonews.utils.ToastUtil;
import com.example.administrator.pengbonews.view.fragment.MainFragment;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/*
 *  @项目名：  PengBoNews
 *  @包名：    com.example.administrator.pengbonews.view.activity
 *  @文件名:   NewsActivity
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/10 18:15
 *  @描述：    TODO
 */
public class NewsActivity extends AppCompatActivity implements View.OnClickListener, OnMenuItemClickListener {
    private static final String TAG = "NewsActivity";
    ActivityNewsBinding binding;
    private NewsDetailService mNewsDetailService;
    private NewsDetailLoader mNewsDetailLoader;
    private List<NewsDetailSubject> list = new ArrayList<>();
    private ContextMenuDialogFragment mMenuDialogFragment;
    private FragmentManager fragmentManager;
    private int mId;
    private String mImgUrl;
    private String mTitle;
    private String mFrom;
    private String mTime;
    private FavoriteDao mFavoriteDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news);
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        Intent intent = getIntent();
        mId = intent.getIntExtra("id",0);
        mImgUrl = intent.getStringExtra("imgUrl");
        getNetData(mId);
        fragmentManager = getSupportFragmentManager();

        initMenuFragment();
        addFragment(new MainFragment(), true, R.id.news_container);
        mFavoriteDao = MyApplication.getInstances().getDaoSession().getFavoriteDao();
    }

    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    private boolean isNoNetWorkConnect(){
        NetworkInfo.State wifiState = null;
        NetworkInfo.State mobileState = null;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context
                .CONNECTIVITY_SERVICE);
        wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

        //没有网络连接
        if(wifiState != null && mobileState != null
                && NetworkInfo.State.CONNECTED != wifiState
                && NetworkInfo.State.CONNECTED != mobileState){
            return true;
        }
        return false;
    }

    private void getNetData(final int id) {
        if (isNoNetWorkConnect()){
            ContentNewsBinding contentMain = binding.contentMain;
            contentMain.newsDetailScroll.setVisibility(View.GONE);
            binding.contentFail.loadingError.setVisibility(View.VISIBLE);
            binding.contentFail.loadingError.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getNetData(id);
                }
            });
        }else{
            Log.e(TAG, "getNetData: "+id );
            final Context baseContext = this.getBaseContext();
            //获取接口实例
            mNewsDetailService = RetrofitServiceManager.getInstance().create4(NewsDetailService.class);
            mNewsDetailLoader = new NewsDetailLoader();
            mNewsDetailLoader.getTodaySubject(id)
                    .subscribe(new Action1<NewsDetailSubject>() {
                        @Override
                        public void call(NewsDetailSubject videoSubject) {
                            initView(videoSubject);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            ToastUtil.toast(baseContext,throwable.getMessage());
                        }
                    });
        }
    }

    private void initView(NewsDetailSubject videoSubject) {
        mTitle = videoSubject.getTitle();
        mFrom = videoSubject.getFrom();
        mTime = videoSubject.getTime();
        binding.contentMain.newsDetailTitle.setText(mTitle);
        binding.contentMain.newsDetailName.setText(mFrom);
        binding.contentMain.newsDetailTime.setText(mTime);
        binding.newsBack.setOnClickListener(this);
        binding.newsMore.setOnClickListener(this);
        String content = videoSubject.getContent();
        Document parse = Jsoup.parse(content);
        Elements p = parse.getElementsByTag("p");
        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        for (Element element: p) {
            boolean img = element.getElementsByTag("img").isEmpty();
            if (!img){
                String src = element.getElementsByTag("img").attr("src");
                Log.e(TAG, "initView: "+src );
                ImageView imageView = new ImageView(this);
                Glide.with(this).load(src).into(imageView);
                imageView.setLayoutParams(params);
                binding.contentMain.newsDetailContainer.addView(imageView);
            }
            String text = element.text();
            Log.e(TAG, "initView: "+text );
            TextView textView = new TextView(this);
            textView.setPadding(0,3,0,0);
            textView.setText("        "+text);
            textView.setLayoutParams(params);
            binding.contentMain.newsDetailContainer.addView(textView);
        }


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.news_back:
                this.finish();
                break;
            case R.id.news_more:
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
                //判断有无登录
                boolean login = SPUtils.getBoolean(this, "login");
                if (login){
                    //判断表里是否有
                    Favorite fa = new Favorite(mId + "","news", mTitle, mFrom, mTime, mImgUrl,"0","0","0");
                    if (isFavor()){
                        mFavoriteDao.deleteByKey(mId+"");
                        ToastUtil.toast(this,"取消收藏");
                    }else{
                        mFavoriteDao.insert(fa);
                        ToastUtil.toast(this,"收藏成功");
                    }
                }else{
                    intent = new Intent(this,LoginActivity.class);
                    startActivity(intent);
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
            if (favorites.get(i).getId().equals(mId+"")){
                return true;
            }
        }
        return false;
    }
}
