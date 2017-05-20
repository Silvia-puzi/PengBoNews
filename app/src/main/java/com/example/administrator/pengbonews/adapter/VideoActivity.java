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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.pengbonews.Bean.CommentSubject;
import com.example.administrator.pengbonews.MyApplication;
import com.example.administrator.pengbonews.R;
import com.example.administrator.pengbonews.databinding.VideoDetailBinding;
import com.example.administrator.pengbonews.entity.Favorite;
import com.example.administrator.pengbonews.gen.FavoriteDao;
import com.example.administrator.pengbonews.net.CommentLoader;
import com.example.administrator.pengbonews.net.CommentService;
import com.example.administrator.pengbonews.net.RetrofitServiceManager;
import com.example.administrator.pengbonews.utils.CircleImageView;
import com.example.administrator.pengbonews.utils.PixelUtil;
import com.example.administrator.pengbonews.utils.ToastUtil;
import com.example.administrator.pengbonews.view.activity.MainActivity;
import com.example.administrator.pengbonews.view.fragment.MainFragment;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import rx.functions.Action1;


/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.adapter
 *  @文件名:   VideoActivity
 *  @创建者:   Administrator
 *  @创建时间:  2017/5/11 0:39
 *  @描述：    TODO
 */
public class VideoActivity extends AppCompatActivity implements View.OnClickListener, OnMenuItemClickListener {
    private static final String TAG = "VideoActivity";
    private VideoDetailBinding mVideoDetailBinding;
    private VideoView mVideoDetailView;
    private TextView mVideoDetailTitle;
    private CircleImageView mVideoDetailImg;
    private RecyclerView mVideoDetailComment;
    private List<CommentSubject> list = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private CommentAdapter mCommentAdapter;
    private ImageView mIvVideoThumbnail;
    private ImageView mIvVideoStart;
    //当前是否为全屏
    private Boolean mIsFullScreen = false;
    /*需要隐藏显示的View*/
    private ArrayList<View> mViews;
    private MediaController mController;
    private String mVideoUrl;
    private FrameLayout mVideoDetailContainer;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private FragmentManager fragmentManager;
    private FavoriteDao mFavoriteDao;
    private String mTitle;
    private String mVideoImg;
    private String mVideoTime;
    private String mName;
    private String mVideoIcon;
    private String mCommentId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!LibsChecker.checkVitamioLibs(this))
            return;

        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        initView();
        getNetData();
        fragmentManager = getSupportFragmentManager();

        initMenuFragment();
        addFragment(new MainFragment(), true, R.id.video_container);
        mFavoriteDao = MyApplication.getInstances().getDaoSession().getFavoriteDao();
    }

    private void initView() {
        mVideoDetailBinding = DataBindingUtil.setContentView(this, R.layout.video_detail);
        mVideoDetailView = mVideoDetailBinding.videoDetailView;
        mVideoDetailTitle = mVideoDetailBinding.videoDetailTitle;
        mVideoDetailImg = mVideoDetailBinding.videoDetailImg;
        mVideoDetailComment = mVideoDetailBinding.videoDetailComment;
        mIvVideoThumbnail = mVideoDetailBinding.ivVideoThumbnail;
        mIvVideoStart = mVideoDetailBinding.ivVideoStart;
        mVideoDetailContainer = mVideoDetailBinding.videoDetailContainer;
        mController = new MediaController(this, true, mVideoDetailContainer);
        mViews = new ArrayList<>();
        mViews.add(mVideoDetailTitle);
        mViews.add(mVideoDetailImg);
        mViews.add(mVideoDetailComment);
        mViews.add(mVideoDetailBinding.videoDetailName);
        //上来隐藏controller
        mController.setVisibility(View.GONE);
        mIvVideoStart.setOnClickListener(this);
        mVideoDetailBinding.videoDetailBack.setOnClickListener(this);
        mVideoDetailBinding.videoDetailMore.setOnClickListener(this);

    }

    public void getNetData() {
        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        mCommentId = intent.getStringExtra("commentId");
        mVideoUrl = intent.getStringExtra("videoUrl");
        mVideoIcon = intent.getStringExtra("videoIcon");
        mVideoImg = intent.getStringExtra("videoImg");
        mVideoTime = intent.getStringExtra("videoTime");
        mName = intent.getStringExtra("name");
        mVideoDetailTitle.setText(mTitle);
        Glide.with(this).load(mVideoIcon).into(mVideoDetailImg);
        mVideoDetailBinding.videoDetailName.setText(mName);

        //设置布局管理器
        mLayoutManager = new LinearLayoutManager(this);
        mVideoDetailComment.setLayoutManager(mLayoutManager);
        //设为垂直布局
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置adapter
        mCommentAdapter = new CommentAdapter(this, list);
        mVideoDetailComment.setAdapter(mCommentAdapter);
        setRecy(mCommentId);
    }

    private void setRecy(String commentId) {
            //获取接口实例
            RetrofitServiceManager.getInstance().create5(CommentService.class);
            CommentLoader commentLoader = new CommentLoader();
            commentLoader.getVideoSubject().subscribe(new Action1<CommentSubject>() {
            @Override
            public void call(CommentSubject newsSubject) {
                list.add(newsSubject);
                mCommentAdapter.notifyDataSetChanged();
                mCommentAdapter.notifyItemRemoved(mCommentAdapter.getItemCount());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ToastUtil.toast(VideoActivity.this,throwable.getMessage());
            }
        });
}

    //记得在activity中声明
    // android:screenOrientation="portrait" 强行设置为竖屏，关闭自动旋转屏幕
    //android:configChanges="orientation|keyboardHidden|screenLayout|screenSize"注册配置变化事件


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
            mVideoDetailContainer.setLayoutParams(params);
            //原视频大小
