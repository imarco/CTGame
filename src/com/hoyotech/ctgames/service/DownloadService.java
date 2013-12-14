package com.hoyotech.ctgames.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.hoyotech.ctgames.util.CTGameConstans;
import com.hoyotech.ctgames.util.StorageUtils;
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
    boolean downloadOnly3G;
    private DownloadManager manager;
    public static final String DOWNLOAD_SERVICE_NAME = "com.hoyotech.ctgames.service.IDownloadService";

    public void setDownloadOnly3G(boolean downloadOnly3G) {
        manager.setDownloadOnly3G(downloadOnly3G);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        String action = intent.getStringExtra("action");
        boolean downloadOnly3G = intent.getBooleanExtra("downloadOnly3G", false);
        manager.init(CTGameConstans.CTGAME_APP_DOWNLOAD_DIR, action, downloadOnly3G);

        // 接收界面点击事件发送的intent，调用downloadmanager的对应方法
        if (intent.getAction().equals(DOWNLOAD_SERVICE_NAME)) {
            int type = intent.getIntExtra(TaskState.DOWNLOAD_STATE, -1);
            String url;

            switch (type) {
                case TaskState.STATE_START:
                    // 启动service
                    if (!manager.isRunning()) {
                        manager.startManager();
                    } else {
//                        manager.reBroadcastAddAllTask();
                    }
                    break;
                case TaskState.STATE_DOWNLOADING:
                    // 添加下载任务
                    url = intent.getStringExtra(TaskState.DOWNLOAD_URL);
                    long appId = intent.getLongExtra(TaskState.DOWNLOAD_APPID, -1);
                    if (!StringUtils.isEmpty(url) && !manager.hasTask(url)) {
                        manager.addTask(appId, url);
                    }
                    break;
                case TaskState.STATE_PAUSED:
                    // 暂停
                    url = intent.getStringExtra(TaskState.DOWNLOAD_URL);
                    if (!StringUtils.isEmpty(url)) {
                        manager.pauseTask(url);
                    }
                    break;
                case TaskState.STATE_PAUSE_ALL:
                    // 暂停所有
                    manager.pauseAllTask();
                    break;
                case TaskState.STATE_CONTINUE:
                    // 继续
                    url = intent.getStringExtra(TaskState.DOWNLOAD_URL);
                    if (!StringUtils.isEmpty(url)) {
                        manager.continueTask(url);
                    }
                    break;
                case TaskState.STATE_CONTINUE_ALL:
                    // 继续所有
                    manager.continueAllTask();
                    break;
                case TaskState.STATE_STOP:
                    // 停止
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
        public void addTask(long appId, String url) throws RemoteException {
            manager.addTask(appId, url);
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
