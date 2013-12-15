package com.hoyotech.ctgames.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.activity.AppDetailActivity;
import com.hoyotech.ctgames.adapter.holder.TaskCompleteHolder;
import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.util.CTGameConstans;
import com.hoyotech.ctgames.util.TaskState;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by GGCoke on 13-12-14.
 */
public class TaskCompleteAdapter extends BaseAdapter {
    private List<AppInfo> data;
    private Context context;
    private AppInfo appInfo;

    public TaskCompleteAdapter(Context context, List<AppInfo> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return this.data.size();
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        TaskCompleteHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_task_complete_item, null);
            holder = new TaskCompleteHolder(view);
            view.setTag(holder);
        } else {
            holder = (TaskCompleteHolder) view.getTag();
        }

        appInfo = data.get(position);
        holder.setData(context, appInfo);
        holder.info = appInfo;

        holder.btnOptions.setOnClickListener(new ButtonClickListener(appInfo.getAppUrl(), appInfo, holder));
        holder.appImageHeader.setOnClickListener(new ButtonClickListener(appInfo.getAppUrl(), appInfo, holder));
        return view;
    }

    private class ButtonClickListener implements View.OnClickListener{
        private String mUrl;
        private TaskCompleteHolder mHolder;
        private AppInfo mAppInfo;

        public ButtonClickListener(String url, AppInfo info, TaskCompleteHolder holder) {
            this.mUrl = url;
            this.mAppInfo = info;
            this.mHolder = holder;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_app_options:
                    switch (mAppInfo.getState()) {
                        case TaskState.STATE_TASK_COMPLETE:
                            // 任务已完成，可以打开应用
                            try {
                                String fileName = new File(new URL(mAppInfo.getAppUrl()).getFile()).getName();
                                String apkName = CTGameConstans.CTGAME_APP_DOWNLOAD_DIR + fileName;
                                PackageManager pm = context.getPackageManager();
                                PackageInfo packageInfo = pm.getPackageArchiveInfo(apkName, PackageManager.GET_ACTIVITIES);
                                Intent i = pm.getLaunchIntentForPackage(packageInfo.packageName);
                                context.startActivity(i);
                            } catch (MalformedURLException e) {
                                Toast.makeText(context, "打开应用失败", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        default:
                            Toast.makeText(context, "错误的应用信息状态：" + mAppInfo.getState(), Toast.LENGTH_SHORT).show();
                            break;
                    }
                    break;
                case R.id.image_app:
                    // 响应app的图标点击的事件
                    Intent intent = new Intent(context, AppDetailActivity.class);
                    Bundle data = new Bundle();
                    data.putSerializable("appInfo", mAppInfo);
                    intent.putExtras(data);
                    context.startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }
}
