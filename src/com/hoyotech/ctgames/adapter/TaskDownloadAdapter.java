package com.hoyotech.ctgames.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.activity.AppDetailActivity;
import com.hoyotech.ctgames.adapter.holder.TaskInstallHolder;
import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.adapter.holder.TaskDownloadHolder;
import com.hoyotech.ctgames.db.dao.AppDao;
import com.hoyotech.ctgames.service.DownloadService;
import com.hoyotech.ctgames.service.DownloadTask;
import com.hoyotech.ctgames.util.NetworkUtils;
import com.hoyotech.ctgames.util.TaskState;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-6
 * Time: 下午8:26
 * To change this template use File | Settings | File Templates.
 */
public class TaskDownloadAdapter extends BaseAdapter{

    //定义相关的变量
    private Context context;
    private List<AppInfo> data;
    AppInfo appInfo;

    //构造函数
    public TaskDownloadAdapter(Context context, List<AppInfo> data){
        this.context = context;
        this.data = data;
    }

    public void setData(List<AppInfo> data) {
        this.data = data;
    }


    /**
     * 添加一条信息
     * @param info appinfo信息
     */
    public void addItem(AppInfo info) {
        data.add(info);
        this.notifyDataSetChanged();
    }

    /**
     * 根据url删除一条信息
     * @param url url
     */
    public void removeItem(String url) {
        String tmp;
        for (int i = 0; i < data.size(); i++) {
            tmp = data.get(i).getAppUrl();
            if (tmp.equals(url)) {
                data.remove(i);
                this.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        TaskDownloadHolder holder = null;


        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_task_download_item, null);
            holder = new TaskDownloadHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (TaskDownloadHolder) convertView.getTag();
        }

        appInfo = data.get(position);
        holder.setData(convertView.getContext(), appInfo);
        holder.info = appInfo;

        //设置事件监听响应
        holder.btnOptions.setOnClickListener(new ButtonClickListener(appInfo.getAppUrl(), appInfo, holder));
        holder.appImageHeader.setOnClickListener(new ButtonClickListener(appInfo.getAppUrl(), appInfo, holder));
        return convertView;
    }

    private class ButtonClickListener implements View.OnClickListener {
        private String url;
        private TaskDownloadHolder holder;
        private AppInfo info;

        public ButtonClickListener(String url, AppInfo info, TaskDownloadHolder holder) {
            this.url = url;
            this.info = info;
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            Intent downloadIntet = new Intent(DownloadService.DOWNLOAD_SERVICE_NAME);

            switch (v.getId()) {
                case R.id.btn_options:
                    // 补充响应
                    // 根据按钮的状态决定操作
                    // 点击下载-暂停 点击暂停-继续 点击继续-暂停
                    if(info.getState() == TaskState.STATE_PREPARE) {
                        if (!NetworkUtils.isNetworkAvailable(context)) {
                            Toast.makeText(context, R.string.network_no_network, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (!NetworkUtils.is3GNetwork(context)) {

                            new AlertDialog.Builder(context).setMessage(R.string.network_open_3g).setPositiveButton(R.string.network_open_3g_confirm,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 打开3G网络并关闭WIFI
                                            NetworkUtils.set3GNetworkStatus(context, true);
                                            NetworkUtils.setWifiStatus(context, false);
                                            info.setState(TaskState.STATE_DOWNLOADING);
                                            holder.setButtonState(context, info);
                                            // 通知service开始下载
                                            Intent downloadIntet = new Intent(DownloadService.DOWNLOAD_SERVICE_NAME);
                                            downloadIntet.putExtra(TaskState.DOWNLOAD_STATE, TaskState.STATE_DOWNLOADING);
                                            downloadIntet.putExtra(TaskState.DOWNLOAD_URL, info.getAppUrl());
                                            downloadIntet.putExtra("action", DownloadTask.ACTION_DOWNLOAD);
                                            context.startService(downloadIntet);

                                            // 将下载任务放入数据库备用
                                            AppDao appDao = new AppDao(context);
                                            appDao.addApp(info);
                                        }
                                    }).setNegativeButton(R.string.network_open_3g_cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                        } else {
                            info.setState(TaskState.STATE_DOWNLOADING);
                            holder.setButtonState(context, info);
                            // 通知service开始下载
                            downloadIntet = new Intent(DownloadService.DOWNLOAD_SERVICE_NAME);
                            downloadIntet.putExtra(TaskState.DOWNLOAD_STATE, TaskState.STATE_DOWNLOADING);
                            downloadIntet.putExtra(TaskState.DOWNLOAD_URL, info.getAppUrl());
                            downloadIntet.putExtra("action", DownloadTask.ACTION_DOWNLOAD);
                            context.startService(downloadIntet);

                            // 将下载任务放入数据库备用
                            AppDao appDao = new AppDao(context);
                            appDao.addApp(info);
                        }
                    } else if(info.getState() == TaskState.STATE_DOWNLOADING) {
                        // 暂停
                        info.setState(TaskState.STATE_PAUSED);
                        holder.setButtonState(context, info);
                        // 通知service暂停下载
                        downloadIntet.putExtra(TaskState.DOWNLOAD_STATE, TaskState.STATE_PAUSED);
                        downloadIntet.putExtra(TaskState.DOWNLOAD_URL, info.getAppUrl());
                        downloadIntet.putExtra("action", DownloadTask.ACTION_DOWNLOAD);
                        context.startService(downloadIntet);
                    } else if(info.getState() == TaskState.STATE_PAUSED) {
                        // 继续下载
                        info.setState(TaskState.STATE_DOWNLOADING);
                        holder.setButtonState(context, info);
                        // 通知service继续下载
                        downloadIntet.putExtra(TaskState.DOWNLOAD_STATE, TaskState.STATE_CONTINUE);
                        downloadIntet.putExtra(TaskState.DOWNLOAD_URL, info.getAppUrl());
                        downloadIntet.putExtra("action", DownloadTask.ACTION_DOWNLOAD);
                        context.startService(downloadIntet);
                    }
                    break;
                case R.id.image_app:
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

    /**
     * 通过url找info
     * @param url
     * @return
     */
    public AppInfo getAppInfoByUrl(String url) {

        String tmp;
        for (int i = 0; i < data.size(); i++) {
            tmp = data.get(i).getAppUrl();
            if (tmp.equals(url)) {
                return data.get(i);
            }
        }

        return null;

    }
}
