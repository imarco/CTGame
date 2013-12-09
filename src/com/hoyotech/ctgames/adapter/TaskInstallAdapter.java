package com.hoyotech.ctgames.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.bean.AppInfo;
import com.hoyotech.ctgames.adapter.holder.TaskDownloadHolder;
import com.hoyotech.ctgames.adapter.holder.TaskInstallHolder;
import com.hoyotech.ctgames.util.TaskState;


public class TaskInstallAdapter extends BaseAdapter {

    //定义相应的变量
    private List<AppInfo> data;
    private Context context;
    private AppInfo appInfo;

    //构造函数
    public TaskInstallAdapter(List<AppInfo> data, Context context) {
        this.data = data;
        this.context = context;
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

        //设置事件监听响应
        holder.btnOptions.setOnClickListener(new ButtonClickListener(appInfo.getUrl(), appInfo, holder));
        holder.btnAppBonus.setOnClickListener(new ButtonClickListener(appInfo.getUrl(), appInfo, holder));

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

            switch (v.getId()) {
                case R.id.btn_app_bonus:
                    // 补充响应
                    // 应该弹出应用详情信息
                    break;
                case R.id.btn_app_options:
                    // 补充响应
                    // 根据按钮的状态决定操作
                    // 点击安装-安装中 打开-领取奖励 (中间状态 安装完成之后变成打开)
                    // 点击领取奖励再进行跳转
                    if(info.getState() == TaskState.STATE_INSTALL) {
                        info.setState(TaskState.STATE_INSTALLING);
                        holder.setData(context, info);
                        // 当安装完成之后，在这个时候需要更新状态表示打开
                        // 通知service安装

                    } else if(info.getState() == TaskState.STATE_OPEN) {
                        info.setState(TaskState.STATE_GET_PRIZE);
                        holder.setData(context, info);
                        // 通知service打开安装好的应用

                    } else if(info.getState() == TaskState.STATE_GET_PRIZE) {
                        // 点击获取奖励之后的动作
                    }
                    break;
                default:
                    // 补充默认情况
                    break;
            }
        }

    }

}
