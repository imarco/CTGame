package com.hoyotech.ctgames.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.*;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.util.CTGameImageLoader;
import com.hoyotech.ctgames.util.TaskState;

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
    private Mode mode = Mode.none;

    public AppInfo info;

    // 此控件有三种模式，选择，下载和安装，三种都不是就是none
    private enum Mode {
        none, selection, download, install
    }

    /**
     * 从界面中获取view构件holder
     * @param convertView parentView
     */
    public PackageDownloadHolder(View convertView) {
        this.appImageHeader = (ImageView) convertView.findViewById(R.id.image_app);
        this.appName = (TextView) convertView.findViewById(R.id.tv_app_name);
        this.appPackageSize = (TextView) convertView.findViewById(R.id.tv_app_size);
        this.btnOptions = (Button) convertView.findViewById(R.id.btn_options);
        this.checkBoxToDownload = (CheckBox) convertView.findViewById(R.id.cb_download);
        this.tvSummary = (TextView) convertView.findViewById(R.id.tv_summary);
        this.progressBar = (ProgressBar) convertView.findViewById(R.id.progress_bar);
        this.tvDownloadRate = (TextView) convertView.findViewById(R.id.tv_download_rate);
        this.tvDownloadPercent = (TextView) convertView.findViewById(R.id.tv_download_percent);
    }

    /**
     * 根据appinfo的信息设置viewholder中view的状态
     * @param appInfo app的信息
     */
    public void setData(Context context, AppInfo appInfo) {

        CTGameImageLoader.loadImage(context, appInfo.getAppLogoUrl(), this.appImageHeader);// app logo
        this.appName.setText(appInfo.getAppName());         // app name
        this.appPackageSize.setText(String.valueOf(appInfo.getAppSize())+"M");    // app size

        //三种状态，选择状态，下载状态，安装状态
        if(appInfo.getMode() == TaskState.MODE_SELECTION) {
            setToSelectionMode(context, appInfo);
        } else if(appInfo.getMode() == TaskState.MODE_DOWNLOADING){
            setToDownloadingMode(context, appInfo);
        } else if(appInfo.getMode() == TaskState.MODE_INSTALL) {
            setToInstallMode(context, appInfo);
        } else {
            return;
        }

        TaskState.setButtonView(appInfo.getState(), context, btnOptions);

    }

    /**
     * 应用并未下载或安装，处于供用户选择是否下载的状态，有复选框
     * 此时试图在选择模式
     */
    private void setToSelectionMode(Context context, AppInfo appInfo) {
        this.btnOptions.setVisibility(View.GONE);
        this.checkBoxToDownload.setVisibility(View.VISIBLE);

        this.tvSummary.setVisibility(View.VISIBLE);
        this.progressBar.setVisibility(View.GONE);
        this.tvDownloadRate.setVisibility(View.GONE);
        this.tvDownloadPercent.setVisibility(View.GONE);

        mode = Mode.selection;
    }

    /**
     * 应用正在下载，其中包含了暂停，下载，继续等状态，此时有下载条无复选框
     * 此时的试图在下载模式
     */
    private void setToDownloadingMode(Context context, AppInfo appInfo) {
        this.btnOptions.setVisibility(View.VISIBLE);
        this.checkBoxToDownload.setVisibility(View.GONE);

        this.tvSummary.setVisibility(View.GONE);
        this.progressBar.setVisibility(View.VISIBLE);
        this.tvDownloadRate.setVisibility(View.VISIBLE);
        this.tvDownloadPercent.setVisibility(View.VISIBLE);
        mode = Mode.download;
    }

    /**
     * 应用正在安装，其中包含了安装，安装中，打开，领取奖励等状态，此时无下载条无复选框
     * 此时的试图在安装模式
     */
    private void setToInstallMode(Context context, AppInfo appInfo) {
        this.btnOptions.setVisibility(View.VISIBLE);
        this.checkBoxToDownload.setVisibility(View.GONE);

        this.tvSummary.setVisibility(View.VISIBLE);
        this.progressBar.setVisibility(View.GONE);
        this.tvDownloadRate.setVisibility(View.GONE);
        this.tvDownloadPercent.setVisibility(View.GONE);
        mode = Mode.install;
    }

    /**
     * 是否为选择模式
     * @return true or false
     */
    public boolean isSelectionMode() {
        return mode == Mode.selection;
    }

    /**
     * 是否为下载模式
     * @return true or false
     */
    public boolean isDownloadingMode() {
        return mode == Mode.download;
    }

    /**
     * 是否为安装模式
     * @return true or false
     */
    public boolean isInstallMode() {
        return mode == Mode.install;
    }

}
