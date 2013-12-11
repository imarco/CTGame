package com.hoyotech.ctgames.service;

import com.hoyotech.ctgames.adapter.bean.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 接口响应类
 * Created by GGCoke on 13-12-11.
 */
public class CTGameResponse {

    /**
     * 获取热门推荐应用列表
     * @param response
     * @return
     */
    public static List<AppInfo> getApps(String response) {
        List<AppInfo> apps = new ArrayList<AppInfo>();
        try {
            JSONObject result = new JSONObject(response);
            JSONArray appInfos = result.optJSONArray("appList");
            if (null != appInfos) {
                for (int i = 0; i < appInfos.length(); i++) {
                    JSONObject obj = appInfos.getJSONObject(i);
                    AppInfo info = getAppFromJSON(obj);
                    if (null != info)
                        apps.add(info);
                }
            }
        } catch (JSONException e) {
            return null;
        }

        return apps;
    }

    /**
     * 由json数据生成appinfo信息
     * @param obj
     * @return
     */
    private static AppInfo getAppFromJSON(JSONObject obj) {
        // TODO 修改字段
        AppInfo info = new AppInfo();
        try {
            info.setImg(obj.getString("image_path"));
            info.setUrl(obj.getString("url"));
            info.setAppName(obj.getString("name"));
            info.setAppSize(obj.getLong("size"));
            info.setState(obj.getInt("state"));
            info.setSummary(obj.getString("summary"));
            info.setPrizeCount(obj.getInt("prize_count"));
        } catch (JSONException e) {
            return null;
        }
        return info;
    }
}
