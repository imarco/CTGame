package com.hoyotech.ctgames.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static String name = "ctgames.db";
    private static int version = 1;

    public DBOpenHelper(Context context) {
        super(context, name, null, version);
    }

    // 创建数据库的时候第一次执行
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建数据库，支持整型，字符串，日期和二进制数据
        // app
        // id primary key
        // url download site
        // version_code code standards for the app
        // version_name version name of app
        // name app name
        // summary desc of app
        // image_path image path of app
        // prize_count chance of big wheel
        // luckpean_count luck pea in app
        // state 下载状态，包含了正常状态(0), 下载中(1)，下载完成未安装(2), 下载完成已安装(3), 已安装并打开(4)
        // 需要添加其它状态的可以自己指定
        String sql = "CREATE TABLE app(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT," +
                "version_code INTEGER, " +
                "version_name TEXT, " +
                "url TEXT, " +
                "size BIGINT, " +
                "image_path TEXT, " +
                "summary TEXT, " +
                "prize_count INTEGER, " +
                "luckypean_count INTEGER, " +
                "state INTEGER)";
        db.execSQL(sql);
    }

    // 当要更新数据库的结构时在这个地方执行，一般是更新apk新版本数据库的结构有变化的时候操作
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
