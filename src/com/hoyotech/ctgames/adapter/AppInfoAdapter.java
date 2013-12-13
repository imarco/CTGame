package com.hoyotech.ctgames.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.adapter.holder.AppInfoHolder;
import com.hoyotech.ctgames.db.dao.AppDao;
import com.hoyotech.ctgames.service.DownloadService;
import com.hoyotech.ctgames.service.DownloadTask;
import com.hoyotech.ctgames.util.TaskState;

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
        holder.btnOptions.setOnClickListener(new ButtonClickListener(appInfo.getAppUrl(), appInfo, holder));
        holder.btnAppBonus.setOnClickListener(new ButtonClickListener(appInfo.getAppUrl(), appInfo, holder));

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
                case R.id.btn_options:
                    // 补充响应
                    // 根据按钮的状态决定操作
                    // 点击下载-暂停 点击暂停-继续 点击继续-暂停
                    if(info.getState() == TaskState.STATE_PREPARE) {
                        info.setState(TaskState.STATE_DOWNLOADING);
                        holder.setButtonState(context, info);
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
                        holder.setButtonState(context, info);
                        // 通知service暂停下载
                        downloadIntet.putExtra(TaskState.DOWNLOAD_STATE, TaskState.STATE_PAUSED);
                        downloadIntet.putExtra(TaskState.DOWNLOAD_URL, info.getAppUrl());
                        downloadIntet.putExtra("action", DownloadTask.ACTION_DOWNLOAD);
                        context.startService(downloadIntet);
                    } else if(info.getState() == TaskState.STATE_PAUSED) {
                        info.setState(TaskState.STATE_DOWNLOADING);
                        holder.setButtonState(context, info);
                        // 通知service继续下载
                        downloadIntet.putExtra(TaskState.DOWNLOAD_STATE, TaskState.STATE_CONTINUE);
                        downloadIntet.putExtra(TaskState.DOWNLOAD_URL, info.getAppUrl());
                        downloadIntet.putExtra("action", DownloadTask.ACTION_DOWNLOAD);
                        context.startService(downloadIntet);
                    }
                    break;
                default:
                    // 补充默认情况
                    break;
            }
        }
    }
}
