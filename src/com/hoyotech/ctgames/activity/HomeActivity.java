package com.hoyotech.ctgames.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.*;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.db.DBOpenHelper;
import com.hoyotech.ctgames.fragment.*;
import com.hoyotech.ctgames.service.CTGameReceiver;
import com.hoyotech.ctgames.service.DownloadService;
import com.hoyotech.ctgames.util.TaskState;

import cn.sharesdk.framework.ShareSDK;

public class HomeActivity extends FragmentActivity implements OnClickListener {

    // 定义Fragment页面
    private AppFragment fragmentApp;
    private AwardFragment fragmentAward;
    private OrderProductFragment fragmentOrderProduct;
    private MoreFragment fragmentMore;
    private BigWheelFragment fragmentBigWheel;

    // 切换Fragment
    private Fragment from;
    private Fragment to;

    // 定义布局对象
    private RelativeLayout appFg, awardFg, orderProductFg, moreFg, wheelFg;

    // 定义图片组件对象
    private ImageView appIv, awardIv, orderPruductIv, moreIv, wheelIv;

    // 定义文字组件对象
    private TextView appTv, awardTv, orderProductTv, moreTv, wheelTv;

    // 定义布局组件和图片组件数组，便于管理
    private ImageView[] imageViews;
    private RelativeLayout[] relativeLayouts;
    private TextView[] textViews;

    // 定义按钮图片组件
    private ImageView bigWheelIv;

    // 定义PopupWindow
    private PopupWindow popWindow;

    // Actionbar标题
    private TextView title;

    private CTGameReceiver receiver = new CTGameReceiver();

    private static int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 加载数据库
        DBOpenHelper helper = new DBOpenHelper(HomeActivity.this);
        helper.getWritableDatabase();

        // 显示标题栏
        title = (TextView) findViewById(R.id.action_bar_title);
        title.setText(R.string.home);

        // 初始化分享组件
        ShareSDK.initSDK(this);

