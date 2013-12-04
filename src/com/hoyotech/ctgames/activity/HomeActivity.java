package com.hoyotech.ctgames.activity;

import android.content.Intent;
import android.widget.TextView;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.hoyotech.ctgames.R;

public class HomeActivity extends FragmentActivity implements OnClickListener {

    // 定义Fragment页面
    private AppFragment fragmentApp;
    private VideoFragment fragmentVideo;
    private AwardFragment fragmentAward;
    private ZoneFragment fragmentZone;

    // 定义布局对象
    private FrameLayout appFg, videoFg, awardFg, zoneFg;

    // 定义图片组件对象
    private ImageView appIv, videoIv, awardIv, zoneIv;

    // 定义文字组件对象
    private TextView appTv, videoTv, awardTv, zoneTv;

    // 定义布局组件和图片组件数组，便于管理
    private ImageView[] imageViews;
    private FrameLayout[] frameLayouts;
    private TextView[] textViews;

    // 定义按钮图片组件
    private ImageView bigWheelIv;

    // 定义PopupWindow
    private PopupWindow popWindow;

    // Actionbar标题
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 显示标题栏
        title = (TextView) findViewById(R.id.action_bar_title);
        title.setText(R.string.home);

        initView();
        initData();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        // 实例化布局对象
        appFg = (FrameLayout) findViewById(R.id.button_app);
        videoFg = (FrameLayout) findViewById(R.id.button_video);
        awardFg = (FrameLayout) findViewById(R.id.button_award);
        zoneFg = (FrameLayout) findViewById(R.id.button_zone);

        // 实例化图片组件对象
        appIv = (ImageView) findViewById(R.id.image_app);
        videoIv = (ImageView) findViewById(R.id.image_video);
        awardIv = (ImageView) findViewById(R.id.image_award);
        zoneIv = (ImageView) findViewById(R.id.image_zone);

        // 实例化文字组件对象
        appTv = (TextView) findViewById(R.id.text_app);
        videoTv = (TextView) findViewById(R.id.text_video);
        awardTv = (TextView) findViewById(R.id.text_award);
        zoneTv = (TextView) findViewById(R.id.text_zone);

        // 实例化按钮图片组件
        bigWheelIv = (ImageView) findViewById(R.id.button_big_wheel);

        // 放入数组，便于管理
        frameLayouts = new FrameLayout[] {appFg, videoFg, awardFg, zoneFg};
        imageViews = new ImageView[] {appIv, videoIv, awardIv, zoneIv};
        textViews = new TextView[] {appTv, videoTv, awardTv, zoneTv};
    }


    /**
     * 初始化数据
     */
    private void initData() {
        // 给布局对象设置监听
        appFg.setOnClickListener(this);
        videoFg.setOnClickListener(this);
        awardFg.setOnClickListener(this);
        zoneFg.setOnClickListener(this);

        // 给按钮图片设置监听
        bigWheelIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            // 点击动态按钮
            case R.id.button_app:
                clickAppButton();
                break;
            // 点击与我相关按钮
            case R.id.button_video:
                clickVideoButton();
                break;
            // 点击我的空间按钮
            case R.id.button_award:
                clickAwardButton();
                break;
            // 点击更多按钮
            case R.id.button_zone:
                clickZoneButton();
                break;
            // 点击中间按钮
            case R.id.button_big_wheel:
                clickBigWheelButton();
                break;
            // 点击actionbar home按钮
            case R.id.action_bar_button_home:
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
            // 点击actionbar 任务管理按钮
            case R.id.action_bar_button_task:
                intent = new Intent(this, TaskHomeActivity.class);
                startActivity(intent);
                break;
        }
    }


    /**
     * 点击了“动态”按钮
     */
    private void clickAppButton() {
        // 实例化Fragment页面
        fragmentApp = new AppFragment();
        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // 替换当前的页面
        fragmentTransaction.replace(R.id.frame_content, fragmentApp);
        // 事务管理提交
        fragmentTransaction.commit();

        setItemSelected(0);

        // 标题栏标题更改为玩应用
        title.setText(R.string.play_app);
        // 显示首页和管理button
        ImageView btHome = (ImageView) findViewById(R.id.action_bar_button_home);
        ImageView btDownload = (ImageView) findViewById(R.id.action_bar_button_task);
        btHome.setVisibility(View.VISIBLE);
        btDownload.setVisibility(View.VISIBLE);
    }

    /**
     * 点击了“与我相关”按钮
     */
    private void clickVideoButton() {
        // 实例化Fragment页面
        fragmentVideo = new VideoFragment();
        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // 替换当前的页面
        fragmentTransaction.replace(R.id.frame_content, fragmentVideo);
        // 事务管理提交
        fragmentTransaction.commit();

        setItemSelected(1);

    }

    /**
     * 点击了“我的空间”按钮
     */
    private void clickAwardButton() {
        // 实例化Fragment页面
        fragmentAward = new AwardFragment();
        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // 替换当前的页面
        fragmentTransaction.replace(R.id.frame_content, fragmentAward);
        // 事务管理提交
        fragmentTransaction.commit();

        setItemSelected(2);

    }

    /**
     * 点击了“更多”按钮
     */
    private void clickZoneButton() {
        // 实例化Fragment页面
        fragmentZone = new ZoneFragment();
        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // 替换当前的页面
        fragmentTransaction.replace(R.id.frame_content, fragmentZone);
        // 事务管理提交
        fragmentTransaction.commit();

        setItemSelected(3);

    }

    /**
     * 设置第几个按钮是被选中的
     *
     * @param positionSelected 被选中的序号，-1表示全部不选中
     */
    private void setItemSelected(int positionSelected) {
        for (int i = 0; i < imageViews.length; i++) {
            frameLayouts[i].setSelected(false);
            imageViews[i].setSelected(false);
            textViews[i].setSelected(false);
            if (positionSelected == i) {
                frameLayouts[i].setSelected(true);
                imageViews[i].setSelected(true);
                textViews[i].setSelected(true);
            }

        }

        bigWheelIv.setSelected(false);
    }

    /**
     * 点击了中间按钮
     */
    private void clickBigWheelButton() {
        bigWheelIv.setSelected(true);

        Intent intent = new Intent();
        intent.setClass(HomeActivity.this, BigWheelAwardActivity.class);
        startActivity(intent);

    }

    /**
     * 改变显示的按钮图片为正常状态
     */
    private void changeButtonImage() {
        bigWheelIv.setSelected(false);
    }

}