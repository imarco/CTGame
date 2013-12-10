package com.hoyotech.ctgames.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.hoyotech.ctgames.db.DBOpenHelper;
import com.hoyotech.ctgames.db.bean.App;

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
     * 添加一条app的信息
     * @param values 插入的键值对信息
     * @return
     */
    public boolean addAPP(ContentValues values) {
        boolean flag = false;
        SQLiteDatabase database = null;
        try {
            database = helper.getWritableDatabase();
            database.insert(App.TABLE_NAME, null, values);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(database != null) {
                database.close();
            }
        }
        return flag;
    }

    /**
     * 添加一条app的信息
     * @param app app实例
     * @return
     */
    public boolean addAPP(App app) {
        boolean flag = false;
        SQLiteDatabase database = null;
        ContentValues values = new ContentValues();

        // 填充键值对
        values.put(App.NAME, app.getName());
        values.put(App.IMAGE_PATH, app.getImage_path());
        values.put(App.SIZE, app.getSize());
        values.put(App.LUCKPEAN_COUNT, app.getLuckypean_count());
        values.put(App.PRIZE_COUNT, app.getPrize_count());
        values.put(App.SUMMARY, app.getSummary());
        values.put(App.STATE, app.getState());
        values.put(App.VERSION_CODE, app.getVersion_code());
        values.put(App.VERSION_NAME, app.getVersion_name());
        values.put(App.URL, app.getUrl());

        try {
            database = helper.getWritableDatabase();
            database.insert(App.TABLE_NAME, null, values);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(database != null) {
                database.close();
            }
        }

        return flag;
    }

    /**
     * 通过url找app信息
     * @param url
     * @return
     */
    public App queryAppByUrl(String url) {
        SQLiteDatabase database = null;
        try {
            database = helper.getReadableDatabase();
            Cursor cursor = database.rawQuery("select * from app where url = ? limit 1",
                    new String[] { url });

            if (cursor.getCount() == 0) {
                cursor.close();
                return new App();
            }

            App app = new App();
            cursor.moveToFirst();

            app.setId(cursor.getInt(0));
            app.setName(cursor.getString(1));
            app.setVersion_code(cursor.getInt(2));
            app.setVersion_name(cursor.getString(3));
            app.setUrl(cursor.getString(4));
            app.setSize(cursor.getLong(5));
            app.setImage_path(cursor.getString(6));
            app.setSummary(cursor.getString(7));
            app.setPrize_count(cursor.getInt(8));
            app.setLuckypean_count(cursor.getInt(9));
            app.setState(cursor.getInt(10));

            cursor.close();

            return app;
        } catch (Exception e) {
            e.printStackTrace();
            return new App();
        } finally {
            if(database != null) {
                database.close();
            }
        }
    }

    /**
     * 根据歌曲简写查询歌曲所有信息
     *
     * @param state app的状态
     * @return
     */
    public List<App> queryAppByState(int state) {
        SQLiteDatabase database = null;
        try {
            database = helper.getReadableDatabase();
            Cursor cursor = database.rawQuery(
                    "select * from app where state = ? ",
                    new String[] { String.valueOf(state) });

            if (cursor.getCount() == 0) {
                cursor.close();
                return new ArrayList<App>();
            }

            List<App> l = new ArrayList<App>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                App app = new App();

                app.setId(cursor.getInt(0));
                app.setName(cursor.getString(1));
                app.setVersion_code(cursor.getInt(2));
                app.setVersion_name(cursor.getString(3));
                app.setUrl(cursor.getString(4));
                app.setSize(cursor.getLong(5));
                app.setImage_path(cursor.getString(6));
                app.setSummary(cursor.getString(7));
                app.setPrize_count(cursor.getInt(8));
                app.setLuckypean_count(cursor.getInt(9));
                app.setState(cursor.getInt(10));

                l.add(app);
                cursor.moveToNext();
            }

            cursor.close();

            return l;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<App>();
        } finally {
            if(database != null) {
                database.close();
            }
        }
    }

    /**
     * 通过url删除一条app的信息
     * @param url
     * @return
     */
    public boolean deleteAppByUrl(String url) {
        boolean flag = false;
        SQLiteDatabase database = null;
        try {
            database = helper.getWritableDatabase();
            String sql = "delete from app where url = ? ";
            database.execSQL(sql, new String[] { url });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            return flag;
        } finally {
            if(database != null) {
                database.close();
            }
        }

        return flag;
    }

    /**
     * 删除app表中的一些信息(参数参见api中SQLiteDatabase)
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public boolean deleteApp(String whereClause, String[] whereArgs) {

        boolean flag = false;
        SQLiteDatabase database = null;
        try {
            database = helper.getWritableDatabase();
            database.delete(App.TABLE_NAME, whereClause, whereArgs);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            return flag;
        } finally {
            if(database != null) {
                database.close();
            }
        }

        return flag;

    }

    /**
     * 更新一条信息(参数参见api中SQLiteDatabase)
     * @param values
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public boolean updateApp(ContentValues values, String whereClause, String[] whereArgs) {
        boolean flag = false;
        SQLiteDatabase database = null;
        try {
            database = helper.getWritableDatabase();
            database.update(App.TABLE_NAME, values, whereClause, whereArgs);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            return flag;
        } finally {
            if(database != null) {
                database.close();
            }
        }

        return flag;
    }



}