        initView();
        initData();
        startService();
        // 默认出来在大转盘
        switchToBigWheelFragment();

    }

    /**
     * 初始化组件
     */
    private void initView() {
        // 实例化布局对象
        appFg = (RelativeLayout) findViewById(R.id.button_app);
        awardFg = (RelativeLayout) findViewById(R.id.button_video);
        orderProductFg = (RelativeLayout) findViewById(R.id.button_award);
        moreFg = (RelativeLayout) findViewById(R.id.button_zone);
        wheelFg = (RelativeLayout) findViewById(R.id.button_big_wheel);

        // 实例化图片组件对象
        appIv = (ImageView) findViewById(R.id.image_app);
        awardIv = (ImageView) findViewById(R.id.image_video);
        orderPruductIv = (ImageView) findViewById(R.id.image_award);
        moreIv = (ImageView) findViewById(R.id.image_zone);

        // 实例化文字组件对象
        appTv = (TextView) findViewById(R.id.text_app);
        awardTv = (TextView) findViewById(R.id.text_video);
        orderProductTv = (TextView) findViewById(R.id.text_award);
        moreTv = (TextView) findViewById(R.id.text_zone);

        // 实例化按钮图片组件
        bigWheelIv = (ImageView) findViewById(R.id.image_big_wheel);

        // 放入数组，便于管理
        relativeLayouts = new RelativeLayout[] {appFg, awardFg, orderProductFg, moreFg, wheelFg};
        imageViews = new ImageView[] {appIv, awardIv, orderPruductIv, moreIv};
        textViews = new TextView[] {appTv, awardTv, orderProductTv, moreTv};
    }


    /**
     * 初始化数据
     */
    private void initData() {
        // 给布局对象设置监听
        appFg.setOnClickListener(this);
        awardFg.setOnClickListener(this);
        orderProductFg.setOnClickListener(this);
        moreFg.setOnClickListener(this);

        // 给按钮图片设置监听
        bigWheelIv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            // 点击动态按钮
            case R.id.button_app:
                clickAppButton(true);
                break;
            // 点击与我相关按钮
            case R.id.button_video:
                clickAwardButton(true);
                break;
            // 点击我的空间按钮
            case R.id.button_award:
                clickOrderProductButton(true);
                break;
            // 点击更多按钮
            case R.id.button_zone:
                clickZoneButton(true);
                break;
            // 点击中间按钮
            case R.id.image_big_wheel:
                clickBigWheelButton(true);
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
    public void clickAppButton(boolean click) {
        if (click && position == 0) return;
        position = 0;
        // 实例化Fragment页面
        fragmentApp = new AppFragment();
        from = to;
        to = fragmentApp;

        // 切换Fragm
        switchContent(from, to);
        setItemSelected(0);

        // 标题栏标题更改为玩应用
        title.setText(R.string.play_app);
        // 显示首页和管理button
        ImageView ivTaskManager = (ImageView) findViewById(R.id.action_bar_button_task);
        ivTaskManager.setVisibility(View.VISIBLE);
    }

    /**
     * 点击了“与我相关”按钮
     */
    public void clickAwardButton(boolean click) {
        if (click && position == 1) return;
        position = 1;
        // 实例化Fragment页面
        fragmentAward = new AwardFragment();
        from = to;
        to = fragmentAward;

        // 切换Fragm
        switchContent(from, to);
        setItemSelected(1);

        // 标题栏标题更改为玩应用
        title.setText(R.string.get_award);
        // 显示首页和管理button
        ImageView ivTaskManager = (ImageView) findViewById(R.id.action_bar_button_task);
        ivTaskManager.setVisibility(View.VISIBLE);
    }

    /**
     * 点击了“我的空间”按钮
     */
    public void clickOrderProductButton(boolean click) {
        if (click && position == 2) return;
        position = 2;
        // 实例化Fragment页面
        fragmentOrderProduct = new OrderProductFragment();
        from = to;
        to = fragmentOrderProduct;

        // 切换Fragm
        switchContent(from, to);

        setItemSelected(2);

        // 标题栏标题更改为玩应用
        title.setText(R.string.order_goods);
        // 显示首页和管理button
        ImageView ivTaskManager = (ImageView) findViewById(R.id.action_bar_button_task);
        ivTaskManager.setVisibility(View.VISIBLE);

    }

    /**
     * 点击了“更多”按钮
     */
    public void clickZoneButton(boolean click) {
        if (click && position == 3) return;
        position = 3;
        // 实例化Fragment页面
        fragmentMore = new MoreFragment();
        from = to;
        to = fragmentMore;

        // 切换Fragm
        switchContent(from, to);

        setItemSelected(3);
        // 标题栏标题更改为玩应用
        title.setText(R.string.more);
        // 显示首页和管理button
        ImageView ivTaskManager = (ImageView) findViewById(R.id.action_bar_button_task);
        ivTaskManager.setVisibility(View.VISIBLE);

    }

    /**
     * 设置第几个按钮是被选中的
     *
     * @param positionSelected 被选中的序号，-1表示全部不选中
     */
    private void setItemSelected(int positionSelected) {
        for (int i = 0; i < imageViews.length; i++) {
            relativeLayouts[i].setSelected(false);
            imageViews[i].setSelected(false);
            textViews[i].setSelected(false);
            if (positionSelected == i) {
                relativeLayouts[i].setSelected(true);
                imageViews[i].setSelected(true);
                textViews[i].setSelected(true);
            }

        }

        bigWheelIv.setSelected(false);
    }

    /**
     * 点击了中间按钮
     */
    public void clickBigWheelButton(boolean click) {
        if (click && position ==-1) return;
        position = -1;
        bigWheelIv.setSelected(true);

        // 实例化Fragment页面
        fragmentBigWheel = new BigWheelFragment();
        from = to;
        to = fragmentBigWheel;

        // 切换Fragm
        switchContent(from, to);

        setItemSelected(-1);

        // 标题栏标题更改为玩应用
        title.setText(R.string.big_wheel);
        // 显示首页和管理button
        ImageView ivTaskManager = (ImageView) findViewById(R.id.action_bar_button_task);
        ivTaskManager.setVisibility(View.VISIBLE);

    }

    /**
     * 切换到大转盘
     */
    private void switchToBigWheelFragment() {
        clickBigWheelButton(false);
    }

    /**
     * 改变显示的按钮图片为正常状态
     */
    private void changeButtonImage() {
        bigWheelIv.setSelected(false);
    }

    private void startService(){
        Log.e("TAG_HomeActivity", "============> HomeActivity.startService");
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(receiver, filter);

        // start download service
        Intent downloadIntent = new Intent(DownloadService.DOWNLOAD_SERVICE_NAME);
        downloadIntent.putExtra(TaskState.DOWNLOAD_STATE, TaskState.STATE_START);
        startService(downloadIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        // 结束分享组件的统计功能
        ShareSDK.stopSDK(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 切换Fragment
     * @param from
     * @param to
     */
    private void switchContent(Fragment from, Fragment to) {
        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();

        if (null != from) {
            fragmentTransaction.detach(from);
        }

        if (!to.isAdded()) {
            fragmentTransaction.add(R.id.frame_content, to).attach(to).commit();
        }
    }
}