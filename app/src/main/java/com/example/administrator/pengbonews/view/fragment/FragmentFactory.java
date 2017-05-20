package com.example.administrator.pengbonews.view.fragment;

import android.support.v4.app.Fragment;

import com.example.administrator.pengbonews.view.fragment.video.AdorableFragment;
import com.example.administrator.pengbonews.view.fragment.video.BeautyFragment;
import com.example.administrator.pengbonews.view.fragment.video.ComicFragment;
import com.example.administrator.pengbonews.view.fragment.video.CuriousFragment;
import com.example.administrator.pengbonews.view.fragment.video.FunnyFragment;
import com.example.administrator.pengbonews.view.fragment.video.GossipFragment;
import com.example.administrator.pengbonews.view.fragment.video.KnowledgeFragment;
import com.example.administrator.pengbonews.view.fragment.video.MilitaryFragment;
import com.example.administrator.pengbonews.view.fragment.video.MoviesFragment;
import com.example.administrator.pengbonews.view.fragment.video.MusicFragment;
import com.example.administrator.pengbonews.view.fragment.video.OpusculumFragment;
import com.example.administrator.pengbonews.view.fragment.video.RecomFragment;
import com.example.administrator.pengbonews.view.fragment.video.SceneFragment;
import com.example.administrator.pengbonews.view.fragment.video.TechnologyFragment;

import java.util.HashMap;
import java.util.Map;

/*
 *  @项目名：  PengBoNews 
 *  @包名：    com.example.administrator.pengbonews.view.fragment
 *  @文件名:   FragmentFactory
 *  @创建者:   Administrator
 *  @创建时间:  2017/4/19 22:42
 *  @描述：    TODO
 */
public class FragmentFactory {
    private static final String TAG = "FragmentFactory";
    public static boolean hasChild = false;
    private static Map<Integer, Fragment> fragmentMap = new HashMap<>();//newsfragment
    private static Map<Integer, Fragment> videoMap = new HashMap<>();//videofragment


    public static Fragment createFragment(int position) {
        Fragment fragment = null;
        //遍历集合看集合中有没有对应的fragment，有就不new用现成的
        if (fragmentMap.containsKey(position)) {
            fragment = fragmentMap.get(position);
            hasChild = true;
            return fragment;
        } else {
            switch (position) {
                case 0:
                    fragment = new WarFragment();
                    break;
                case 1:
                    fragment = new SportFragment();
                    break;
                case 2:
                    fragment = new ScienceFragment();
                    break;
                case 3:
                    fragment = new EducationFragment();
                    break;
                case 4:
                    fragment = new EntertainmentFragment();
                    break;
                case 5:
                    fragment = new EconomyFragment();
                    break;
                case 6:
                    fragment = new GupiaoFragment();
                    break;
                case 7:
                    fragment = new TripFragment();
                    break;
                case 8:
                    fragment = new WomenFragment();
                    break;
                default:
                    break;
            }
            fragmentMap.put(position, fragment);
            return fragment;
        }
    }

    public Fragment getFragment(int position){
        return fragmentMap.get(position);
    }

    public static Fragment createVideoFragment(int position) {
        Fragment fragment = null;
        //遍历集合看集合中有没有对应的fragment，有就不new用现成的
        if (videoMap.containsKey(position)) {
            fragment = videoMap.get(position);
            hasChild = true;
            return fragment;
        } else {
            switch (position) {
                case 0:
                    fragment = new RecomFragment();
                    break;
                case 1:
                    fragment = new FunnyFragment();
                    break;
                case 2:
                    fragment = new SceneFragment();
                    break;
                case 3:
                    fragment = new GossipFragment();
                    break;
                case 4:
                    fragment = new AdorableFragment();
                    break;
                case 5:
                    fragment = new MoviesFragment();
                    break;
                case 6:
                    fragment = new KnowledgeFragment();
                    break;
                case 7:
                    fragment = new BeautyFragment();
                    break;
                case 8:
                    fragment = new OpusculumFragment();
                    break;
                case 9:
                    fragment = new CuriousFragment();
                    break;
                case 10:
                    fragment = new MusicFragment();
                    break;
                case 11:
                    fragment = new MilitaryFragment();
                    break;
                case 12:
                    fragment = new TechnologyFragment();
                    break;
                case 13:
                    fragment = new ComicFragment();
                    break;
                default:
                    break;
            }
            videoMap.put(position, fragment);
            return fragment;
        }
    }

    public Fragment getVideoFragment(int position){
        return videoMap.get(position);
    }


}
