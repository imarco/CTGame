package com.hoyotech.ctgames.adapter.holder;

import android.widget.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-6
 * Time: 下午9:09
 * To change this template use File | Settings | File Templates.
 */
public class PackageDownloadHolder {

    public ImageView appImageHeader; //应用图标
    public TextView appName;         // 应用名
    public TextView appPackageSize;  // 应用安装包大小
    public Button btnOptions;        // 应用的状态（安装或者打开获取奖励等）
    public TextView tvSummary; // 应用的简介
    public ProgressBar progressBar; // 下载进度条
    public CheckBox checkBoxToDownload; // 应用是否已经安装
    public TextView tvDownloadRate; // 下载速度
    public TextView tvDownloadPercent; // 下载百分比

}
