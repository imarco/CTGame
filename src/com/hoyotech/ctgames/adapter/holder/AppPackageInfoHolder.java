package com.hoyotech.ctgames.adapter.holder;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-6
 * Time: 下午5:00
 * To change this template use File | Settings | File Templates.
 */
public class AppPackageInfoHolder {

    public ImageView image;              //应用图标
    public TextView tvPackageName;         // 应用名
    public TextView tvPackageSize;         // 应用安装包大小
    public Button btnOpen;               // 应用的状态（安装或者打开）
    public TextView tvPrizeCount;        // 应用的抽奖次数
    public TextView tvLuckyBeanCount;    // 幸运豆个数

    //应用的简介部分
    public TextView tvSummary; // 应用的简介

}
