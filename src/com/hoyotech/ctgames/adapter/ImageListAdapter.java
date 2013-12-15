package com.hoyotech.ctgames.adapter;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.hoyotech.ctgames.R;


/**
 * image list adapter
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2012-11-22
 */
public class ImageListAdapter extends BaseAdapter {


    private LayoutInflater inflater;
    public List<Integer>   imageResIdList;


    public ImageListAdapter(Context context){
        super();
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return isEmpty(imageResIdList) ? 0 : imageResIdList.size();
    }


    @Override
    public Object getItem(int position) {
        return isEmpty(imageResIdList) ? null : imageResIdList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.image_list_item, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView)convertView.findViewById(R.id.image_list_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.imageView.setImageResource(imageResIdList.get(position));
        return convertView;
    }


    public List<Integer> getImageList() {
        return imageResIdList;
    }


    public void setImageList(List<Integer> imageList) {
        this.imageResIdList = imageList;
    }


    /**
     * ViewHolder
     *
     * @author Trinea 2012-11-22
     */
    static class ViewHolder {


        ImageView imageView;
    }


    private boolean isEmpty(List<Integer> imageList) {
        return imageList == null || imageList.size() == 0;
    }
}

