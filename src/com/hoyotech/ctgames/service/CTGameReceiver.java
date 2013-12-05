package com.hoyotech.ctgames.service;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 接收系统ACTION_BOOT_COMPLETE和ACTION_TIME_TICK广播启动service<br/>
 * 在android3.1版本以上，只有曾经打开过的应用才能接收到ACTION_BOOT_COMPLETE广播<br/>
 * 应用启动时注册了CTGameReceiver，监听ACTION_TIME_TICK广播，这个广播每分钟发送一次，<br/>
 * CTGameReceiver接收到广播后判断service是否运行，如果没有运行则进行启动
 * Created by GGCoke on 13-12-5.
 */
public class CTGameReceiver extends BroadcastReceiver {
    private static final String TAG = "TAG_CTGameReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        boolean isRunning = false;

        Log.e(TAG, "============> Broadcast is " + action);
        // 只接收ACTION_BOOT_COMPLETED和ACTION_TIME_TICK广播
        if (action.equals(Intent.ACTION_BOOT_COMPLETED) ||
                action.equals(Intent.ACTION_TIME_TICK) ||
                action.equals(Intent.ACTION_USER_PRESENT)) {
            // 获取系统正在运行的服务，判断CTGameService是否在运行
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if ("com.hoyotech.ctgames.service.CTGameService".equals(service.service.getClassName())) {
                    isRunning = true;
                }
            }

            if (!isRunning) {
                Log.e(TAG, "============> CTGameService ISN'T running, start!!!");
                Intent ctgameService = new Intent(context, CTGameService.class);
                ctgameService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    // 需要添加NEW_TASK标记才能启动
                context.startService(ctgameService);
            } else {
                Log.e(TAG, "============> CTGameService IS running.");
            }
        }
    }
}
