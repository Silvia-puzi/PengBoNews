package com.example.administrator.pengbonews.adapter;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.pengbonews.Bean.Today1Subject;
import com.example.administrator.pengbonews.MyApplication;
import com.example.administrator.pengbonews.R;
import com.example.administrator.pengbonews.databinding.ActivityTodayBinding;
import com.example.administrator.pengbonews.entity.Favorite;
import com.example.administrator.pengbonews.gen.FavoriteDao;
import com.example.administrator.pengbonews.net.TodayDetail1Loader;
import com.example.administrator.pengbonews.net.TodayDetail1Service;
import com.example.administrator.pengbonews.utils.PixelUtil;
import com.example.administrator.pengbonews.utils.ToastUtil;
import com.example.administrator.pengbonews.view.activity.MainActivity;
import com.example.administrator.pengbonews.view.fragment.MainFragment;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.adapter
 *  @文件名:   TodayActivity
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/13 0:08
 *  @描述：    TODO
 */
public class TodayActivity extends AppCompatActivity implements View.OnClickListener, OnMenuItemClickListener {
    private static final String TAG = "TodayActivity";

    private TodayDetail1Service mService;
    private TodayDetail1Loader mLoader;
    private String mTodayId;
    private List<Today1Subject> list = new ArrayList<>();
    private ImageView mIvVideoThumbnail;
    private ImageView mIvVideoStart;
    private FrameLayout mTodayDetailContainer;
    private MediaController mMediaController;
    private VideoView mTodayDetailView;

    private Boolean mIsFullScreen = false;
    /*需要隐藏显示的View*/
    private ArrayList<View> mViews;
    private String mVideoUrl;
    private ActivityTodayBinding mBinding;

