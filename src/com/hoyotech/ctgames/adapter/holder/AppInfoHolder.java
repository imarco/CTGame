package com.hoyotech.ctgames.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.db.dao.AppDao;
import com.hoyotech.ctgames.util.CTGameImageLoader;
import com.hoyotech.ctgames.util.TaskState;

/**
 * Created by GGCoke on 13-12-6.
 */
public class AppInfoHolder {
    public ImageView appImageHeader; //应用图标
    public TextView appName;         // 应用名
    public TextView appPackageSize;  // 应用安装包大小
    public Button btnOptions;        // 应用的状态（安装或者打开）
    public Button btnAppBonus;       // 应用包的奖励信息
    public TextView tvPrizeCount;    // 应用的抽奖次数
    public TextView tvLuckyBeanCount; // 幸运豆个数

    //应用的简介部分
    public TextView tvSummary; // 应用的简介

    public AppInfo info;

    /**
     * 从界面中获取view构件holder
     * @param convertView parentView
     */
    public AppInfoHolder(View convertView) {
        this.appImageHeader = (ImageView) convertView.findViewById(R.id.app_img_head);
        this.appName = (TextView) convertView.findViewById(R.id.app_name);
        this.appPackageSize = (TextView) convertView.findViewById(R.id.app_package_size);
        this.btnOptions = (Button) convertView.findViewById(R.id.btn_options);
        this.tvPrizeCount = (TextView) convertView.findViewById(R.id.tv_prize_count);
        this.tvLuckyBeanCount = (TextView) convertView.findViewById(R.id.tv_luckybean_count);
    }

    /**
     * 根据appinfo的信息设置viewholder中view的状态
     * @param appInfo app 的信息
     */
    public void setData(Context context, AppInfo appInfo) {
        CTGameImageLoader.loadImage(context, appInfo.getAppLogoUrl(), this.appImageHeader);
        this.appName.setText(appInfo.getAppName());
        this.appPackageSize.setText(String.valueOf(appInfo.getAppSize()));
        this.btnOptions.setText(TaskState.getTaskStateMap().get(appInfo.getState()));
        this.tvPrizeCount.setText(String.valueOf(appInfo.getLotteryNum()));
        this.tvLuckyBeanCount.setText(String.valueOf(appInfo.getLuckyBeansNum()));

        // 应用已经开始下载，显示当前状态
        AppDao appDao = new AppDao(context);
        AppInfo app = appDao.queryAppById(appInfo.getAppId());
        if (null == app) {
            TaskState.setButtonView(appInfo.getState(), context, btnOptions);
        } else {
            TaskState.setButtonView(app.getState(), context, btnOptions);
        }
    }

    public void setButtonState(Context context, AppInfo appInfo) {
        btnOptions.setText(TaskState.getTaskStateMap().get(appInfo.getState()));
        TaskState.setButtonView(appInfo.getState(), context, btnOptions);
    }
}

