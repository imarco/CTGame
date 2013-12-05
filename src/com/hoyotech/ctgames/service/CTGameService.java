package com.hoyotech.ctgames.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by GGCoke on 13-12-5.
 */
public class CTGameService extends Service {
    private static final String TAG = "TAG_CTGameService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "============> CTGameService.onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.e(TAG, "============> CTGameService.onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "============> CTGameService.onStartCommand()");
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "============> CTGameService.onDestroy");
        Intent localIntent = new Intent(this, CTGameService.class);
        startService(localIntent);
    }
}
