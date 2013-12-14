package com.hoyotech.ctgames.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.db.dao.AppDao;
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

    public TextView tvPrizeCount1;    // 应用的抽奖次数
    public TextView tvLuckyBeanCount1; // 幸运豆个数
    public TextView tvDownload1; // 幸运豆个数
    public TextView tvInstall1; // 幸运豆个数


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
        this.tvLuckyBeanCount = (TextView) convertView.findViewById(R.id.tv_luckypean_count);

        this.layoutDownloadEnabled = (LinearLayout) convertView.findViewById(R.id.layout_download_button_enabled);
        this.layoutDownloadDisabled = (LinearLayout) convertView.findViewById(R.id.layout_download_button_disabled);
        this.layoutInstallEnabled = (LinearLayout) convertView.findViewById(R.id.layout_install_button_enabled);
        this.layoutInstallDisabled = (LinearLayout) convertView.findViewById(R.id.layout_install_button_disabled);

        this.tvPrizeCount1 = (TextView) convertView.findViewById(R.id.tv_prize_count1);
        this.tvLuckyBeanCount1 = (TextView) convertView.findViewById(R.id.tv_luckypean_count1);

        this.tvDownload = (TextView) convertView.findViewById(R.id.tv_download);
        this.tvInstall = (TextView) convertView.findViewById(R.id.tv_install);

        this.tvDownload1 = (TextView) convertView.findViewById(R.id.tv_download1);
        this.tvInstall1 = (TextView) convertView.findViewById(R.id.tv_install1);
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

        AppDao dao = new AppDao(context);
        AppInfo info = dao.queryAppByUrl(appInfo.getAppUrl());

        if(info != null) {

            // 下载中或者暂停时，下载按钮灰掉
            if(info.getState() == TaskState.STATE_DOWNLOADING || info.getState() == TaskState.STATE_PAUSED) {
                setDownloadButtonEnabledOrNot(false);
                setInstallButtonEnabledOrNot(false);
            }

            // 下载完成一个状态
            if(info.getState() == TaskState.STATE_COMPLETE) {
                setDownloadButtonEnabledOrNot(true);
                setInstallButtonEnabledOrNot(true);
            }

            // 完成安装一个状态
            if(info.getState() == TaskState.STATE_INSTALLED) {
                setDownloadButtonEnabledOrNot(true);
                setInstallButtonEnabledOrNot(false);
            }

            // 任务完成一个状态
            if(info.getState() == TaskState.STATE_TASK_COMPLETE) {
                setDownloadButtonEnabledOrNot(true);
                setInstallButtonEnabledOrNot(false);
                this.tvLuckyBeanCount.setText("(已获取幸运豆)");
                this.tvLuckyBeanCount1.setText("(已获取幸运豆)");
            }

            // 如果曾经下载过，就要改文字
            setDownloadAgainOrNot(info.isHasDownloaded());

        } else {
            setDownloadButtonEnabledOrNot(true);
            setInstallButtonEnabledOrNot(false);
            setDownloadAgainOrNot(false);
        }

    }

    public void setDownloadAgainOrNot(boolean isAgain) {
        String text = (isAgain) ? "再次下载" : "下载";
        tvDownload.setText(text);
        tvDownload1.setText(text);
    }

    public void setPrizeCount(int prizeCount) {
        this.tvPrizeCount.setText("(获" + prizeCount + "次机会)");
        this.tvPrizeCount1.setText("(获" + prizeCount + "次机会)");
    }

    public void setLuckyBeanCount(int luckyBeanCount) {
        this.tvLuckyBeanCount.setText("(获" + luckyBeanCount + "幸运豆)");
        this.tvLuckyBeanCount1.setText("(获" + luckyBeanCount + "幸运豆)");
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

