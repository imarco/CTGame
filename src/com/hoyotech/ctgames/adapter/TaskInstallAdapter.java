package com.hoyotech.ctgames.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.adapter.holder.TaskInstallHolder;

import java.util.List;


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
        holder.btnAppBonus.setOnClickListener(new ButtonClickListener(appInfo.getAppUrl(), appInfo, holder));

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
                    // TODO button点击事件
                    break;
                default:
                    // 补充默认情况
                    break;
            }
        }

    }

}
