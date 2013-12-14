package com.hoyotech.ctgames.db.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-6
 * Time: 下午4:59
 * To change this template use File | Settings | File Templates.
 */
public class PackageInfo {

    private int id;
    private String url;        // 图标
    private String name;      // 名称
    private long size;       // 大小
    private int prizeCount;       // 抽奖次数
    private int luckybeanCount;   // 幸运豆个数

    private String summary;

    public static List<PackageInfo> parseJson(JSONArray jsonArray) {
        List<PackageInfo> packageList = new ArrayList<PackageInfo>();
        if (null != jsonArray && jsonArray.size() > 0) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                PackageInfo packageInfo = new PackageInfo();
                packageInfo.setId(obj.getIntValue("id"));
                packageInfo.setName(obj.getString("name"));
                packageInfo.setUrl(obj.getString("picUrl"));
                packageInfo.setSummary(obj.getString("description"));
                packageInfo.setSize(obj.getLongValue("size"));
                packageInfo.setLuckybeanCount(obj.getIntValue("luckyBeansNum"));
                packageInfo.setPrizeCount(obj.getIntValue("lotteryNum"));

                packageList.add(packageInfo);
            }
        }

        return packageList;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public PackageInfo() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
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
