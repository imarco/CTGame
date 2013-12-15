package com.hoyotech.ctgames.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.db.DBOpenHelper;
import com.hoyotech.ctgames.util.TaskState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-10
 * Time: 上午9:38
 * To change this template use File | Settings | File Templates.
 */
public class AppDao {
    private DBOpenHelper helper = null;

    public AppDao(Context context) {
        helper = new DBOpenHelper(context);
    }

    /**
     * 插入app信息
     *
     * @param appInfo
     * @return
     */
    public boolean addApp(AppInfo appInfo) {
        boolean flag = false;
        SQLiteDatabase database = null;

        try {
            // 查询是否下载过，如果已经完成过下载，则更新状态为下载中，否则添加新纪录
            AppInfo existApp = queryAppById(appInfo.getAppId());
            if (existApp != null && existApp.isHasDownloaded()) {
                ContentValues values = new ContentValues();
                values.put(AppInfo.APPINFO_STATE, TaskState.STATE_DOWNLOADING);
                updateApp(values, AppInfo.APPINFO_APPURL + "=?", new String[] {existApp.getAppUrl()});
            } else {
                ContentValues values = contentWrapper(appInfo);
                // 插入应用信息
                database = helper.getWritableDatabase();
                long result = database.insert(AppInfo.APPINFO_TABLE_NAME, null, values);
                if (-1 == result) {
                    System.out.println("插入失败 in AppDap.addApp");
                }
            }
            flag = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return flag;
        } finally {
            if (null != database) {
                database.close();
            }
        }

        return flag;
    }


    private ContentValues contentWrapper(AppInfo appInfo) {
        ContentValues values = new ContentValues();
        values.put(AppInfo.APPINFO_APPID, appInfo.getAppId());
        values.put(AppInfo.APPINFO_APPLOGOURL, appInfo.getAppLogoUrl());
        values.put(AppInfo.APPINFO_APPNAME, appInfo.getAppName());
        values.put(AppInfo.APPINFO_APPSIZE, appInfo.getAppSize());
        values.put(AppInfo.APPINFO_LUCKYBEANSNUM, appInfo.getLuckyBeansNum());
        values.put(AppInfo.APPINFO_LOTTERYNUM, appInfo.getLotteryNum());
        values.put(AppInfo.APPINFO_APPDESC, appInfo.getAppDesc());
        values.put(AppInfo.APPINFO_VERSION, appInfo.getVersion());
        values.put(AppInfo.APPINFO_APPURL, appInfo.getAppUrl());
        values.put(AppInfo.APPINFO_APPMD5, appInfo.getMD5());
        values.put(AppInfo.APPINFO_AD, appInfo.getAd());
        values.put(AppInfo.APPINFO_APPPICURLS, appInfo.getAppPicUrls());
        values.put(AppInfo.APPINFO_MODE, appInfo.getMode());
        values.put(AppInfo.APPINFO_STATE, appInfo.getState());

        return values;
    }

