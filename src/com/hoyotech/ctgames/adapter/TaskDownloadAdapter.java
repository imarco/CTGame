package com.hoyotech.ctgames.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.bean.AppInfo;
import com.hoyotech.ctgames.adapter.holder.TaskDownloadHolder;

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
    AppInfo info;

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
            holder = new TaskDownloadHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_task_download_item, null);
            holder.appImageHeader = (ImageView) convertView.findViewById(R.id.image_app);
            holder.appName = (TextView) convertView.findViewById(R.id.tv_app_name);
            holder.appPackageSize = (TextView) convertView.findViewById(R.id.tv_app_size);
            holder.btnOptions = (Button) convertView.findViewById(R.id.btn_options);
            holder.btnAppBonus = (Button) convertView.findViewById(R.id.btn_app_bonus);
            holder.tvPrizeCount = (TextView) convertView.findViewById(R.id.tv_prize_count);
            holder.tvLuckyBeanCount = (TextView) convertView.findViewById(R.id.tv_luckypean_count);
            holder.tvDownloadRate = (TextView) convertView.findViewById(R.id.tv_download_rate);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progress_bar);
            convertView.setTag(holder);
        }else {
            holder = (TaskDownloadHolder) convertView.getTag();
        }
        info = data.get(position);
        holder.appImageHeader.setBackgroundDrawable(info.getImg());
        holder.appName.setText(info.getAppName());
        holder.appPackageSize.setText(String.valueOf(info.getAppSize())+"M");
        holder.tvPrizeCount.setText(String.valueOf(info.getPrizeCount()));
        holder.tvLuckyBeanCount.setText(String.valueOf(info.getLuckybeanCount()));
        holder.tvDownloadRate.setText(String.valueOf(info.getRate())+"KB/S");
        holder.progressBar.setProgress(info.getProgress());

        //设置事件监听响应
        holder.btnOptions.setOnClickListener(new ButtonClickListener());
        holder.btnAppBonus.setOnClickListener(new ButtonClickListener());
        return convertView;
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
