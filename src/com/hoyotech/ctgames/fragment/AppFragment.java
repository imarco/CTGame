package com.hoyotech.ctgames.fragment;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.AppStoreAdapter;
import com.hoyotech.ctgames.util.CTGameConstans;
import com.hoyotech.ctgames.viewdef.CTGameViewPager;
import com.viewpagerindicator.TabPageIndicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AppFragment extends Fragment {
    private static final String TAG = AppFragment.class.getSimpleName();
    private AppStoreAdapter mAdapter;
    private CTGameViewPager mPager;
    private TabPageIndicator mIndicator;
    private static int mCurrentSubFragmentSeq = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 获取玩应用首页标签标题
        String[] titles = new String[] {
                getString(R.string.app_recommend),
                getString(R.string.app_collection),
                getString(R.string.app_categories)
        };

        Log.e(TAG, "In AppFragment.onCreateView");

        View v = inflater.inflate(R.layout.fragment_app, container, false);
        mAdapter = new AppStoreAdapter(getChildFragmentManager(), titles);
        mPager = (CTGameViewPager) v.findViewById(R.id.pager);
        mPager.setScrollable(CTGameConstans.CTGAME_VIEWPAGER_SCROLL_NO);
        mPager.setAdapter(mAdapter);

        mIndicator = (TabPageIndicator) v.findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager, mCurrentSubFragmentSeq);

        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurrentSubFragmentSeq = position;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}