    private ContextMenuDialogFragment mMenuDialogFragment;
    private FragmentManager fragmentManager;
    private String mTitle;
    private String mSource;
    private String mPtime;
    private String mTodayimg;
    private FavoriteDao mFavoriteDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_today);
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }


        Intent intent = getIntent();
        mTodayId = intent.getStringExtra("todayId");
        mTodayimg = intent.getStringExtra("todayimg");

        Log.e(TAG, "today1: "+mTodayimg );

        if (!LibsChecker.checkVitamioLibs(this))
            return;
        getNetData(mTodayId);
        fragmentManager = getSupportFragmentManager();

        initMenuFragment();
        addFragment(new MainFragment(), true, R.id.today_container);
        mFavoriteDao = MyApplication.getInstances().getDaoSession().getFavoriteDao();
    }

    private void getNetData(String todayId) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url("http://c.m.163.com/nc/article/" + mTodayId + "/full.html").build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String result = response.body().string();
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    initView(result);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initView(String result) throws JSONException {
        mIvVideoThumbnail = mBinding.ivVideoThumbnail;
        mIvVideoStart = mBinding.ivVideoStart;
        mTodayDetailContainer = mBinding.todayDetailContainer;
        mTodayDetailView = mBinding.todayDetailView;
        mMediaController = new MediaController(this, true, mTodayDetailContainer);
        mViews = new ArrayList<>();
        mViews.add(mBinding.todayDetailTime);
        mViews.add(mBinding.todayDetailTitle);
        mViews.add(mBinding.todayDetailTuicontent);
        mViews.add(mBinding.todayDetailImg);
        mViews.add(mBinding.todayDetailTuijian);
        mViews.add(mBinding.todayDetailContent);
        mViews.add(mBinding.todayDetailRecy);
        mViews.add(mBinding.todayDetailSource);
        mViews.add(mBinding.todayDetailRelative);
        mViews.add(mBinding.todayDetailTuititle);

        //上来隐藏controller
        mMediaController.setVisibility(View.GONE);
        mIvVideoStart.setOnClickListener(this);

        //back&more
        mBinding.todayBack.setOnClickListener(this);
        mBinding.todayMore.setOnClickListener(this);

        String jsonObject = new JSONObject(result).getString(mTodayId);
        JSONObject bean = new JSONObject(jsonObject);
        JSONArray img = bean.getJSONArray("img");
            if (!(img+"").equals("[]")){
                JSONObject imgbean = img.getJSONObject(0);
                String src = imgbean.getString("src");
                Log.e("ccc",src);
                Glide.with(TodayActivity.this).load(src).into(mBinding.todayDetailImg);
                Log.e(TAG, "ccc: "+"youtupian" );
            }else{
                mBinding.todayDetailImg.setVisibility(View.GONE);
                if (bean.has("video")){
                    JSONArray video = bean.getJSONArray("video");
                    JSONObject videoJSONObject = video.getJSONObject(0);
                    String mp4_url = videoJSONObject.getString("mp4_url");
                    mVideoUrl = mp4_url;
                    Log.e(TAG, "ccc: "+mVideoUrl );
                    mBinding.todayDetailContainer.setVisibility(View.VISIBLE);
                }else{
                    Log.e(TAG, "ccc: "+"shadoumeiyou" );
                    mBinding.todayDetailImgContainer.setVisibility(View.GONE);
                }
            }


        mTitle = bean.getString("title");
        mBinding.todayDetailTitle.setText(mTitle);
        mSource = bean.getString("source");
        mBinding.todayDetailSource.setText(mSource);
        mPtime = bean.getString("ptime");
        mBinding.todayDetailTime.setText(mPtime);

        String body = bean.getString("body");
        Document parse = Jsoup.parse(body);
        Elements p = parse.getElementsByTag("p");
        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        for (Element element : p) {
            String text = element.text();
            TextView textView = new TextView(TodayActivity.this);
            textView.setPadding(0, 8, 0, 0);
            textView.setText("        " + text);
            textView.setLayoutParams(params);
            mBinding.todayDetailContent.addView(textView);
        }

        JSONArray spinfo = bean.getJSONArray("spinfo");
        if (!(spinfo+"").equals("[]")) {
            String sptype = spinfo.getJSONObject(0).getString("sptype");
            String spcontent = spinfo.getJSONObject(0).getString("spcontent");
            mBinding.todayDetailTuijian.setText(sptype);
            Document parse1 = Jsoup.parse(spcontent);
            if (!parse1.getElementsByTag("b").isEmpty()) {
                mBinding.todayDetailTuititle.setText(parse1.getElementsByTag("b").text());
            } else if (!parse1.getElementsByTag("a").isEmpty()) {
                Elements a = parse1.getElementsByTag("a");
                String href = a.attr("href");
                mBinding.todayDetailTuititle.setText(a.text());
            }
            String substring = spcontent.substring(spcontent.indexOf("<br/>", spcontent.indexOf("<br/>") + 1) + 1);
            String[] split = substring.split("<br/>");
            for (int i = 0; i < split.length; i++) {
                mBinding.todayDetailTuicontent.setText(split[i]);
            }
        }else{
            mBinding.todayDetailTuicontent.setVisibility(View.GONE);
            mBinding.todayDetailTuititle.setVisibility(View.GONE);
            mBinding.todayDetailTuijian.setVisibility(View.GONE);
        }

        JSONArray relative_sys = bean.getJSONArray("relative_sys");
        if (!(relative_sys+"").equals("[]")) {
            for (int i = 0; i < relative_sys.length(); i++) {
                JSONObject jsonObject1 = relative_sys.getJSONObject(i);
                String id = jsonObject1.getString("id");
                String title1 = jsonObject1.getString("title");
                String ptime1 = jsonObject1.getString("ptime");
                String imgsrc = jsonObject1.getString("imgsrc");
                String source1 = jsonObject1.getString("source");
                Today1Subject today1Subject = new Today1Subject();
                today1Subject.setId(id);
                today1Subject.setImgsrc(imgsrc);
                today1Subject.setTitle(title1);
                today1Subject.setPtime(ptime1);
                today1Subject.setSource(source1);
                list.add(today1Subject);
            }
        }else{
            mBinding.todayDetailRecy.setVisibility(View.GONE);
            mBinding.todayDetailRelative.setVisibility(View.GONE);
        }

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TodayActivity.this);
        mBinding.todayDetailRecy.setLayoutManager(linearLayoutManager);
        //设为垂直布局
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        TodayDetailAdapter todayDetailAdapter = new TodayDetailAdapter(TodayActivity.this, list);
        mBinding.todayDetailRecy.setAdapter(todayDetailAdapter);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            //横屏
            mIsFullScreen = true;
            //去掉系统通知栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            hideViews(true);
            //调整mFlVideoGroup布局参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout
                    .LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            mTodayDetailContainer.setLayoutParams(params);
            mTodayDetailView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        } else {
            mIsFullScreen = false;
            /*清除flag,恢复显示系统状态栏*/
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            hideViews(false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout
                    .LayoutParams.MATCH_PARENT,
                    PixelUtil.dip2px(this,220));
            mTodayDetailContainer.setLayoutParams(params);
        }
    }

    private void hideViews(boolean hide) {
        if (hide) {
            for (int i = 0; i < mViews.size(); i++) {
                mViews.get(i).setVisibility(View.GONE);
            }
        } else {
            for (int i = 0; i < mViews.size(); i++) {
                mViews.get(i).setVisibility(View.VISIBLE);
            }
        }
    }

    //没有布局中没有设置返回键，只能响应硬件返回按钮，你可根据自己的意愿添加一个。若全屏就切换为小屏
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mIsFullScreen) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mMediaController.setFullScreenIconState(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.today_back){
            this.finish();
        }else if (id == R.id.today_more){
            //弹出菜单框
            if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
            }
        }else{
            mIvVideoStart.setVisibility(View.GONE);
            mIvVideoThumbnail.setVisibility(View.GONE);
            Log.e(TAG, "onClick: +"+mVideoUrl );
            mTodayDetailView.setVideoPath(mVideoUrl);
            mTodayDetailView.setMediaController(mMediaController);
            mTodayDetailView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mTodayDetailView.start();
                    mTodayDetailView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
                }
            });
            mTodayDetailView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    //停止播放
                    mTodayDetailView.stopPlayback();
                    mIvVideoStart.setVisibility(View.VISIBLE);
                }
            });

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTodayDetailView != null) {
            //清除缓存
            mTodayDetailView.destroyDrawingCache();
            //停止播放
            mTodayDetailView.stopPlayback();
            mTodayDetailView = null;
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
                Favorite fa = new Favorite(mTodayId,"today1",mTitle,mSource,mPtime,mTodayimg,"0","0","0");
                if (isFavor()){
                    mFavoriteDao.deleteByKey(mTodayId);
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
            if (favorites.get(i).getId().equals(mTodayId)){
                return true;
            }
        }
        return false;
    }

}
