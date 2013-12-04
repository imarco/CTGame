package com.hoyotech.ctgames.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hoyotech.ctgames.activity.AppCategoriesFragment;
import com.hoyotech.ctgames.activity.AppCollectionFragment;
import com.hoyotech.ctgames.activity.AppRecommendFragment;

/**
 * Created by GGCoke on 13-12-3.
 */
public class AppStoreAdapter extends FragmentStatePagerAdapter {
    private static final String[] APP_FRAGMENTS = new String[] {"recommend", "collection", "categories"};
    private int mCount = APP_FRAGMENTS.length;

    public AppStoreAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (0 == position) {
            return new AppRecommendFragment();
        } else if (1 == position) {
            return new AppCollectionFragment();
        } else if (2 == position) {
            return new AppCategoriesFragment();
        } else {
            System.out.println("创建子AppFragment_" + position + "失败");
            return null;
        }
    }

    @Override
    public int getCount() {
        return this.mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return APP_FRAGMENTS[position % mCount];
    }
}
