package com.hoyotech.ctgames.fragment;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.AppInfoAdapter;
import com.hoyotech.ctgames.adapter.GalleryAdapter;
import com.hoyotech.ctgames.adapter.PackageInfoAdapter;
import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.db.bean.PackageInfo;
import com.hoyotech.ctgames.util.Constant;
import com.hoyotech.ctgames.util.GetDataCallback;
import com.hoyotech.ctgames.util.GetDataTask;

import java.util.ArrayList;

/**
 * Created by GGCoke on 13-12-3.
 */
public class AppCollectionFragment extends Fragment implements GetDataCallback {
    private static final String KEY_CONTENT = "AppCollectionFragment:Content";
    private Bundle bundle;

    private ListView lv;
    private PackageInfoAdapter adapter;
    private Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            bundle = savedInstanceState.getBundle(KEY_CONTENT);
        }
        Log.e(KEY_CONTENT, "In AppCollectionFragment.onCreate");

        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(KEY_CONTENT, "In AppCollectionFragment.onCreateView");
        View v = inflater.inflate(R.layout.fragment_app_collection, container, false);

        //相关的资源变量的索取
        lv = (ListView) v.findViewById(R.id.list_package_info);

        new GetDataTask(this, Constant.GETPACKLIST).execute(""); // 获取热门应用列表

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(KEY_CONTENT, bundle);


    }

    @Override
    public void AddData(String data, int flag) {

        JSONObject JsonObject = JSON.parseObject(data);
        switch(flag){
            case Constant.GETPACKLIST:
                adapter = new PackageInfoAdapter(getActivity(), PackageInfo.parseJson( (JSONArray) ((JSONObject)JsonObject.get("data")).get("packList")));
                lv.setAdapter(adapter);
                break;
            default:
                break;
        }


        lv.setAdapter(adapter);
    }

    @Override
    public Handler GetHandle() {
        return handler;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
