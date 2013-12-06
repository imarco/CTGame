package com.hoyotech.ctgames.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.bean.AppPackageInfo;
import com.hoyotech.ctgames.adapter.holder.AppPackageInfoHolder;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-6
 * Time: 下午5:09
 * To change this template use File | Settings | File Templates.
 */
public class PackageInfoAdapter extends BaseAdapter {

    //定义相应的变量
    private ArrayList<AppPackageInfo> data;
    private Context context;
    private AppPackageInfo info;

    //构造函数
    public PackageInfoAdapter(ArrayList<AppPackageInfo> data, Context context) {
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
        AppPackageInfoHolder holder = null;

        if (convertView == null){
            holder = new AppPackageInfoHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_package_info_item, null);
            holder.image = (ImageView) convertView.findViewById(R.id.image_app_package);
            holder.tvPackageName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvPackageSize = (TextView) convertView.findViewById(R.id.package_size);
            holder.btnOpen = (Button) convertView.findViewById(R.id.btn_open);
            holder.tvPrizeCount = (TextView) convertView.findViewById(R.id.btn_play_chance);
            holder.tvLuckyBeanCount = (TextView) convertView.findViewById(R.id.tv_bonus);
            holder.tvSummary = (TextView) convertView.findViewById(R.id.tv_summary);
            convertView.setTag(holder);
        } else {
            holder = (AppPackageInfoHolder) convertView.getTag();
        }

        info = data.get(position);
        holder.image.setBackgroundDrawable(info.getImg());
        holder.tvPackageName.setText(info.getName());
        holder.tvPackageSize.setText(String.valueOf(info.getSize())+"M");
        holder.tvPrizeCount.setText(String.valueOf(info.getPrizeCount()));
        holder.tvLuckyBeanCount.setText(String.valueOf(info.getLuckybeanCount()));
        holder.tvSummary.setText(info.getSummary());

        //设置事件监听响应
        holder.btnOpen.setOnClickListener(new ButtonListener());

        return convertView;
    }

    // view中按钮的点击事件处理
    private class ButtonListener implements View.OnClickListener {

        public ButtonListener() {
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_open:
                    break;
            }
        }
    }
}
