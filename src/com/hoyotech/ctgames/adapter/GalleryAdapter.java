package com.hoyotech.ctgames.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hoyotech.ctgames.R;

/**
 * Created by GGCoke on 13-12-6.
 */
public class GalleryAdapter extends BaseAdapter {
    Context context;
    int[] res = new int[]{R.drawable.t1, R.drawable.t2, R.drawable.t3};

    public GalleryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return res.length;
    }

    @Override
    public Object getItem(int position) {
        return res[position];
    }

    @Override
    public long getItemId(int postion) {
        return postion;
    }

    @Override
    public View getView(int position, View view, ViewGroup arg2) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.gallery_item,
                    null);
        }
        ImageView img = (ImageView) view.findViewById(R.id.iv_app_recommend);
        img.setImageResource(res[position]);
        return view;
    }
}
