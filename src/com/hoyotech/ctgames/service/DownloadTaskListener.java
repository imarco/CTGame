package com.hoyotech.ctgames.service;

public interface DownloadTaskListener {
    /**
     * 下载预处理
     * @param task
     */
    public void preDownload(DownloadTask task);

    /**
     * 更新下载进度
     * @param task
     */
    public void updateProcess(DownloadTask task);

    /**
     * 下载完成
     * @param task
     */
    public void finishDownload(DownloadTask task);

    /**
     * 异常处理
     * @param task
     * @param error
     */
    public void errorDownload(DownloadTask task, Throwable error);
}
