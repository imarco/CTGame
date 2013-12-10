package com.hoyotech.ctgames.adapter.bean;

import android.graphics.drawable.Drawable;

/**
 * 应用信息
 * Created by GGCoke on 13-12-6.
 */
public class AppInfo {
    private Drawable img;
    private String imgUrl;        // 图标
    private String appName;      // 名称
    private long appSize;       // 大小
    private int state;           //目前的状态，下载还是安装 数字在TaskState中定义
    private String summary = ""; //应用的简介
    private int prizeCount;
    private int luckybeanCount;

    // 便于在礼包中的应用进行操作，为应用设置三种模式，供选择是否下载的模式，下载中的模式，安装的模式
    // 数字在TaskState中定义
    private int mode = 0;

    private String url; // 下载的地址
    private int progress;        // 下载进度 0-100%
    private int rate;            // 下载速度 bps


    public AppInfo(String imgUrl, String appName, long appSize, int state, String summary, int prizeCount, int luckybeanCount) {
        this.imgUrl = imgUrl;
        this.appName = appName;
        this.appSize = appSize;
        this.state = state;
        this.summary = summary;
        this.prizeCount = prizeCount;
        this.luckybeanCount = luckybeanCount;
    }

    public AppInfo(Drawable img, String appName, long appSize, int state, String summary, int prizeCount, int luckybeanCount) {
        this.img = img;
        this.appName = appName;
        this.appSize = appSize;
        this.state = state;
        this.summary = summary;
        this.prizeCount = prizeCount;
        this.luckybeanCount = luckybeanCount;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public Drawable getImg() {
        return this.img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public AppInfo() {
    }

    public String getSummary() {
        return summary;

    }

    public void setSummary(String summary) {

        this.summary = summary;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImg(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String app_name) {
        this.appName = app_name;
    }

    public long getAppSize() {
        return appSize;
    }

    public void setAppSize(long appSize) {
        this.appSize = appSize;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPrizeCount() {
        return prizeCount;
    }

    public void setPrizeCount(int prizeCount) {
        this.prizeCount = prizeCount;
    }

    public int getLuckybeanCount() {
        return luckybeanCount;
    }

    public void setLuckybeanCount(int luckybeanCount) {
        this.luckybeanCount = luckybeanCount;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

}
