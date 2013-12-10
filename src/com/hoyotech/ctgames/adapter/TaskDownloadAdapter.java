package com.hoyotech.ctgames.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.bean.AppInfo;
import com.hoyotech.ctgames.adapter.holder.TaskDownloadHolder;
import com.hoyotech.ctgames.fragment.TaskDownloadFragment;
import com.hoyotech.ctgames.service.DownloadService;
import com.hoyotech.ctgames.util.TaskState;

import java.util.HashMap;
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
            tmp = data.get(i).getUrl();
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_task_download_item, null);
        }

        appInfo = data.get(position);
        convertView.setTag(appInfo.getUrl());// 便于通过url找view

        TaskDownloadHolder holder = new TaskDownloadHolder(convertView);
        holder.setData(convertView.getContext(), appInfo);
        holder.info = appInfo;

        //设置事件监听响应
        holder.btnOptions.setOnClickListener(new ButtonClickListener(appInfo.getUrl(), appInfo, holder));
        holder.btnAppBonus.setOnClickListener(new ButtonClickListener(appInfo.getUrl(), appInfo, holder));
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
                case R.id.btn_app_bonus:
                    // 补充响应
                    // 应该弹出应用详情信息
                    break;
                case R.id.btn_options:
                    // 补充响应
                    // 根据按钮的状态决定操作
                    // 点击下载-暂停 点击暂停-继续 点击继续-暂停
                    if(info.getState() == TaskState.STATE_DOWNLOAD) {
                        info.setState(TaskState.STATE_PAUSE);
                        holder.setButtonState(context, info);
                        // 通知service开始下载
                        downloadIntet.putExtra(TaskState.DOWNLOAD_STATE, TaskState.STATE_DOWNLOAD);
                        downloadIntet.putExtra(TaskState.DOWNLOAD_URL, info.getUrl());
                        downloadIntet.putExtra("action", TaskDownloadFragment.INTENT_FILTER_ACTION_NAME_TASK_DOWNLOAD);
                        System.out.println("点击按钮下载" + getClass().getName());
                        context.startService(downloadIntet);

                    } else if(info.getState() == TaskState.STATE_PAUSE) {
                        info.setState(TaskState.STATE_CONTINUE);
                        holder.setButtonState(context, info);
                        // 通知service暂停下载
                        downloadIntet.putExtra(TaskState.DOWNLOAD_STATE, TaskState.STATE_PAUSE);
                        downloadIntet.putExtra(TaskState.DOWNLOAD_URL, info.getUrl());
                        downloadIntet.putExtra("action", TaskDownloadFragment.INTENT_FILTER_ACTION_NAME_TASK_DOWNLOAD);
                        context.startService(downloadIntet);

                    } else if(info.getState() == TaskState.STATE_CONTINUE) {
                        info.setState(TaskState.STATE_PAUSE);
                        holder.setButtonState(context, info);
                        // 通知service继续下载
                        downloadIntet.putExtra(TaskState.DOWNLOAD_STATE, TaskState.STATE_CONTINUE);
                        downloadIntet.putExtra(TaskState.DOWNLOAD_URL, info.getUrl());
                        downloadIntet.putExtra("action", TaskDownloadFragment.INTENT_FILTER_ACTION_NAME_TASK_DOWNLOAD);
                        context.startService(downloadIntet);

                    }
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
            tmp = data.get(i).getUrl();
            if (tmp.equals(url)) {
                return data.get(i);
            }
        }

        return new AppInfo();

    }
}
