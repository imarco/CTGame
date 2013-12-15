package com.hoyotech.ctgames.adapter;

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
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.activity.AppDetailActivity;
import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.adapter.holder.TaskInstallHolder;
import com.hoyotech.ctgames.db.dao.AppDao;
import com.hoyotech.ctgames.fragment.TaskInstallFragment;
import com.hoyotech.ctgames.util.CTGameConstans;
import com.hoyotech.ctgames.util.Constant;
import com.hoyotech.ctgames.util.GetDataCallback;
import com.hoyotech.ctgames.util.GetDataTask;
import com.hoyotech.ctgames.util.StorageUtils;
import com.hoyotech.ctgames.util.TaskState;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class TaskInstallAdapter extends BaseAdapter {

    //定义相应的变量
    private List<AppInfo> data;
    private Context context;
    private AppInfo appInfo;

    //构造函数
    public TaskInstallAdapter(Context context, List<AppInfo> data) {
        this.context = context;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        TaskInstallHolder holder = null;

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_task_install_item, null);
            holder = new TaskInstallHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (TaskInstallHolder) convertView.getTag();
        }

        appInfo = data.get(position);
        holder.setData(context, appInfo);
        holder.info = appInfo;

        //设置事件监听响应
        holder.btnOptions.setOnClickListener(new ButtonClickListener(appInfo.getAppUrl(), appInfo, holder));
        holder.appImageHeader.setOnClickListener(new ButtonClickListener(appInfo.getAppUrl(), appInfo, holder));
        return convertView;
    }

    // 处理按钮的点击事件
    private class ButtonClickListener implements View.OnClickListener {
        private String url;
        private TaskInstallHolder holder;
        private AppInfo info;

        public ButtonClickListener(String url, AppInfo info, TaskInstallHolder holder) {
            this.url = url;
            this.info = info;
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            JSONObject request = new JSONObject();
            JSONObject d = new JSONObject();
            switch (v.getId()) {
                case R.id.btn_app_options:
                    // TODO button点击事件
                    switch (info.getState()) {
                        case TaskState.STATE_COMPLETE:
                            // 下载完成，点击后文字改变并安装应用
                            info.setState(TaskState.STATE_INSTALLING);
                            holder.setData(context, info);
                            notifyDataSetChanged();

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
                            } catch (MalformedURLException e) {
                            }
                            break;
                        case TaskState.STATE_INSTALLING:
                            // 不应该进入这个分支，安装中时button为disable状态
                            Toast.makeText(context, "错误的点击事件", Toast.LENGTH_SHORT).show();
                            break;
                        case TaskState.STATE_INSTALLED:
                            // 安装完成，点击打开应用
                            info.setState(TaskState.STATE_OPENED);
                            holder.setData(context, info);
                            notifyDataSetChanged();

                            // 数据库更新应用状态
                            AppDao dao = new AppDao(context);
                            ContentValues cv = new ContentValues();
                            cv.put(AppInfo.APPINFO_STATE, TaskState.STATE_OPENED);
                            dao.updateApp(cv, AppInfo.APPINFO_APPURL + "=?", new String[] {info.getAppUrl()});

                            // TODO 向服务器发送打开应用请求，由服务器判断是否是第一次打开
                            d.put("id", info.getAppId());
                            d.put("download", Constant.REQUEST_APP_TYPE_OPEN);
                            d.put("downloadSize", 0);
                            request.put("type", CTGameConstans.REQUEST_TYPE_DOWNLOADAPP);
                            request.put("sessionId", StorageUtils.getSessionID());
                            request.put("versionId", CTGameConstans.VERSION);
                            request.put("phone", StorageUtils.getUserPhoneNumber());
                            request.put("data", d);
                            new GetDataTask(new GetDataCallback() {
                                @Override
                                public void AddData(String data, int flag) {
                                }

                                @Override
                                public Handler GetHandle() {
                                    return null;
                                }
                            }, Constant.DOWNLOADAPP).execute(request.toJSONString());

                            // 打开应用
                            try {
                                String fileName = new File(new URL(info.getAppUrl()).getFile()).getName();
                                String apkName = CTGameConstans.CTGAME_APP_DOWNLOAD_DIR + fileName;
                                PackageManager pm = context.getPackageManager();
                                PackageInfo packageInfo = pm.getPackageArchiveInfo(apkName, PackageManager.GET_ACTIVITIES);
                                Intent i = pm.getLaunchIntentForPackage(packageInfo.packageName);
                                context.startActivity(i);
                            } catch (MalformedURLException e) {
                                Toast.makeText(context, "打开应用失败", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case TaskState.STATE_OPENED:
                            // 应用打开过，点击领取奖励
                            // 数据库更新应用状态
                            AppDao ad = new AppDao(context);
                            ContentValues cv2 = new ContentValues();
                            cv2.put(AppInfo.APPINFO_STATE, TaskState.STATE_TASK_COMPLETE);
                            ad.updateApp(cv2, AppInfo.APPINFO_APPURL + "=?", new String[] {info.getAppUrl()});

                            // TODO 向服务器发送领取奖励请求
                            d.put("id", info.getAppId());
                            d.put("download", Constant.REQUEST_APP_TYPE_GET_PRIZE);
                            d.put("downloadSize", 0);
                            request.put("type", CTGameConstans.REQUEST_TYPE_DOWNLOADAPP);
                            request.put("sessionId", StorageUtils.getSessionID());
                            request.put("versionId", CTGameConstans.VERSION);
                            request.put("phone", StorageUtils.getUserPhoneNumber());
                            request.put("data", d);
                            new GetDataTask(new GetDataCallback() {
                                @Override
                                public void AddData(String data, int flag) {
                                }

                                @Override
                                public Handler GetHandle() {
                                    return null;
                                }
                            }, Constant.DOWNLOADAPP).execute(request.toJSONString());

                            // 从任务列表中删除
                            data.remove(info);
                            notifyDataSetChanged();
                            Intent updateIntent = new Intent(TaskInstallFragment.INTENT_FILTER_ACTION_NAME_TASK_COMPLETE);
                            context.sendBroadcast(updateIntent);
                            break;
                        default:
                            Toast.makeText(context, "错误的应用状态：" + info.getState(), Toast.LENGTH_SHORT).show();
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

}
