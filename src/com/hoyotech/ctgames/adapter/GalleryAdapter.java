package com.hoyotech.ctgames.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.util.BitmapHelp;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;

/**
 * Created by GGCoke on 13-12-6.
 */
public class GalleryAdapter extends BaseAdapter {
    Context context;
//    int[] res = new int[]{R.drawable.t1, R.drawable.t2, R.drawable.t3};
//    List<String> imgSrcList;
    JSONArray jsonArray;
    
    public static BitmapUtils bitmapUtils;

    public GalleryAdapter(Context context, JSONArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
        
        bitmapUtils = BitmapHelp.getBitmapUtils(context.getApplicationContext());
        bitmapUtils.configDefaultLoadingImage(R.drawable.t1);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.t2);
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
    }

    @Override
    public int getCount() {
        return jsonArray.size();
    }

    @Override
    public Object getItem(int position) {
    	return jsonArray.get(position);
    }

    @Override
    public long getItemId(int postion) {
        return postion;
    }

    @Override
    public View getView(int position, View view, ViewGroup arg2) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.gallery_item, null);
        }
        
        BitmapDisplayConfig displayConfig = new BitmapDisplayConfig();
        ImageView img = (ImageView) view.findViewById(R.id.iv_app_recommend);
        bitmapUtils.display(img, ((JSONObject)jsonArray.get(position)).get("adUrl").toString(), displayConfig);
        return view;
    }
}
