package com.hoyotech.ctgames.adapter;

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
import com.hoyotech.ctgames.adapter.holder.AppInfoHolder;
import com.hoyotech.ctgames.util.TaskState;

import java.util.List;

/**
 * Created by GGCoke on 13-12-6.
 */
public class AppInfoAdapter extends BaseAdapter {
    private List<AppInfo> data;
    private Context context;
    private AppInfo info;

    //构造函数
    public AppInfoAdapter(List<AppInfo> data, Context context) {
        this.data = data;
        this.context = context;
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
        AppInfoHolder holder = null;
        if (convertView == null){
            holder = new AppInfoHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_simple_info_item, null);
            holder.appImageHeader = (ImageView) convertView.findViewById(R.id.app_img_head);
            holder.appName = (TextView) convertView.findViewById(R.id.app_name);
            holder.appPackageSize = (TextView) convertView.findViewById(R.id.app_package_size);
            holder.btnOptions = (Button) convertView.findViewById(R.id.btn_options);
            holder.tvPrizeCount = (TextView) convertView.findViewById(R.id.tv_prize_count);
            holder.tvLuckyBeanCount = (TextView) convertView.findViewById(R.id.tv_luckybean_count);
            holder.btnAppBonus = (Button) convertView.findViewById(R.id.btn_app_bonus);
            convertView.setTag(holder);
        }else {
            holder = (AppInfoHolder) convertView.getTag();
        }
        info = data.get(position);

        // 设置各种文字图片信息
        holder.appImageHeader.setBackgroundDrawable(info.getImg());
        holder.appName.setText(info.getAppName());
        holder.appPackageSize.setText(String.valueOf(info.getAppSize()));
        holder.btnOptions.setText(TaskState.getTaskStateMap().get(info.getState()));
        holder.tvPrizeCount.setText(String.valueOf(info.getPrizeCount()));
        holder.tvLuckyBeanCount.setText(String.valueOf(info.getLuckybeanCount()));

        //设置事件监听响应
        holder.btnOptions.setOnClickListener(new ButtonClickListener());
        holder.btnAppBonus.setOnClickListener(new ButtonClickListener());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ButtonClickListener implements View.OnClickListener {

        public ButtonClickListener() {
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_app_bonus:
                    // 补充响应
                    break;
                case R.id.btn_options:
                    // 补充响应
                    break;
                default:
                    // 补充默认情况
                    break;
            }
        }

    }
}
