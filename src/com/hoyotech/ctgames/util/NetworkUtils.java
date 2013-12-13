package com.hoyotech.ctgames.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 网络相关公用方法
 * Created by GGCoke on 13-12-5.
 */
public class NetworkUtils {
    public static boolean DEBUG = true;
    private static final String TAG = "NetworkUtils";
    public static final boolean NEWWORK_WIFI_STATUS_OPEN = true;
    public static final boolean NEWWORK_WIFI_STATUS_CLOSED = false;
    public static final boolean NEWWORK_3G_STATUS_OPEN = true;
    public static final boolean NEWWORK_3G_STATUS_CLOSED = false;

    /**
     * 是否连接网络，包括wifi和数据网络
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo[] info = manager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    // 已经连接上或者正在连接都是有网络连接
                    if (info[i].getState() == NetworkInfo.State.CONNECTED || info[i].getState() == NetworkInfo.State.CONNECTING) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * wifi是否开启
     * @param context
     * @return wifi开启返回true，否则返回false
     */
    public static boolean getWifiEnabled(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return manager.isWifiEnabled();
    }

    /**
     * 设置wifi状态
     * @param context
     * @param open wifi状态
     */
    public static void setWifiStatus(Context context, boolean open) {
        WifiManager manager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        manager.setWifiEnabled(open);
    }

    /**
     * 判断当前手机网络是否是电信3G<br/>
     * 电信3G的network type是NETWORK_TYPE_EVDO_(0,A,B)
     * @param context
     * @return 是电信3G返回true，否则返回false
     */
    public static boolean is3GNetwork(Context context) {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != manager.getActiveNetworkInfo()) {
            NetworkInfo info = manager.getActiveNetworkInfo();

            // 当前使用的是手机网络
            if (null != info && info.getType() == ConnectivityManager.TYPE_MOBILE) {
                int subType = info.getSubtype();
                return subType == TelephonyManager.NETWORK_TYPE_EVDO_0 ||
                        subType == TelephonyManager.NETWORK_TYPE_EVDO_A ||
                        subType == TelephonyManager.NETWORK_TYPE_EVDO_B;
            }
        }
        return false;
    }

    /**
     * 设置手机3G网络
     * @param context
     * @param status
     */
    public static void set3GNetworkStatus(Context context, boolean status) {
        final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Class conmanClass;
        try {
            conmanClass = Class.forName(conman.getClass().getName());
            final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
            iConnectivityManagerField.setAccessible(true);
            final Object iConnectivityManager = iConnectivityManagerField.get(conman);
            final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
            final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);
            setMobileDataEnabledMethod.invoke(iConnectivityManager, status);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
