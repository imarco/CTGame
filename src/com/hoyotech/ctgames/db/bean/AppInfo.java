package com.hoyotech.ctgames.db.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoyotech.ctgames.util.TaskState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 应用信息
 * Created by GGCoke on 13-12-6.
 */
public class AppInfo implements Serializable {
    // 字段
    public static final transient String APPINFO_TABLE_NAME = "app";
    public static final transient String APPINFO_APPID = "appId";
    public static final transient String APPINFO_APPLOGOURL = "appLogoUrl";
    public static final transient String APPINFO_APPNAME = "appName";
    public static final transient String APPINFO_APPSIZE = "appSize";
    public static final transient String APPINFO_LUCKYBEANSNUM = "luckyBeansNum";
    public static final transient String APPINFO_LOTTERYNUM = "lotteryNum";
    public static final transient String APPINFO_APPDESC = "appDesc";
    public static final transient String APPINFO_VERSION = "version";
    public static final transient String APPINFO_APPURL = "appUrl";
    public static final transient String APPINFO_APPMD5 = "MD5";
    public static final transient String APPINFO_AD = "ad";
    public static final transient String APPINFO_APPPICURLS = "appPicUrls";
    public static final transient String APPINFO_MODE = "mode";
    public static final transient String APPINFO_PROGRESS = "progress";
    public static final transient String APPINFO_RATE = "rate";
    public static final transient String APPINFO_STATE = "state";
    public static final transient String APPINFO_HASDOWNLOADED = "hasDownloaded";

    // 应用基本信息
    private long appId;             //应用id
    private String appLogoUrl;       //应用LOGO地址
    private String appName;         //应用名称
    private long appSize;           //应用大小
    private int luckyBeansNum;      //下载可获得幸运豆
    private int lotteryNum;         //下载可获取抽奖次数
    private String appDesc;         //应用描述
    private String appUrl;          //应用下载地址
    private String version;         //版本信息
    private String MD5;             //MD5
    private String ad;              //最后一张截图的广告
    private String appPicUrls;      //截图地址，多个url之间用分号隔开


    // 便于在礼包中的应用进行操作，为应用设置三种模式，供选择是否下载的模式，下载中的模式，安装的模式
    // 数字在TaskState中定义
    private int mode = 0;
    private int progress;        // 下载进度 0-100%
    private int rate;            // 下载速度 bps
    private int state;           // 目前的状态，下载还是安装 数字在TaskState中定义
    private boolean hasDownloaded;   // 应用是否下载过

    public AppInfo() {}

    public AppInfo(long appId, String appLogoUrl, String appName, long appSize,
                   int luckyBeansNum, int lotteryNum, String appDesc, String appUrl,
                   String version, String MD5, String ad, String appPicUrls) {
        this.appId = appId;
        this.appLogoUrl = appLogoUrl;
        this.appName = appName;
        this.appSize = appSize;
        this.luckyBeansNum = luckyBeansNum;
        this.lotteryNum = lotteryNum;
        this.appDesc = appDesc;
        this.appUrl = appUrl;
        this.version = version;
        this.MD5 = MD5;
        this.ad = ad;
        this.appPicUrls = appPicUrls;
    }

    public static List<AppInfo> parseJson(JSONArray jsonArray) {
        List<AppInfo> appList = new ArrayList<AppInfo>();
        if (null != jsonArray || jsonArray.size() > 0) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                JSONArray imgs = obj.getJSONArray("appPicUrls");
                StringBuilder sb = new StringBuilder();
                if (null != imgs) {
                    for (int j = 0; j < imgs.size(); j++) {
                        sb.append(imgs.getString(j)).append(";");
                    }
                }
                AppInfo app = new AppInfo(
                        obj.getLong("appId"),
                        obj.getString("appLogoUrl"),
                        obj.getString("appName"),
                        obj.getLong("appSize"),
                        obj.getInteger("luckyBeansNum"),
                        obj.getInteger("lotteryNum"),
                        obj.getString("appDesc"),
                        obj.getString("appUrl"),
                        obj.getString("version"),
                        obj.getString("MD5"),
                        obj.getString("ad"),
                        sb.toString()
                );
                app.setState(TaskState.STATE_PREPARE);
                appList.add(app);
            }
        }

        return appList;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getAppLogoUrl() {
        return appLogoUrl;
    }

    public void setAppLogoUrl(String appLogoUrl) {
        this.appLogoUrl = appLogoUrl;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public long getAppSize() {
        return appSize;
    }

    public void setAppSize(long appSize) {
        this.appSize = appSize;
    }

    public int getLuckyBeansNum() {
        return luckyBeansNum;
    }

    public void setLuckyBeansNum(int luckyBeansNum) {
        this.luckyBeansNum = luckyBeansNum;
    }

    public int getLotteryNum() {
        return lotteryNum;
    }

    public void setLotteryNum(int lotteryNum) {
        this.lotteryNum = lotteryNum;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getAppPicUrls() {
        return appPicUrls;
    }

    public void setAppPicUrls(String appPicUrls) {
        this.appPicUrls = appPicUrls;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isHasDownloaded() {
        return hasDownloaded;
    }

    public void setHasDownloaded(boolean hasDownloaded) {
        this.hasDownloaded = hasDownloaded;
    }
}
