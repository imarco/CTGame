package com.hoyotech.ctgames.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GGCoke on 13-12-6.
 */
public class ConfigUtils {
    public static final String PREFERENCE_NAME = "com.hoyotech.ctgames";
    public static final String PREFERENCE_URL_KEY = "url";    // 在SharedPreferences中存储的下载url的key

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_WORLD_WRITEABLE);
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences preferences = getPreferences(context);
        if (preferences != null) {
            return preferences.getString(key, defaultValue);
        }

        return defaultValue;
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences preferences = getPreferences(context);
        if (null != preferences) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    public static void storeURL(Context context, int index, String url) {
        setString(context, PREFERENCE_URL_KEY+index, url);
    }

    public static void clearURL(Context context, int index) {
        setString(context, PREFERENCE_URL_KEY+index, "");
    }

    public static String getURL(Context context, int index, String defaultURL) {
        return getString(context, PREFERENCE_URL_KEY+index, defaultURL);
    }

    public static List<String> getURLArray(Context context) {
        List<String> urlList = new ArrayList<String>();
        for (int i = 0; i < CTGameConstans.MAX_DOWNLOADIND_TASK_COUNT; i++) {
            if (!StringUtils.isEmpty(getURL(context, i, ""))) {
                urlList.add(getURL(context, i, ""));
            }
        }
        return urlList;
    }

}
