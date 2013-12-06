package com.hoyotech.ctgames.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.bean.AppInfo;
import com.hoyotech.ctgames.adapter.holder.AppInstallInfoHolder;
import com.hoyotech.ctgames.util.TaskState;


public class AppInstallAdapter extends BaseAdapter {

    //定义相应的变量
    private ArrayList<AppInfo> data;
    private Context context;
    private AppInfo info;

    //构造函数
    public AppInstallAdapter(ArrayList<AppInfo> data, Context context) {
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
        AppInstallInfoHolder holder = null;

        if (convertView == null){
            holder = new AppInstallInfoHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.app_install_item, null);
            holder.appImageHeader = (ImageView) convertView.findViewById(R.id.app_install_img);
            holder.appName = (TextView) convertView.findViewById(R.id.tv_app_name);
            holder.appPackageSize = (TextView) convertView.findViewById(R.id.tv_app_size);
            holder.btnOptions = (Button) convertView.findViewById(R.id.btn_options);
            holder.btnAppBonus = (Button) convertView.findViewById(R.id.btn_app_bonus);
            holder.tvPrizeCount = (TextView) convertView.findViewById(R.id.tv_prize_count);
            holder.tvLuckyBeanCount = (TextView) convertView.findViewById(R.id.tv_luckybean_count);
            holder.tvSummary = (TextView) convertView.findViewById(R.id.tv_summary);
            convertView.setTag(holder);
        } else {
            holder = (AppInstallInfoHolder) convertView.getTag();
        }

        info = data.get(position);
        holder.appImageHeader.setBackgroundDrawable(info.getImg());
        holder.appName.setText(info.getAppName());
        holder.appPackageSize.setText(String.valueOf(info.getAppSize())+"M");
        holder.btnOptions.setText(TaskState.getMap().get(info.getState()));
        holder.tvPrizeCount.setText(String.valueOf(info.getPrizeCount()));
        holder.tvLuckyBeanCount.setText(String.valueOf(info.getLuckybeanCount()));
        holder.tvSummary.setText(info.getSummary());

        //设置事件监听响应
        holder.btnOptions.setOnClickListener(new ButtonListener());
        holder.btnAppBonus.setOnClickListener(new ButtonListener());

        return convertView;
    }

    // view中按钮的点击事件处理
    private class ButtonListener implements View.OnClickListener {

        public ButtonListener() {
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_options:
                    break;
                case R.id.btn_app_bonus:
                    break;
                default:
                    break;
            }
        }
    }

}
