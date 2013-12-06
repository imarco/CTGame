package com.hoyotech.ctgames.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.TaskAdapter;
import com.hoyotech.ctgames.util.CTGameConstans;
import com.hoyotech.ctgames.viewdef.CTGameViewPager;
import com.viewpagerindicator.TabPageIndicator;

/**
 * Created by GGCoke on 13-12-6.
 */
public class TaskHomeFragment extends Fragment {
    private TaskAdapter mAdapter;
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
        // 任务管理标签标题
        String[] titles = new String[] {
                getString(R.string.task_download),
                getString(R.string.task_install),
                getString(R.string.task_activate),
                getString(R.string.task_registe)
        };

        View v = inflater.inflate(R.layout.fragment_task, container, false);
        mAdapter = new TaskAdapter(getFragmentManager(), titles);
        mPager = (CTGameViewPager) v.findViewById(R.id.task_pager);
        mPager.setScrollable(CTGameConstans.CTGAME_VIEWPAGER_SCROLL_NO);
        mPager.setAdapter(mAdapter);

        mIndicator = (TabPageIndicator) v.findViewById(R.id.task_indicator);
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
