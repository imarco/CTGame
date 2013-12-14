package com.hoyotech.ctgames.service;

interface IAIDLDownloadService {
    void startManage();
    void addTask(long appId, String url);
    void pauseTask(String url);
    void continueTask(String url);
    void deleteTask(String url);
}