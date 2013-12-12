package com.hoyotech.ctgames.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.activity.PackageDetailActivity;
import com.hoyotech.ctgames.db.bean.PackageInfo;
import com.hoyotech.ctgames.adapter.holder.PackageInfoHolder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-6
 * Time: 下午5:09
 * To change this template use File | Settings | File Templates.
 */
public class PackageInfoAdapter extends BaseAdapter {

    //定义相应的变量
    private List<PackageInfo> data;
    private Context context;
    private PackageInfo packageInfo;

    //构造函数
    public PackageInfoAdapter(List<PackageInfo> data, Context context) {
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
        PackageInfoHolder holder = null;

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_package_info_item, null);
            holder = new PackageInfoHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PackageInfoHolder) convertView.getTag();
        }

        packageInfo = data.get(position);
        holder.setData(context, packageInfo);
        holder.info = packageInfo;

        //设置事件监听响应
        holder.btnOpen.setOnClickListener(new ButtonClickListener(packageInfo, holder));

        return convertView;
    }

    // 处理按钮的点击事件
    private class ButtonClickListener implements View.OnClickListener {
        private PackageInfoHolder holder;
        private PackageInfo info;

        public ButtonClickListener(PackageInfo info, PackageInfoHolder holder) {
            this.info = info;
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_open:
                    // 打开礼包的详情，打开PackageDetailActivity
                    Intent intent = new Intent(context, PackageDetailActivity.class);
                    context.startActivity(intent);
                    break;
            }
        }

    }

}
