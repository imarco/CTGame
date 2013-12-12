package com.hoyotech.ctgames.service;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.hoyotech.ctgames.adapter.bean.AppInfo;
import com.hoyotech.ctgames.db.dao.AppDao;
import com.hoyotech.ctgames.util.CTGameConstans;
import com.hoyotech.ctgames.util.ConfigUtils;
import com.hoyotech.ctgames.util.StorageUtils;
import com.hoyotech.ctgames.util.TaskState;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by GGCoke on 13-12-6.
 */
public class DownloadManager extends Thread {
    private static final String TAG = DownloadManager.class.getSimpleName();
    private Context mContext;
    private TaskQueue mTaskQueue;    // 等待下载
    private List<DownloadTask> mDownloadingTasks; // 正在下载
    private List<DownloadTask> mPausingTasks;     // 暂停状态
    private String action;    // 使用DownloadManager的activity类
    private Boolean isRunning = false;
    private String path;
    private boolean downloadOnly3G;

    public DownloadManager(Context context) {
        this.mContext = context;
        this.mTaskQueue = new TaskQueue();
        this.mDownloadingTasks = new ArrayList<DownloadTask>();
        this.mPausingTasks = new ArrayList<DownloadTask>();
    }

    public void init (String path, String action, boolean downloadOnly3G) {
        this.path = path;
        this.action = action;
        this.downloadOnly3G = downloadOnly3G;
    }

    public void setDownloadOnly3G(boolean downloadOnly3G) {
        this.downloadOnly3G = downloadOnly3G;
    }

