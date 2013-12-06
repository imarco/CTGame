package com.hoyotech.ctgames.adapter.bean;

import android.graphics.drawable.Drawable;

/**
 * 应用信息
 * Created by GGCoke on 13-12-6.
 */
public class AppInfo {
    private Drawable img;        // 图标
    private String appName;      // 名称
    private float appSize;       // 大小
    private int state;           //目前的状态，下载还是安装 数字在AppTaskManagerState中定义
    private String summary = ""; //应用的简介
    private int prizeCount;
    private int luckybeanCount;
    private int progress;        // 下载进度
    private int rate;            // 下载速度

    public AppInfo() {
    }

    public String getSummary() {
        return summary;

    }

    public AppInfo(Drawable img, String appName, float appSize, int state, String summary, int prizeCount, int luckybeanCount) {
        this.img = img;
        this.appName = appName;
        this.appSize = appSize;
        this.state = state;
        this.summary = summary;
        this.prizeCount = prizeCount;
        this.luckybeanCount = luckybeanCount;
    }

    public void setSummary(String summary) {

        this.summary = summary;
    }
    public Drawable getImg() {
        return img;
    }
    public void setImg(Drawable img) {
        this.img = img;
    }
    public String getAppName() {
        return appName;
    }
    public void setAppName(String app_name) {
        this.appName = app_name;
    }
    public float getAppSize() {
        return appSize;
    }
    public void setAppSize(float appSize) {
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
}
