package com.hoyotech.ctgames.db;

import android.content.ContentValues;
import android.content.Context;
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

    public void textApp(Context context) {

        /*AppDao dao = new AppDao(context);


        ContentValues values = new ContentValues();
        values.put(App.NAME, "天天跑酷");
        values.put(App.IMAGE_PATH, "http://www.baidu.com");
        values.put(App.SIZE, 100);
        values.put(App.LUCKPEAN_COUNT, 500);
        values.put(App.PRIZE_COUNT, 2);
        values.put(App.SUMMARY, "玩到爽的游戏");
        values.put(App.STATE, 1);
        values.put(App.VERSION_CODE, 1);
        values.put(App.VERSION_NAME, "1.9.20");
        values.put(App.URL, "http://www.ichangge.net");

        System.out.println(dao.addAPP(values));
        values.put(App.IMAGE_PATH, "http://www.google.com");
        System.out.println(dao.addAPP(values));

        List<App> lists = dao.queryAppByState(1);

        for (int i = 0; i < lists.size(); i++) {
            System.out.println(lists.get(i));
        }*/


        /*App app = dao.queryAppByUrl("http://www.ichangge.net");
        System.out.println(app);

        dao.deleteAppByUrl("http://www.ichangge.net");
        app = dao.queryAppByUrl("http://www.ichangge.net");
        System.out.println(app);*/

        /*dao.deleteApp("url = ?", new String[]{"http://www.ichangge.net"});
        App app = dao.queryAppByUrl("http://www.ichangge.net");
        System.out.println(app);*/

        /*values = new ContentValues();
        values.put(App.STATE, 10);
        values.put(App.LUCKPEAN_COUNT, 1000);
        values.put(App.VERSION_CODE, 2);
        values.put(App.VERSION_NAME, "2.0");

        dao.updateApp(values, "url = ?" , new String[] {"http://www.ichangge.net"});
        System.out.println(dao.queryAppByUrl("http://www.ichangge.net"));*/

    }

}
