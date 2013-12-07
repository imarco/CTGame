package com.hoyotech.ctgames.adapter.holder;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
}

