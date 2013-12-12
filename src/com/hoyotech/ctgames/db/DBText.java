package com.hoyotech.ctgames.db;

import android.content.ContentValues;
import android.content.Context;

import com.hoyotech.ctgames.adapter.bean.AppInfo;
import com.hoyotech.ctgames.db.bean.App;
import com.hoyotech.ctgames.db.dao.AppDao;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-10
 * Time: 上午10:54
 * To change this template use File | Settings | File Templates.
 */
public class DBText {

    public static void textApp(Context context) {
        AppDao dao = new AppDao(context);
        AppInfo appInfo = new AppInfo(1,
                "http://appimg1.3g.qq.com/android_new/852/792852/16912286/icon_72.png",
                "海豚浏览器",
                13830717,
                10,
                2,
                "谢谢大家的支持",
                "http://122.193.23.63/down.myapp.com/android_new/852/792852/16912286/com.dolphin.browser.xf_133.apk?mkey=52a6f7dbb3c26326&f=d688&p=.apk",
                "10.4.0", "aaaaaaaaaaaaaaaa", "", "");
        dao.addApp(appInfo);
    }

}
