package com.hoyotech.ctgames.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.util.AppInfoHolder;
import com.hoyotech.ctgames.util.TaskState;

import java.util.HashMap;
import java.util.List;

/**
 * Created by GGCoke on 13-12-6.
 */
public class AppInfoAdapter extends BaseAdapter {
    private List<HashMap<String, Object>> data;
    private Context context;
    private HashMap<String, Object> map;

    //构造函数
    public AppInfoAdapter(List<HashMap<String, Object>> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        AppInfoHolder holder = null;
        if (convertView == null){
            holder = new AppInfoHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_app_simple_info, null);
            holder.app_img_head = (ImageView) convertView.findViewById(R.id.app_img_head);
            holder.app_name = (TextView) convertView.findViewById(R.id.app_name);
            holder.app_package_size = (TextView) convertView.findViewById(R.id.app_package_size);
            holder.btn_options = (Button) convertView.findViewById(R.id.btn_options);
            holder.tv_download_number = (TextView) convertView.findViewById(R.id.tv_download_number);
            holder.tv_download_money = (TextView) convertView.findViewById(R.id.tv_download_money);
            convertView.setTag(holder);
        }else {
            holder = (AppInfoHolder) convertView.getTag();
        }
        map = data.get(position);
        holder.app_img_head.setBackgroundDrawable((Drawable) map.get("drawable"));
        holder.app_name.setText((String)map.get("name"));
        holder.app_package_size.setText(map.get("size").toString());
        holder.btn_options.setText(TaskState.getTaskStateMap().get((Integer)map.get("state")));
        holder.tv_download_number.setText(map.get("download").toString());
        holder.tv_download_money.setText(map.get("bonus").toString());

        //设置事件监听响应
        holder.btn_options.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                
            }
        });

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
