package com.hoyotech.ctgames.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.db.bean.PackageInfo;
import com.hoyotech.ctgames.util.CTGameImageLoader;
import com.hoyotech.ctgames.util.StorageUtils;

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
    public TextView tvPrizeCount;        // 应用的抽奖次数
    public TextView tvLuckyBeanCount;    // 幸运豆个数


    public PackageInfo info;

    /**
     * 从界面中获取view构件holder
     * @param convertView parentView
     */
    public PackageInfoHolder(View convertView) {
        this.image = (ImageView) convertView.findViewById(R.id.image_app_package);
        this.tvPackageName = (TextView) convertView.findViewById(R.id.tv_name);
        this.tvPackageSize = (TextView) convertView.findViewById(R.id.package_size);
        this.tvPrizeCount = (TextView) convertView.findViewById(R.id.tv_prize_count);
        this.tvLuckyBeanCount = (TextView) convertView.findViewById(R.id.tv_luckypean_count);
    }

    /**
     * 根据appinfo的信息设置viewholder中view的状态
     * @param packageInfo package的信息
     */
    public void setData(Context context, PackageInfo packageInfo) {

        CTGameImageLoader.loadImage(context, packageInfo.getUrl(), image);
        this.tvPackageName.setText(packageInfo.getName());
        this.tvPackageSize.setText(StorageUtils.getSizeFormatted(packageInfo.getSize()));
        this.tvPrizeCount.setText(String.valueOf(packageInfo.getPrizeCount()));
        this.tvLuckyBeanCount.setText(String.valueOf(packageInfo.getLuckybeanCount()));

    }

}
