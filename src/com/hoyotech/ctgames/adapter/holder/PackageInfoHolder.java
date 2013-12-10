package com.hoyotech.ctgames.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.bean.AppInfo;
import com.hoyotech.ctgames.adapter.bean.PackageInfo;
import com.hoyotech.ctgames.util.TaskState;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-6
 * Time: 下午5:00
 * To change this template use File | Settings | File Templates.
 */
public class PackageInfoHolder {

    public ImageView image;              //应用图标
    public TextView tvPackageName;         // 应用名
    public TextView tvPackageSize;         // 应用安装包大小
    public Button btnOpen;               // 应用的状态（安装或者打开）
    public TextView tvPrizeCount;        // 应用的抽奖次数
    public TextView tvLuckyBeanCount;    // 幸运豆个数

    //应用的简介部分
    public TextView tvSummary; // 应用的简介

    public PackageInfo info;

    /**
     * 从界面中获取view构件holder
     * @param convertView parentView
     */
    public PackageInfoHolder(View convertView) {
        this.image = (ImageView) convertView.findViewById(R.id.image_app_package);
        this.tvPackageName = (TextView) convertView.findViewById(R.id.tv_name);
        this.tvPackageSize = (TextView) convertView.findViewById(R.id.package_size);
        this.btnOpen = (Button) convertView.findViewById(R.id.btn_open);
        this.tvPrizeCount = (TextView) convertView.findViewById(R.id.btn_play_chance);
        this.tvLuckyBeanCount = (TextView) convertView.findViewById(R.id.tv_bonus);
        this.tvSummary = (TextView) convertView.findViewById(R.id.tv_summary);
    }

    /**
     * 根据appinfo的信息设置viewholder中view的状态
     * @param packageInfo package的信息
     */
    public void setData(Context context, PackageInfo packageInfo) {

        this.image.setBackgroundDrawable(packageInfo.getImg());
        this.tvPackageName.setText(packageInfo.getName());
        this.tvPackageSize.setText(String.valueOf(packageInfo.getSize()) + "M");
        this.tvPrizeCount.setText(String.valueOf(packageInfo.getPrizeCount()));
        this.tvLuckyBeanCount.setText(String.valueOf(packageInfo.getLuckybeanCount()));
        this.tvSummary.setText(packageInfo.getSummary());

    }

}
