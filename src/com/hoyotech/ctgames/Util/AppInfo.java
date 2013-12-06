package com.hoyotech.ctgames.util;

import android.graphics.drawable.Drawable;

/**
 * 应用信息
 * Created by GGCoke on 13-12-6.
 */
public class AppInfo {
    private Drawable img;        // 图标
    private String app_name;     // 名称
    private float app_size;      // 大小
    private int state;           //目前的状态，下载还是安装
    private String summary = "";  //应用的简介
    private int downlowd_count;
    private int bonus_count;

    public String getSummary() {
        return summary;
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
    public String getApp_name() {
        return app_name;
    }
    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }
    public float getApp_size() {
        return app_size;
    }
    public void setApp_size(float app_size) {
        this.app_size = app_size;
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public int getDownlowd_count() {
        return downlowd_count;
    }
    public void setDownlowd_count(int downlowd_count) {
        this.downlowd_count = downlowd_count;
    }
    public int getBonus_count() {
        return bonus_count;
    }
    public void setBonus_count(int bonus_count) {
        this.bonus_count = bonus_count;
    }

    //构造函数1，适合于应用管理页面，非应用安装，因为应用安装需要简介部分
    public AppInfo(Drawable img, String app_name, float app_size, int state,
                   int downlowd_count, int bonus_count) {
        super();
        this.img = img;
        this.app_name = app_name;
        this.app_size = app_size;
        this.state = state;
        this.downlowd_count = downlowd_count;
        this.bonus_count = bonus_count;
    }

    //构造函数1，适合于应用管理页面，非应用安装，因为应用安装需要简介部分
    public AppInfo(Drawable img, String app_name, float app_size, int state,
                   int downlowd_count, int bonus_count, String summary) {
        super();
        this.img = img;
        this.app_name = app_name;
        this.app_size = app_size;
        this.state = state;
        this.downlowd_count = downlowd_count;
        this.bonus_count = bonus_count;
        this.summary = summary;
    }
}
