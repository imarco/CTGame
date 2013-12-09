package com.hoyotech.ctgames.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.bean.AppInfo;
import com.hoyotech.ctgames.adapter.holder.PackageDownloadHolder;
import com.hoyotech.ctgames.util.TaskState;

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
    AppInfo appInfo;

    //这个地方并不准确，因为如果要标志一个程序被选中，最好有很多的变量，这里Demo只有一个id，不过如果
    //可以大礼包的记录顺序也可以了。
    private List<Integer> cbClick = new ArrayList<Integer>();

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
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_package_app_item, null);
            holder = new PackageDownloadHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (PackageDownloadHolder) convertView.getTag();
        }

        appInfo = data.get(position);
        holder.setData(context, appInfo);

        //复选框的点击部分
        holder.checkBoxToDownload.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    cbClick.set(position, 1);
                }
            }
        });

        // 只有当不是选择模式的时候才进行按钮事件的响应
        if (!holder.isSelectionMode()){
            holder.btnOptions.setOnClickListener(new ButtonClickListener(appInfo, holder));
        }

        return convertView;
    }

    private class ButtonClickListener implements View.OnClickListener {
        private AppInfo info;
        private PackageDownloadHolder holder;

        public ButtonClickListener(AppInfo info, PackageDownloadHolder holder) {
            this.info = info;
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_app_bonus:
                    // 补充响应
                    // 应该弹出应用详情信息
                    break;
                case R.id.btn_options:
                    // 补充响应
                    // 根据按钮的状态决定操作
                    // 点击下载-暂停 点击暂停-继续 点击继续-暂停 点击安装-安装中
                    // 打开-领取奖励 (中间状态 安装完成之后变成打开)
                    // 点击领取奖励再进行跳转
                    System.out.println("被点击了");
                    if(info.getState() == TaskState.STATE_DOWNLOAD) {
                        info.setState(TaskState.STATE_PAUSE);
                        info.setMode(TaskState.MODE_DOWNLOADING);
                        holder.setData(context, info);
                        // 通知service开始下载

                    } else if(info.getState() == TaskState.STATE_PAUSE) {
                        info.setState(TaskState.STATE_CONTINUE);
                        info.setMode(TaskState.MODE_DOWNLOADING);
                        holder.setData(context, info);
                        // 通知service暂停下载

                    } else if(info.getState() == TaskState.STATE_CONTINUE) {
                        info.setState(TaskState.STATE_PAUSE);
                        info.setMode(TaskState.MODE_DOWNLOADING);
                        holder.setData(context, info);
                        // 通知service继续下载

                    } else if(info.getState() == TaskState.STATE_INSTALL) {
                        info.setState(TaskState.STATE_INSTALLING);
                        info.setMode(TaskState.MODE_INSTALL);
                        holder.setData(context, info);
                        // 当安装完成之后，在这个时候需要更新状态表示打开
                        // 通知service安装

                    } else if(info.getState() == TaskState.STATE_OPEN) {
                        info.setState(TaskState.STATE_GET_PRIZE);
                        info.setMode(TaskState.MODE_INSTALL);
                        holder.setData(context, info);
                        // 通知service打开安装好的应用

                    } else if(info.getState() == TaskState.STATE_GET_PRIZE) {
                        info.setMode(TaskState.MODE_INSTALL);
                        holder.setData(context, info);
                        // 点击获取奖励之后的动作
                    }
                    break;
                default:
                    // 补充默认情况
                    break;
            }
        }

    }

}
