package com.hoyotech.ctgames.adapter.bean;

import android.graphics.drawable.Drawable;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-6
 * Time: 下午4:59
 * To change this template use File | Settings | File Templates.
 */
public class AppPackageInfo {

    private Drawable img;        // 图标
    private String name;      // 名称
    private float size;       // 大小
    private int state;           //目前的状态，下载还是安装 数字在AppTaskManagerState中定义
    private String summary = "";  //应用的简介
    private int prizeCount;       // 抽奖次数
    private int luckybeanCount;   // 幸运豆个数

    public AppPackageInfo(Drawable img, String name, float size, int state, String summary, int prizeCount, int luckybeanCount) {
        this.img = img;
        this.name = name;
        this.size = size;
        this.state = state;
        this.summary = summary;
        this.prizeCount = prizeCount;
        this.luckybeanCount = luckybeanCount;
    }

    public AppPackageInfo() {

    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
