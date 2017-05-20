package com.example.administrator.pengbonews.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.administrator.pengbonews.R;
import com.example.administrator.pengbonews.databinding.ActivitySplash1Binding;


/**
 * 此界面为导航界面
 * 欢迎界面，如果是第一次显示引导viewpager
 * 否则显示图片，展示动画后进入主界面
 */
public class SplashActivity extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener {


    ActivitySplash1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash1);
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        Log.e("ddd", "onCreate: "+"splas");
        ImageView splashImg = binding.splashImg;
        binding.splashJump.setOnClickListener(this);
        Animation animation = AnimationUtils.loadAnimation(this, R.animator.anim_splash);
        animation.setAnimationListener(this);
        splashImg.setAnimation(animation);
    }

    private void jump() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onClick(View view) {
        jump();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        jump();

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
