package com.hoyotech.ctgames.adapter.holder;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-6
 * Time: 下午8:01
 * To change this template use File | Settings | File Templates.
 */
public class TaskDownloadHolder {

    public ImageView appImageHeader; //应用图标
    public TextView appName;         // 应用名
    public TextView appPackageSize;  // 应用安装包大小
    public Button btnOptions;        // 应用的状态（安装或者打开获取奖励等）
    public Button btnAppBonus;       // 应用的所有奖励按钮
    public TextView tvPrizeCount;    // 应用的抽奖次数
    public TextView tvLuckyBeanCount; // 幸运豆个数
    public TextView tvSummary; // 应用的简介
    public ProgressBar progressBar; // 下载进度条
    public TextView tvDownloadRate; // 下载速度
    public TextView tvDownloadPercent; // 下载百分比

}
