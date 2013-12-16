package com.hoyotech.ctgames.adapter;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.alibaba.fastjson.JSONObject;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.activity.AppDetailActivity;
import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.adapter.holder.AppInfoHolder;
import com.hoyotech.ctgames.db.dao.AppDao;
import com.hoyotech.ctgames.service.DownloadService;
import com.hoyotech.ctgames.service.DownloadTask;
import com.hoyotech.ctgames.util.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by GGCoke on 13-12-6.
 */
public class AppInfoAdapter extends BaseAdapter {
    private List<AppInfo> data;
    private Context context;
    private AppInfo appInfo;

    //构造函数
    public AppInfoAdapter(Context context, List<AppInfo> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_simple_info_item, null);
        }

        appInfo = data.get(position);
        convertView.setTag(appInfo.getAppUrl());// 便于通过url找view

        AppInfoHolder holder = new AppInfoHolder(convertView);
        holder.setData(convertView.getContext(), appInfo);
        holder.info = appInfo;

        //设置事件监听响应
        holder.layoutDownloadEnabled.setOnClickListener(new ButtonClickListener(appInfo.getAppUrl(), appInfo, holder));
        holder.layoutInstallEnabled.setOnClickListener(new ButtonClickListener(appInfo.getAppUrl(), appInfo, holder));
        holder.appImageHeader.setOnClickListener(new ButtonClickListener(appInfo.getAppUrl(), appInfo, holder));

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ButtonClickListener implements View.OnClickListener {
        private String url;
        private AppInfoHolder holder;
        private AppInfo info;

        public ButtonClickListener(String url, AppInfo info, AppInfoHolder holder) {
            this.url = url;
            this.info = info;
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            Intent downloadIntet = new Intent(DownloadService.DOWNLOAD_SERVICE_NAME);

            switch (v.getId()) {
                case R.id.layout_download_button_enabled:
                    // 补充响应
                    // 根据按钮的状态决定操作
                    // 点击下载-暂停 点击暂停-继续 点击继续-暂停
                    if(info.getState() == TaskState.STATE_PREPARE) {
                        info.setState(TaskState.STATE_DOWNLOADING);
                        // 通知service开始下载
                        downloadIntet.putExtra(TaskState.DOWNLOAD_STATE, TaskState.STATE_DOWNLOADING);
                        downloadIntet.putExtra(TaskState.DOWNLOAD_URL, info.getAppUrl());
                        downloadIntet.putExtra("action", DownloadTask.ACTION_DOWNLOAD);
                        context.startService(downloadIntet);

                        // 将下载任务放入数据库备用
                        AppDao appDao = new AppDao(context);
                        appDao.addApp(info);
                    } else if(info.getState() == TaskState.STATE_DOWNLOADING) {
                        info.setState(TaskState.STATE_PAUSED);
                        // 通知service暂停下载
                        downloadIntet.putExtra(TaskState.DOWNLOAD_STATE, TaskState.STATE_PAUSED);
                        downloadIntet.putExtra(TaskState.DOWNLOAD_URL, info.getAppUrl());
                        downloadIntet.putExtra("action", DownloadTask.ACTION_DOWNLOAD);
                        context.startService(downloadIntet);
                    } else if(info.getState() == TaskState.STATE_PAUSED) {
                        info.setState(TaskState.STATE_DOWNLOADING);
                        // 通知service继续下载
                        downloadIntet.putExtra(TaskState.DOWNLOAD_STATE, TaskState.STATE_CONTINUE);
                        downloadIntet.putExtra(TaskState.DOWNLOAD_URL, info.getAppUrl());
                        downloadIntet.putExtra("action", DownloadTask.ACTION_DOWNLOAD);
                        context.startService(downloadIntet);
                    }
                    notifyDataSetChanged();
                    break;
                case R.id.layout_install_button_enabled:
                    info.setState(TaskState.STATE_INSTALLING);
                    // 数据库更新应用状态
                    AppDao appDao = new AppDao(context);
                    ContentValues values = new ContentValues();
                    values.put(AppInfo.APPINFO_STATE, TaskState.STATE_INSTALLED);
                    appDao.updateApp(values, AppInfo.APPINFO_APPURL + "=?", new String[] {info.getAppUrl()});

                    // 安装应用，安装成功后再向服务器发送安装成功的请求
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        String fileName = new File(new URL(info.getAppUrl()).getFile()).getName();
                        Uri uri = Uri.fromFile(new File(CTGameConstans.CTGAME_APP_DOWNLOAD_DIR + fileName));
                        intent.setDataAndType(uri,"application/vnd.android.package-archive");
                        context.startActivity(intent);
                    } catch (MalformedURLException e) {}
                    notifyDataSetChanged();
                    break;
                case R.id.app_img_head:
                    // 响应app的图标点击的事件
                    Intent intent = new Intent(context, AppDetailActivity.class);
                    Bundle data = new Bundle();
                    data.putSerializable("appInfo", info);
                    intent.putExtras(data);
                    context.startActivity(intent);
                    break;
                default:
                    // 补充默认情况
                    break;
            }
        }
    }

    public void setListData(List<AppInfo> apps) {
        this.data = apps;
    }

}
