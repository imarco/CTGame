package com.hoyotech.ctgames.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.TaskAdapter;
import com.hoyotech.ctgames.util.CTGameConstans;
import com.hoyotech.ctgames.viewdef.CTGameViewPager;
import com.viewpagerindicator.TabPageIndicator;

/**
 * Created by GGCoke on 13-12-4.
 */
public class TaskHomeActivity extends FragmentActivity implements View.OnClickListener {
    private TaskAdapter mAdapter;
    private CTGameViewPager mPager;
    private TabPageIndicator mIndicator;
    private static int mCurrentSubFragmentSeq = 0;
    private TaskHomeFragment taskHomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_home);

        // 显示标题
        TextView title = (TextView) findViewById(R.id.action_bar_title);
        title.setText(R.string.task_title);

        // 显示home按钮和我的地盘按钮
        ImageView btHome = (ImageView) findViewById(R.id.action_bar_button_home);
        ImageView btZone = (ImageView) findViewById(R.id.action_bar_button_zone);

        btHome.setVisibility(View.VISIBLE);
        btZone.setVisibility(View.VISIBLE);

        taskHomeFragment = new TaskHomeFragment();
        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        // 替换当前的页面
        fragmentTransaction.replace(R.id.task_frame_content, taskHomeFragment);
        // 事务管理提交
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.action_bar_button_home:
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.action_bar_button_zone:
                break;
            default:
                break;
        }
    }

    private void initTaskTabs() {
        String[] titles = new String[] {
                getString(R.string.task_download),
                getString(R.string.task_install),
                getString(R.string.task_activate),
                getString(R.string.task_registe)
        };

        mAdapter = new TaskAdapter(getSupportFragmentManager(), titles);
//        mPager = (CTGameViewPager) findViewById(R.id.task_pager);
        mPager.setScrollable(CTGameConstans.CTGAME_VIEWPAGER_SCROLL_NO);
        mPager.setAdapter(mAdapter);

//        mIndicator = (TabPageIndicator) findViewById(R.id.task_indicator);
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
    }
}
