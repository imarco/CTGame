package com.hoyotech.ctgames.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.util.CTGameImageLoader;
import com.hoyotech.ctgames.util.StorageUtils;
import com.hoyotech.ctgames.util.TaskState;

/**
 * Created by GGCoke on 13-12-14.
 */
public class TaskCompleteHolder {
    public ImageView appImageHeader; //应用图标
    public TextView appName;         // 应用名
    public TextView appVersion;     // 应用版本
    public TextView appPackageSize;  // 应用安装包大小
    public Button btnOptions;        // 应用的状态（安装或者打开获取奖励等）
    public TextView tvPrizeCount;    // 应用的抽奖次数
    public TextView tvLuckyBeanCount; // 幸运豆个数
    public TextView tvSummary; // 应用的简介

    public AppInfo info;

    /**
     * 从界面中获取view构件holder
     * @param convertView parentView
     */
    public TaskCompleteHolder(View convertView) {
        this.appImageHeader = (ImageView) convertView.findViewById(R.id.image_app);
        this.appName = (TextView) convertView.findViewById(R.id.tv_app_name);
        this.appVersion = (TextView) convertView.findViewById(R.id.tv_app_version);
        this.appPackageSize = (TextView) convertView.findViewById(R.id.tv_app_size);
        this.btnOptions = (Button) convertView.findViewById(R.id.btn_app_options);
        this.tvPrizeCount = (TextView) convertView.findViewById(R.id.tv_prize_count);
        this.tvLuckyBeanCount = (TextView) convertView.findViewById(R.id.tv_luckypean_count);
        this.tvSummary = (TextView) convertView.findViewById(R.id.tv_summary);
    }

    /**
     * 根据appinfo的信息设置viewholder中view的状态
     * @param appInfo app 的信息
     */
    public void setData(Context context, AppInfo appInfo) {
        CTGameImageLoader.loadImage(context, appInfo.getAppLogoUrl(), this.appImageHeader);
        this.appName.setText(appInfo.getAppName());
        this.appVersion.setText(appInfo.getVersion());
        this.appPackageSize.setText(StorageUtils.getSizeFormatted(appInfo.getAppSize()));
        this.btnOptions.setText(TaskState.getTaskStateMap().get(appInfo.getState()));
        this.tvPrizeCount.setText("下载完成已获"+appInfo.getLotteryNum()+"次获奖机会");
        this.tvLuckyBeanCount.setText(appInfo.getLuckyBeansNum()+"幸运豆奖励已获取");
        this.tvLuckyBeanCount.setTextColor(context.getResources().getColor(R.color.new_task_prize_info_grey));
        this.tvSummary.setText(appInfo.getAppDesc());
        TaskState.setButtonView(appInfo.getState(), context, btnOptions);
    }
}