    @Override
    public void run() {
        super.run();
        while (isRunning) {
            DownloadTask task = mTaskQueue.poll();
            if (null != task) {
                mDownloadingTasks.add(task);

                // SDK 11之后AsyncTask默认是串行执行，需要调用executeOnExecutor，传入并行执行的executor THREAD_POOL_EXECUTOR
                if (Build.VERSION.SDK_INT >= 11) {
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    task.execute();
                }
            }

        }
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void startManager() {
        this.isRunning = true;
        this.start();
        checkUncompleteTasks();
    }

    public void close() {
        this.isRunning = false;
        pauseAllTask();
        this.stop();
    }

    /**
     * 等待下载任务数量
     * @return
     */
    public int getQueueTaskCount() {
        return mTaskQueue.size();
    }

    /**
     * 正在下载任务数量
     * @return
     */
    public int getDownloadingTaskCount() {
        return mDownloadingTasks.size();
    }

    /**
     * 暂停的任务数量
     * @return
     */
    public int getPausingTaskCount() {
        return mPausingTasks.size();
    }

    /**
     * 总任务数量
     * @return
     */
    public int getTotalTaskCount() {
        return getQueueTaskCount() + getDownloadingTaskCount() + getPausingTaskCount();
    }

    /**
     * 添加下载任务
     * @param url 下载url
     * @return
     */
    public int addTask(String url) {
        DownloadTask task = null;
        if (!StorageUtils.hasSDCard()) {
            return CTGameConstans.DOWNLOAD_TASK_ADD_NO_SDCARD;
        }

        if (StorageUtils.isSDCardReadOnly()) {
            return CTGameConstans.DOWNLOAD_TASK_ADD_SDCARD_READONLY;
        }

        if (getTotalTaskCount() >= CTGameConstans.MAX_DOWNLOAD_TASK_COUNT) {
            return CTGameConstans.DOWNLOAD_TASK_ADD_TASKQUEUE_FULL;
        }

        try {
            task = newDownloadTask(url);
            addTask(task);
        } catch (MalformedURLException e) {
            System.out.println(5 + url + getClass().getName());
        }

        return CTGameConstans.DOWNLOAD_TASK_ADD_OK;
    }

    private DownloadTask newDownloadTask(String url) throws MalformedURLException {
        DownloadTask task = new DownloadTask(mContext, url, path, downloadOnly3G);
        DownloadTaskListener listener = new DownloadTaskListener() {
            @Override
            public void preDownload(DownloadTask task) {
                ConfigUtils.storeURL(mContext, task.getId(), task.getUrl());
            }

            @Override
            public void updateProcess(DownloadTask task) {
                Intent updateIntent = new Intent(action);
                updateIntent.putExtra(TaskState.DOWNLOAD_STATE, TaskState.STATE_DOWNLOADING);
                updateIntent.putExtra(TaskState.DOWNLOAD_SPEED, task.getDownloadSpeed() + "kbps");
                updateIntent.putExtra(TaskState.DOWNLOAD_PROGRESS, task.getDownloadPercent() + "");
                updateIntent.putExtra(TaskState.DOWNLOAD_URL, task.getUrl());
                mContext.sendBroadcast(updateIntent);
            }

            @Override
            public void finishDownload(DownloadTask task) {
                completeTask(task);
            }

            @Override
            public void errorDownload(DownloadTask task, Throwable error) {
                // TODO 异常处理
            }
        };

        task.setListener(listener);
        return task;
    }

    private void addTask(DownloadTask task) {
        broadcastAddTask(task.getId(), task.getUrl());
        mTaskQueue.offer(task);
        if (!this.isAlive()) {
            this.startManager();
        }
    }

    private void broadcastAddTask(long id, String url) {
        broadcastAddTask(id, url, false);
    }

    private void broadcastAddTask(long id, String url, boolean isInterrupt) {
        Intent nofityIntent = new Intent(action);
        nofityIntent.putExtra(TaskState.DOWNLOAD_STATE, TaskState.STATE_DOWNLOADING);
        nofityIntent.putExtra(TaskState.DOWNLOAD_ID, id);
        nofityIntent.putExtra(TaskState.DOWNLOAD_URL, url);
        nofityIntent.putExtra(TaskState.DOWNLOAD_PAUSED, isInterrupt);
        mContext.sendBroadcast(nofityIntent);
    }

    public void reBroadcastAddAllTask() {

        DownloadTask task;
        for (int i = 0; i < mDownloadingTasks.size(); i++) {
            task = mDownloadingTasks.get(i);
            broadcastAddTask(task.getId(), task.getUrl(), task.isInterrupt());
        }
        for (int i = 0; i < mTaskQueue.size(); i++) {
            task = mTaskQueue.get(i);
            broadcastAddTask(task.getId(), task.getUrl());
        }
        for (int i = 0; i < mPausingTasks.size(); i++) {
            task = mPausingTasks.get(i);
            broadcastAddTask(task.getId(), task.getUrl());
        }
    }

    public void checkUncompleteTasks() {
        List<String> urlList = ConfigUtils.getURLArray(mContext);
        if (urlList != null && urlList.size() > 0) {
            for (int i = 0; i < urlList.size(); i++) {
                addTask(urlList.get(i));
            }
        }
    }

    public DownloadTask getTask(int position) {
        if (position > mDownloadingTasks.size()) {
            return mTaskQueue.get(position - mDownloadingTasks.size());
        } else {
            return mDownloadingTasks.get(position);
        }
    }

    public boolean hasTask(String url) {
        for (DownloadTask task : mDownloadingTasks) {
            if (task.getUrl().equalsIgnoreCase(url))
                return true;
        }

        for (int i = 0; i < mTaskQueue.size(); i++) {
            if (mTaskQueue.get(i).getUrl().equalsIgnoreCase(url))
                return true;
        }

        return false;
    }

    /**
     * 暂停下载任务
     * @param task
     */
    public synchronized void pauseTask(DownloadTask task) {
        if (null == task) return;
        task.onCancelled();
        String url = task.getUrl();


        try {
            // 将下载任务放到暂停任务列表里
            mDownloadingTasks.remove(task);
            task = newDownloadTask(url);
            mPausingTasks.add(task);

            // 更新任务状态为暂停
            AppDao appDao = new AppDao(mContext);
            AppInfo info = appDao.queryAppByUrl(task.getUrl());
            ContentValues values = new ContentValues();
            values.put(AppInfo.APPINFO_STATE, TaskState.STATE_PAUSED);
            appDao.updateApp(values, AppInfo.APPINFO_APPURL + "=?", new String[] {info.getAppUrl()});
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 暂停下载任务
     * @param url
     */
    public synchronized void pauseTask(String url) {
        DownloadTask task = null;
        for (int i = 0; i < mDownloadingTasks.size(); i++) {
            task = mDownloadingTasks.get(i);
            if (null != task && task.getUrl().equalsIgnoreCase(url)) {
                pauseTask(task);
            }
        }
    }

    /**
     * 暂停所有任务，包括正在下载和等待下载。<br/>
     * 先暂停等待下载，后暂停正在下载，这样在恢复所有下载的时候就从暂停列表倒推开始下载
     */
    public synchronized void pauseAllTask() {
        DownloadTask task = null;

        for (int i = 0; i < mTaskQueue.size(); i++) {
            task = mTaskQueue.get(i);
            if (null != task) {
                mTaskQueue.remove(task);
                mPausingTasks.add(task);
            }
        }

        for (int i = 0; i < mDownloadingTasks.size(); i++) {
            task = mDownloadingTasks.get(i);
            if (null != task) {
                pauseTask(task);
            }
        }
    }

    /**
     * 继续下载
     * @param task
     */
    public synchronized void continueTask(DownloadTask task) {
        if (null != task) {
            mPausingTasks.remove(task);
            mTaskQueue.offer(task);
            if (!this.isAlive()) {
                this.startManager();
            }

            // 更新任务状态为下载
            AppDao appDao = new AppDao(mContext);
            AppInfo info = appDao.queryAppByUrl(task.getUrl());
            ContentValues values = new ContentValues();
            values.put(AppInfo.APPINFO_STATE, TaskState.STATE_DOWNLOADING);
            appDao.updateApp(values, AppInfo.APPINFO_APPURL + "=?", new String[] {info.getAppUrl()});
        }
    }

    /**
     * 继续下载
     * @param url
     */
    public synchronized void continueTask(String url) {
        for (DownloadTask task : mPausingTasks) {
            if (null != task && task.getUrl().equals(url)) {
                continueTask(task);
            }
        }
    }

    public synchronized void continueAllTask() {
        for (int i = mPausingTasks.size() - 1; i >= 0; i--) {
            DownloadTask task = mPausingTasks.get(i);
            if (null != task) {
                continueTask(task);
            }
        }
    }

    /**
     * 删除任务，从正在下载列表，等待下载列表和暂停列表里查找并删除
     * @param url
     */
    public synchronized void deleteTask(String url) {
        DownloadTask task = null;
        for (int i = 0; i < mDownloadingTasks.size(); i++) {
            task = mDownloadingTasks.get(i);
            if (null != task && task.getUrl().equals(url)) {
                // 删除文件
                File tmpFile = task.getTmpFile();
                if (tmpFile.exists()) {
                    tmpFile.delete();
                }
                File file = task.getFile();
                if (file.exists()) {
                    file.delete();
                }

                task.onCancelled();
                completeTask(task);
                return;
            }
        }

        for (int i = 0; i < mTaskQueue.size(); i++) {
            task = mTaskQueue.get(i);
            if (null != task && task.getUrl().equals(url))
                mTaskQueue.remove(task);
        }

        for (int i = 0; i < mPausingTasks.size(); i++) {
            task = mTaskQueue.get(i);
            if (null != task && task.getUrl().equals(url))
                mTaskQueue.remove(task);
        }
    }

    /**
     * 下载完成
     * @param task
     */
    public synchronized void completeTask(DownloadTask task) {
        if (mDownloadingTasks.contains(task)) {
            ConfigUtils.clearURL(mContext, task.getId());
            mDownloadingTasks.remove(task);

            // 更新任务状态为完成
            AppDao appDao = new AppDao(mContext);
            AppInfo info = appDao.queryAppByUrl(task.getUrl());
            ContentValues values = new ContentValues();
            values.put(AppInfo.APPINFO_STATE, TaskState.STATE_COMPLETE);
            appDao.updateApp(values, AppInfo.APPINFO_APPURL + "=?", new String[] {info.getAppUrl()});

            Intent nofityIntent = new Intent(action);
            nofityIntent.putExtra(TaskState.DOWNLOAD_STATE, TaskState.STATE_COMPLETE);
            nofityIntent.putExtra(TaskState.DOWNLOAD_ID, task.getId());
            nofityIntent.putExtra(TaskState.DOWNLOAD_URL, task.getUrl());
            mContext.sendBroadcast(nofityIntent);
        }
    }


    private class TaskQueue {
        private Queue<DownloadTask> taskQueue;

        public TaskQueue() {
            taskQueue = new LinkedList<DownloadTask>();
        }

        public void offer(DownloadTask task) {
            taskQueue.offer(task);
        }

        public DownloadTask poll() {
            DownloadTask task = null;
            // 生产者模式，如果正在下载列表满或者等待下载列表为空，则等待1s后重新检查
            while (mDownloadingTasks.size() >= CTGameConstans.MAX_DOWNLOADIND_TASK_COUNT ||
                    (task = taskQueue.poll()) == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();;
                }
            }
            return task;
        }
        public DownloadTask get(int position) {
            if (position >= size()) {
                return null;
            }
            return ((LinkedList<DownloadTask>) taskQueue).get(position);
        }

        public int size() {
            return taskQueue.size();
        }

        @SuppressWarnings("unused")
        public boolean remove(int position) {
            return taskQueue.remove(get(position));
        }

        public boolean remove(DownloadTask task) {
            return taskQueue.remove(task);
        }
    }

}
