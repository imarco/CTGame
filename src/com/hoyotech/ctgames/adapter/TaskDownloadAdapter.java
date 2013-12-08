package com.hoyotech.ctgames.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.bean.AppInfo;
import com.hoyotech.ctgames.adapter.holder.TaskDownloadHolder;
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
                        holder.setData(context, info);
                        // 通知service开始下载

                    } else if(info.getState() == TaskState.STATE_PAUSE) {
                        info.setState(TaskState.STATE_CONTINUE);
                        holder.setData(context, info);
                        // 通知service暂停下载

                    } else if(info.getState() == TaskState.STATE_CONTINUE) {
                        info.setState(TaskState.STATE_PAUSE);
                        holder.setData(context, info);
                        // 通知service继续下载

                    }
                    break;
                default:
                    // 补充默认情况
                    break;
            }
        }

    }
}
