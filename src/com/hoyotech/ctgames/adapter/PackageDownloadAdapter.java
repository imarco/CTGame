package com.hoyotech.ctgames.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.bean.AppInfo;
import com.hoyotech.ctgames.adapter.holder.PackageDownloadHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-6
 * Time: 下午9:11
 * To change this template use File | Settings | File Templates.
 */
public class PackageDownloadAdapter extends BaseAdapter{

    private List<AppInfo> data;
    private Context context;
    AppInfo info;

    //这个地方并不准确，因为如果要标志一个程序被选中，最好有很多的变量，这里Demo只有一个id，不过如果
    //可以大礼包的记录顺序也可以了。
    List<Integer> cbClick = new ArrayList<Integer>();

    //构造函数
    public PackageDownloadAdapter(ArrayList<AppInfo> data, Context context) {
        this.data = data;
        this.context = context;

        //在这个地方对点击的进行初始化
        for (int i = 0; i < data.size(); i ++){
            cbClick.add(i, 0);
        }
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

        PackageDownloadHolder holder = null;
        if (convertView == null){
            holder = new PackageDownloadHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_package_app_item, null);
            holder.appImageHeader = (ImageView) convertView.findViewById(R.id.image_app);
            holder.appName = (TextView) convertView.findViewById(R.id.tv_app_name);
            holder.appPackageSize = (TextView) convertView.findViewById(R.id.tv_app_size);
            holder.btnOptions = (Button) convertView.findViewById(R.id.btn_options);
            holder.checkBoxToDownload = (CheckBox) convertView.findViewById(R.id.cb_download);
            holder.tvSummary = (TextView) convertView.findViewById(R.id.tv_summary);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progress_bar);
            holder.tvDownloadRate = (TextView) convertView.findViewById(R.id.tv_download_rate);
            convertView.setTag(holder);
        }else {
            holder = (PackageDownloadHolder) convertView.getTag();
        }

        info = data.get(position);
        holder.appImageHeader.setBackgroundDrawable(info.getImg());
        holder.appName.setText(info.getAppName());
        holder.appPackageSize.setText(String.valueOf(info.getAppSize())+"M");

        boolean is_install = info.isInstall();

        //两种状态，安装或者没有安装
        if(is_install) {
            holder.btnOptions.setVisibility(View.VISIBLE);
            holder.checkBoxToDownload.setVisibility(View.GONE);
        } else {
            holder.btnOptions.setVisibility(View.GONE);
            holder.checkBoxToDownload.setVisibility(View.VISIBLE);
        }

        if (holder.btnOptions.isShown()){//只有当其是显示的时候才可以有相关的事件响应
            holder.btnOptions.setOnClickListener(new ButtonClickListener());
        }

        //复选框的点击部分
        holder.checkBoxToDownload.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    cbClick.set(position, 1);
                }
            }
        });

        //表示正在下载部分，或者下载完成，根据progree来判定，所以还要加progress属性
        if (info.isDownloading()){
            holder.tvSummary.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.tvDownloadRate.setVisibility(View.VISIBLE);
        }

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