//            public static final int VIDEO_LAYOUT_ORIGIN = 0;
            //最优选择，由于比例问题还是会离屏幕边缘有一点间距，所以最好把父View的背景设置为黑色会好一点
//            public static final int VIDEO_LAYOUT_SCALE = 1;
            //拉伸，可能导致变形
//            public static final int VIDEO_LAYOUT_STRETCH = 2;
            //会放大可能超出屏幕
//            public static final int VIDEO_LAYOUT_ZOOM = 3;
            //效果还是竖屏大小（字面意思是填充父View）
//            public static final int VIDEO_LAYOUT_FIT_PARENT = 4;
            mVideoDetailView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        } else {
            mIsFullScreen = false;
            /*清除flag,恢复显示系统状态栏*/
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            hideViews(false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout
                    .LayoutParams.MATCH_PARENT,
                    PixelUtil.dip2px(this,220));
            mVideoDetailContainer.setLayoutParams(params);
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
            mController.setFullScreenIconState(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.video_detail_back){
            this.finish();
        }else if (id == R.id.video_detail_more){
            //弹出菜单框
            if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
            }
        }else{
            mIvVideoStart.setVisibility(View.GONE);
            mIvVideoThumbnail.setVisibility(View.GONE);
            mVideoDetailView.setVideoPath(mVideoUrl);
            mVideoDetailView.setMediaController(mController);
            mVideoDetailView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mVideoDetailView.start();
                    mVideoDetailView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
                }
            });
            mVideoDetailView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    //停止播放
                    mVideoDetailView.stopPlayback();
                    mIvVideoStart.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoDetailView != null) {
            //清除缓存
            mVideoDetailView.destroyDrawingCache();
            //停止播放
            mVideoDetailView.stopPlayback();
            mVideoDetailView = null;
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
                Favorite fa = new Favorite(mVideoUrl,"video", mTitle,mName,mVideoTime,mVideoImg,mVideoUrl,mVideoIcon,mCommentId);
                if (isFavor()){
                    mFavoriteDao.deleteByKey(mVideoUrl);
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
            if (favorites.get(i).getId().equals(mVideoUrl)){
                return true;
            }
        }
        return false;
    }
}
