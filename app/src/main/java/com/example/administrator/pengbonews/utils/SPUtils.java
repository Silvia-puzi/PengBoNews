package com.example.administrator.pengbonews.utils;

import android.content.Context;
import android.content.SharedPreferences;

/*
 *  @项目名：  PBNews 
 *  @包名：    com.example.administrator.pbnews.utils
 *  @文件名:   SPUtils
 *  @创建者:   Administrator
 *  @创建时间:  2017/4/14 0:31
 *  @描述：    TODO
 */
public class SPUtils {
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(Context context, String key,
                                     boolean value) {
        SharedPreferences sp = context.getSharedPreferences(
                Constants.SP_SPLASH, Context.MODE_PRIVATE);
        return sp.getBoolean(key, value);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(
                Constants.SP_SPLASH, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    public static String getString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(
                Constants.SP_SPLASH, Context.MODE_PRIVATE);
        return sp.getString(key, value);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(
                Constants.SP_SPLASH, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    public static int getInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(
                Constants.SP_SPLASH, Context.MODE_PRIVATE);
        return sp.getInt(key, value);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(
                Constants.SP_SPLASH, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    private static final String THEME = "theme";
    public static void settheme(Context context, String theme){
        SharedPreferences sp = context.getSharedPreferences("demo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(THEME,theme);
        editor.apply();
    }
    public static String gettheme(Context context){
        SharedPreferences sp = context.getSharedPreferences("demo",Context.MODE_PRIVATE);
        return sp.getString(THEME,"1");
    }
}
