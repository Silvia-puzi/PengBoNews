package com.example.administrator.pengbonews.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.administrator.pengbonews.MyApplication;
import com.example.administrator.pengbonews.R;
import com.example.administrator.pengbonews.databinding.ActivityMainBinding;
import com.example.administrator.pengbonews.gen.FavoriteDao;
import com.example.administrator.pengbonews.utils.Util;
import com.example.administrator.pengbonews.view.fragment.NewsFragment;
import com.example.administrator.pengbonews.view.fragment.VideoFragment;
import com.example.administrator.pengbonews.view.fragment.WechatFragment;

import java.util.ArrayList;
import java.util.List;

import io.vov.vitamio.Vitamio;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding binding;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    private FavoriteDao mFavoriteDao;
    private Fragment currentFragment = new NewsFragment();
    private List<Fragment> fragments = new ArrayList<>();
    private int currentIndex = 0;
    //当前显示的fragment
    private static final String CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW";
    private FragmentManager mSupportFragmentManager;
    private int theme = 0;

    @Override
    protected void onResume() {
        Log.d("aaa","onResume");
        super.onResume();
        if(theme==Util.getAppTheme(this)){

        }else{
            reload();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("aaa","onDestroy");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState==null){
            theme=Util.getAppTheme(this);
        }else{
            theme=savedInstanceState.getInt("theme");
        }
        setTheme(theme);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        new Thread(){
            @Override
            public void run() {
                Vitamio.initialize(getApplicationContext());
            }
        }.start();

        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        toolbar = binding.appbarMain.toolbar;
        toolbar.setLogo(R.drawable.appicon);
        toolbar.setTitle("蓬勃新闻");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.user);

        drawer = binding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

//        changeFragment(new NewsFragment());
        binding.appbarMain.contentMain.setPresenter(new Presenter());

        mFavoriteDao = MyApplication.getInstances().getDaoSession().getFavoriteDao();

        mSupportFragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) { // “内存重启”时调用

            //获取“内存重启”时保存的索引下标
            currentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT,0);

            fragments.removeAll(fragments);
            fragments.add(mSupportFragmentManager.findFragmentByTag(0+""));
            fragments.add(mSupportFragmentManager.findFragmentByTag(1+""));
            fragments.add(mSupportFragmentManager.findFragmentByTag(2+""));

            //恢复fragment页面
            restoreFragment();
        }else{
            //正常启动时调用
            fragments.add(new NewsFragment());
            fragments.add(new WechatFragment());
            fragments.add(new VideoFragment());
            showFragment();
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_page) {

        } else if (id == R.id.nav_favorite) {
            Intent intent = new Intent(this,FavoritActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_setting) {
            Util.switchAppTheme(this);
            reload();
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public class Presenter{
        public void onClick(View view){
            int id = view.getId();
            switch (id){
                case R.id.main_mainpage:
                    currentIndex = 0;
                    break;
                case R.id.main_wechat:
                    currentIndex = 1;

                    break;
                case R.id.main_video:

                    currentIndex = 2;

                    break;
                default:
                    break;
            }
            showFragment();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //“内存重启”时保存当前的fragment名字
        outState.putInt(CURRENT_FRAGMENT,currentIndex);
        super.onSaveInstanceState(outState);
        outState.putInt("theme",theme);
    }

    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private void showFragment(){

        FragmentTransaction transaction = mSupportFragmentManager.beginTransaction();

        //如果之前没有添加过
        if(!fragments.get(currentIndex).isAdded()){
            transaction
                    .hide(currentFragment)
                    .add(R.id.main_framelayout,fragments.get(currentIndex),""+currentIndex);  //第三个参数为添加当前的fragment时绑定一个tag

        }else{
            transaction
                    .hide(currentFragment)
                    .show(fragments.get(currentIndex));
        }

        currentFragment = fragments.get(currentIndex);

        transaction.commit();

    }

    /**
     * 恢复fragment
     */
    private void restoreFragment(){


        FragmentTransaction mBeginTreansaction = mSupportFragmentManager.beginTransaction();

        for (int i = 0; i < fragments.size(); i++) {

            if(i == currentIndex){
                mBeginTreansaction.show(fragments.get(i));
            }else{
                mBeginTreansaction.hide(fragments.get(i));
            }

        }

        mBeginTreansaction.commit();

        //把当前显示的fragment记录下来
        currentFragment = fragments.get(currentIndex);

    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);//不设置进入退出动画
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);
    }

}
