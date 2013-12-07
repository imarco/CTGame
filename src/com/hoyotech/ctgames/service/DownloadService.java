package com.hoyotech.ctgames.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.hoyotech.ctgames.util.StringUtils;
import com.hoyotech.ctgames.util.TaskState;

/**
 * 下载service<br/>
 * 使用时需要在intent中指定的参数: <br/>
 * action: 开始service的activity自定义的字符串，用于receiver中筛选intent<br/>
 * path: 下载文件存放目录，如果没有则为null<br/>
 * downloadOnly3G: 是否只支持3G下载
 * Created by GGCoke on 13-12-6.
 */
public class DownloadService extends Service {
    private DownloadManager manager;

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        String action = intent.getStringExtra("action");
        String path = intent.getStringExtra("path");
        boolean downloadOnly3G = intent.getBooleanExtra("downloadOnly3G", true);
        manager.init(path, action, downloadOnly3G);

        if (intent.getAction().equals("com.hoyotech.ctgames.service.IAIDLDownloadService")) {
            int type = intent.getIntExtra(TaskState.DOWNLOAD_STATE, -1);
            String url;

            switch (type) {
                case TaskState.STATE_DOWNLOAD:
                    url = intent.getStringExtra(TaskState.DOWNLOAD_URL);
                    if (!StringUtils.isEmpty(url) && !manager.hasTask(url)) {
                        manager.addTask(url);
                    }
                    break;
                case TaskState.STATE_PAUSE:
                    url = intent.getStringExtra(TaskState.DOWNLOAD_URL);
                    if (!StringUtils.isEmpty(url)) {
                        manager.pauseTask(url);
                    }
                    break;
                case TaskState.STATE_PAUSE_ALL:
                    manager.pauseAllTask();
                    break;
                case TaskState.STATE_CONTINUE:
                    url = intent.getStringExtra(TaskState.DOWNLOAD_URL);
                    if (!StringUtils.isEmpty(url)) {
                        manager.continueTask(url);
                    }
                    break;
                case TaskState.STATE_CONTINUE_ALL:
                    manager.continueAllTask();
                    break;
                case TaskState.STATE_STOP:
                    manager.close();
                    break;

                default:
                    break;
            }
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = new DownloadManager(this);
    }

    private IAIDLDownloadService.Stub mBinder = new IAIDLDownloadService.Stub() {
        @Override
        public void startManage() throws RemoteException {
            manager.startManager();
        }

        @Override
        public void addTask(String url) throws RemoteException {
            manager.addTask(url);
        }

        @Override
        public void pauseTask(String url) throws RemoteException {
            manager.pauseTask(url);
        }

        @Override
        public void continueTask(String url) throws RemoteException {
            manager.continueTask(url);
        }

        @Override
        public void deleteTask(String url) throws RemoteException {
            manager.deleteTask(url);
        }
    };
}
