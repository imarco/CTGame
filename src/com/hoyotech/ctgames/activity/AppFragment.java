package com.hoyotech.ctgames.activity;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.AppStoreAdapter;
import com.viewpagerindicator.TitlePageIndicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AppFragment extends Fragment {
    private AppStoreAdapter mAdapter;
    private ViewPager mPager;
    private TitlePageIndicator mIndicator;
    private static int mCurrentSubFragmentSeq = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_app, container, false);
        mAdapter = new AppStoreAdapter(getFragmentManager());
        mPager = (ViewPager) v.findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mIndicator = (TitlePageIndicator) v.findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager, mCurrentSubFragmentSeq);
        mIndicator.setFooterIndicatorStyle(TitlePageIndicator.IndicatorStyle.Triangle);

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