package com.hoyotech.ctgames.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.util.CTGameImageLoader;
import com.hoyotech.ctgames.util.StorageUtils;
import com.hoyotech.ctgames.util.TaskState;

/**
 * Created by GGCoke on 13-12-6.
 */
public class AppInfoHolder {
    public ImageView appImageHeader; //应用图标
    public TextView appName;         // 应用名
    public TextView appPackageSize;  // 应用安装包大小
    public TextView appVersion;      // 应用版本号
    public TextView tvPrizeCount;    // 应用的抽奖次数
    public TextView tvLuckyBeanCount; // 幸运豆个数
    public TextView tvDownload;      // 下载或再次下载的标识
    public TextView tvInstall;       // 安装或再次安装的标识

    public LinearLayout layoutDownloadEnabled;
    public LinearLayout layoutDownloadDisabled;
    public LinearLayout layoutInstallEnabled;
    public LinearLayout layoutInstallDisabled;


    public AppInfo info;

    /**
     * 从界面中获取view构件holder
     * @param convertView parentView
     */
    public AppInfoHolder(View convertView) {
        this.appImageHeader = (ImageView) convertView.findViewById(R.id.app_img_head);
        this.appName = (TextView) convertView.findViewById(R.id.app_name);
        this.appVersion = (TextView) convertView.findViewById(R.id.app_version);
        this.appPackageSize = (TextView) convertView.findViewById(R.id.app_package_size);
        this.tvPrizeCount = (TextView) convertView.findViewById(R.id.tv_prize_count);
        this.tvLuckyBeanCount = (TextView) convertView.findViewById(R.id.tv_luckybean_count);

        this.layoutDownloadEnabled = (LinearLayout) convertView.findViewById(R.id.layout_download_button_enabled);
        this.layoutDownloadDisabled = (LinearLayout) convertView.findViewById(R.id.layout_download_button_disabled);
        this.layoutInstallEnabled = (LinearLayout) convertView.findViewById(R.id.layout_install_button_enabled);
        this.layoutInstallDisabled = (LinearLayout) convertView.findViewById(R.id.layout_install_button_disabled);

        this.tvDownload = (TextView) convertView.findViewById(R.id.tv_download);
        this.tvInstall = (TextView) convertView.findViewById(R.id.tv_install);
    }

    /**
     * 根据appinfo的信息设置viewholder中view的状态
     * @param appInfo app 的信息
     */
    public void setData(Context context, AppInfo appInfo) {
        CTGameImageLoader.loadImage(context, appInfo.getAppLogoUrl(), this.appImageHeader);
        this.appName.setText(appInfo.getAppName());
        this.appPackageSize.setText(StorageUtils.getSizeFormatted(appInfo.getAppSize()));
        this.appVersion.setText(String.valueOf(appInfo.getVersion()));
        setPrizeCount(appInfo.getLotteryNum());
        setLuckyBeanCount(appInfo.getLuckyBeansNum());

    }

    public void setPrizeCount(int prizeCount) {
        this.tvPrizeCount.setText("(获" + prizeCount + "次机会)");
    }

    public void setLuckyBeanCount(int luckyBeanCount) {
        this.tvLuckyBeanCount.setText("(获" + luckyBeanCount + "幸运豆)");
    }

    /**
     * 设置下载按钮是否有效
     * @param enabled 是否有效
     */
    public void setDownloadButtonEnabledOrNot(boolean enabled) {
        if(enabled) {
            layoutDownloadEnabled.setVisibility(View.VISIBLE);
            layoutDownloadDisabled.setAlpha(0f);
        } else {
            layoutDownloadEnabled.setVisibility(View.GONE);
            layoutDownloadDisabled.setAlpha(1f);
        }
    }

    /**
     * 设置安装按钮是否有效
     * @param enabled 是否有效
     */
    public void setInstallButtonEnabledOrNot(boolean enabled) {
        if(enabled) {
            layoutInstallEnabled.setVisibility(View.VISIBLE);
            layoutInstallDisabled.setAlpha(0f);
        } else {
            layoutInstallEnabled.setVisibility(View.GONE);
            layoutInstallDisabled.setAlpha(1f);
        }
    }

}

