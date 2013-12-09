package com.hoyotech.ctgames.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.bean.AppInfo;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-6
 * Time: 下午9:50
 * To change this template use File | Settings | File Templates.
 */
public class DataUtils {

    /**
     * 测试方法，以后要删除
     * @param context
     * @return
     */
    public static ArrayList<AppInfo> getAppInfos(Context context) {
        ArrayList<AppInfo> infos = new ArrayList<AppInfo>();
        Drawable d = context.getResources().getDrawable(
                R.drawable.image_app_sample);
        for (int i = 0; i < 6; i++) {
            AppInfo appInfo = new AppInfo();
            appInfo.setImg(d);
            appInfo.setAppName("神庙逃亡" + i);
            appInfo.setAppSize(i * i);
            if (i % 2 == 0) {
                appInfo.setState(TaskState.STATE_DOWNLOAD);
            } else {
                appInfo.setState(TaskState.STATE_INSTALL);
            }
            appInfo.setPrizeCount(6 - i + i * i);
            appInfo.setLuckybeanCount(300);
            infos.add(appInfo);
        }

        return infos;
    }

    //应用安装部分数据
    public static ArrayList<AppInfo> getTaskInstallInfos(Context context){
        ArrayList<AppInfo> infos = new ArrayList<AppInfo>();
        Drawable d = context.getResources().getDrawable(
                R.drawable.image_app_sample);
        int state = 0;

        for (int i = 0; i < 4; i ++){
            if (i % 2 == 0) {
                state = TaskState.STATE_INSTALL;
            } else {
                state = TaskState.STATE_OPEN;
            }
            infos.add(new AppInfo(d, "tianyu" + i, 124.0f * i, state, "好游戏啊", i * i, 70));

        }
        return infos;
    }

    // 应用安装部分数据
    public static ArrayList<AppInfo> getTaskDownloadInfos(Context context) {
        ArrayList<AppInfo> infos = new ArrayList<AppInfo>();
        Drawable d = context.getResources().getDrawable(
                R.drawable.image_app_sample);

        for (int i = 0; i < 4; i++) {
            int tmp = 20*i + i*i;
            if (i == 2){
                tmp = 100;
            }
            AppInfo appInfo = new AppInfo();
            appInfo.setImg(d);
            appInfo.setAppName("神庙逃亡" + i);
            appInfo.setAppSize(i * i);
            if (i % 2 == 0) {
                appInfo.setState(TaskState.STATE_DOWNLOAD);
            } else {
                appInfo.setState(TaskState.STATE_PAUSE);
            }
            appInfo.setPrizeCount(6 - i + i * i);
            appInfo.setLuckybeanCount(300);
            appInfo.setRate(25*i);
            appInfo.setProgress(i*10);
            infos.add(appInfo);
        }
        return infos;
    }

    // 应用安装部分数据
    public static ArrayList<AppInfo> getPackageAppInfos(Context context) {
        ArrayList<AppInfo> infos = new ArrayList<AppInfo>();
        Drawable d = context.getResources().getDrawable(
                R.drawable.image_app_sample);

        for (int i = 0; i < 4; i++) {
            int tmp = 20*i + i*i;
            if (i == 2){
                tmp = 100;
            }
            AppInfo appInfo = new AppInfo();
            appInfo.setImg(d);
            appInfo.setAppName("神庙逃亡" + i);
            appInfo.setAppSize(i * i);
            if (i % 2 == 0) {
                appInfo.setState(TaskState.STATE_DOWNLOAD);
                appInfo.setMode(TaskState.MODE_SELECTION);
            } else {
                appInfo.setState(TaskState.STATE_INSTALL);
                appInfo.setMode(TaskState.MODE_INSTALL);
            }
            appInfo.setPrizeCount(6 - i + i * i);
            appInfo.setLuckybeanCount(300);
            appInfo.setRate(25*i);
            appInfo.setProgress(i*10);
            infos.add(appInfo);
        }
        return infos;
    }
}
