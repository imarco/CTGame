package com.hoyotech.ctgames.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.bean.AppInfo;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-5
 * Time: 下午9:11
 * To change this template use File | Settings | File Templates.
 */
public class TestActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);


    }

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
                appInfo.setState(0);
            } else {
                appInfo.setState(1);
            }
            appInfo.setPrizeCount(6 - i + i * i);
            appInfo.setLuckybeanCount(300);
            infos.add(appInfo);
        }

        return infos;
    }
}