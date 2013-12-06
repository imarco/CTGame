package com.hoyotech.ctgames.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hoyotech.ctgames.activity.AppCategoriesFragment;
import com.hoyotech.ctgames.activity.AppCollectionFragment;
import com.hoyotech.ctgames.activity.AppRecommendFragment;
import com.hoyotech.ctgames.fragment.TaskActiviteFragment;
import com.hoyotech.ctgames.fragment.TaskDownloadFragment;
import com.hoyotech.ctgames.fragment.TaskInstallFragment;
import com.hoyotech.ctgames.fragment.TaskRegisterFragment;

/**
 * Created by GGCoke on 13-12-4.
 */
public class TaskAdapter extends FragmentPagerAdapter {
    private static String[] TASK_FRAGMENTS = new String[] {};
    private int mCount = 0;

    public TaskAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.TASK_FRAGMENTS = titles;
        this.mCount = this.TASK_FRAGMENTS.length;
    }

    @Override
    public Fragment getItem(int position) {
        if (0 == position) {
            return new TaskDownloadFragment();
        } else if (1 == position) {
            return new TaskInstallFragment();
        } else if (2 == position) {
            return new TaskActiviteFragment();
        } else if (3 == position){
            return new TaskRegisterFragment();
        } else {
            System.out.println("创建子TaskFragment_" + position + "失败");
            return null;
        }
    }

    @Override
    public int getCount() {
        return this.mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TASK_FRAGMENTS[position % mCount];
    }
}
