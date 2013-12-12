package com.hoyotech.ctgames.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.util.CTGameImageLoader;
import com.hoyotech.ctgames.util.TaskState;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-8
 * Time: 下午7:21
 * To change this template use File | Settings | File Templates.
 */
public class TaskInstallHolder {

    public ImageView appImageHeader; //应用图标
    public TextView appName;         // 应用名
    public TextView appPackageSize;  // 应用安装包大小
    public Button btnOptions;        // 应用的状态（安装或者打开获取奖励等）
    public Button btnAppBonus;       // 应用的所有奖励按钮
    public TextView tvPrizeCount;    // 应用的抽奖次数
    public TextView tvLuckyBeanCount; // 幸运豆个数
    public TextView tvSummary; // 应用的简介

    public AppInfo info;

    /**
     * 从界面中获取view构件holder
     * @param convertView parentView
     */
    public TaskInstallHolder(View convertView) {
        this.appImageHeader = (ImageView) convertView.findViewById(R.id.image_app);
        this.appName = (TextView) convertView.findViewById(R.id.tv_app_name);
        this.appPackageSize = (TextView) convertView.findViewById(R.id.tv_app_size);
        this.btnOptions = (Button) convertView.findViewById(R.id.btn_app_options);
        this.btnAppBonus = (Button) convertView.findViewById(R.id.btn_app_bonus);
        this.tvPrizeCount = (TextView) convertView.findViewById(R.id.tv_prize_count);
        this.tvLuckyBeanCount = (TextView) convertView.findViewById(R.id.tv_luckybean_count);
        this.tvSummary = (TextView) convertView.findViewById(R.id.tv_summary);
    }

    /**
     * 根据appinfo的信息设置viewholder中view的状态
     * @param appInfo app 的信息
     */
    public void setData(Context context, AppInfo appInfo) {
        CTGameImageLoader.loadImage(context, appInfo.getAppLogoUrl(), this.appImageHeader);
        this.appName.setText(appInfo.getAppName());
        this.appPackageSize.setText(String.valueOf(appInfo.getAppSize())+"M");
        this.btnOptions.setText(TaskState.getTaskStateMap().get(appInfo.getState()));
        this.tvPrizeCount.setText(String.valueOf(appInfo.getLotteryNum()));
        this.tvLuckyBeanCount.setText(String.valueOf(appInfo.getLuckyBeansNum()));
        this.tvSummary.setText(appInfo.getAppDesc());
        TaskState.setButtonView(appInfo.getState(), context, btnOptions);
    }

}