    private List<AppInfo> appInfoWrapper (Cursor cursor) {
        if (null == cursor || cursor.getCount() == 0) {
            return new ArrayList<AppInfo>();
        }

        List<AppInfo> apps = new ArrayList<AppInfo>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            AppInfo appInfo = new AppInfo(
                    cursor.getLong(0),          // addId
                    cursor.getString(1),        // appLogUrl
                    cursor.getString(2),        // appName
                    cursor.getLong(3),          // appSize
                    cursor.getInt(4),           // luckyBeansNum
                    cursor.getInt(5),           // lotteryNum
                    cursor.getString(6),        // appDesc
                    cursor.getString(7),        // version
                    cursor.getString(8),        // appUrl
                    cursor.getString(9),        // MD5
                    cursor.getString(10),       // ad
                    cursor.getString(11)        // appPicUrls
            );

            appInfo.setMode(cursor.getInt(12));     // mode
            appInfo.setState(cursor.getInt(13));    // state
            appInfo.setHasDownloaded(cursor.getInt(14) == TaskState.APP_DOWNLOADED_HAS ? true:false);
            apps.add(appInfo);
            cursor.moveToNext();
        }
        return apps;
    }

    /**
     * 根据应用ID获取应用信息
     * @param appId 应用ID
     * @return 应用信息，如果没有对应记录，返回null
     */
    public AppInfo queryAppById(long appId) {
        SQLiteDatabase database = null;
        try {
            database = helper.getReadableDatabase();
            Cursor cursor = database.rawQuery(
                    "select * from app where appId = ? limit 1",
                    new String[]{String.valueOf(appId)});
            List<AppInfo> apps = appInfoWrapper(cursor);
            cursor.close();
            return (null == apps || apps.size() <= 0) ? null : apps.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    /**
     * 根据URL获取应用信息
     * @param url 应用下载url
     * @return 应用信息，如果没有对应记录，返回null
     */
    public AppInfo queryAppByUrl(String url) {
        SQLiteDatabase database = null;
        List<AppInfo> apps = new ArrayList<AppInfo>();
        try {
            database = helper.getReadableDatabase();
            Cursor cursor = database.rawQuery(
                    "select * from app where appUrl = ? limit 1",
                    new String[]{url});
            apps = appInfoWrapper(cursor);
            cursor.close();
            return (null == apps || apps.size() <= 0) ? null : apps.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    /**
     * 根据礼包应用状态获取应用信息
     * @param mode 礼包应用状态
     * @return 应用信息，如果没有对应记录，返回null
     */
    public List<AppInfo> queryAppsByMode(int mode) {
        SQLiteDatabase database = null;
        List<AppInfo> apps = new ArrayList<AppInfo>();
        try {
            database = helper.getReadableDatabase();
            Cursor cursor = database.rawQuery(
                    "select * from app where mode = ? ",
                    new String[]{String.valueOf(mode)});
            apps = appInfoWrapper(cursor);
            cursor.close();
            return apps;
        } catch (Exception e) {
            e.printStackTrace();
            return apps;
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    /**
     * 根据应用状态获取应用信息
     * @param state 礼包应用状态
     * @return 应用信息，如果没有对应记录，返回null
     */
    public List<AppInfo> queryAppsByState(int state) {
        SQLiteDatabase database = null;
        List<AppInfo> apps = new ArrayList<AppInfo>();
        try {
            database = helper.getReadableDatabase();
            Cursor cursor = database.rawQuery(
                    "select * from app where state = ? ",
                    new String[]{String.valueOf(state)});
            apps = appInfoWrapper(cursor);
            cursor.close();
            return apps;
        } catch (Exception e) {
            e.printStackTrace();
            return apps;
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    /**
     * 根据条件删除应用记录
     * @param whereClause where语句
     * @param whereArgs where语句中“?”对应的值
     * @return 删除成功返回true，否则返回false
     */
    public boolean deleteApp(String whereClause, String[] whereArgs) {
        boolean flag = false;
        SQLiteDatabase database = null;
        try {
            database = helper.getWritableDatabase();
            database.delete(AppInfo.APPINFO_TABLE_NAME, whereClause, whereArgs);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            return flag;
        } finally {
            if (database != null) {
                database.close();
            }
        }

        return flag;
    }

    /**
     * 更新应用信息
     * @param values 更新后的值
     * @param whereClause where语句
     * @param whereArgs where语句中“?”对应的值
     * @return 更新成功返回true，否则返回false
     */
    public boolean updateApp(ContentValues values, String whereClause, String[] whereArgs) {
        boolean flag = false;
        SQLiteDatabase database = null;
        try {
            database = helper.getWritableDatabase();
            database.update(AppInfo.APPINFO_TABLE_NAME, values, whereClause, whereArgs);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            return flag;
        } finally {
            if (database != null) {
                database.close();
            }
        }

        return flag;
    }
}
