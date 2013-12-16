package com.hoyotech.ctgames.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.PackageInfoAdapter;
import com.hoyotech.ctgames.db.bean.PackageInfo;
import com.hoyotech.ctgames.util.Constant;
import com.hoyotech.ctgames.util.GetDataCallback;
import com.hoyotech.ctgames.util.GetDataTask;

/**
 * Created by GGCoke on 13-12-3.
 */
public class AppCategoriesFragment extends Fragment implements GetDataCallback{
    private static final String KEY_CONTENT = "AppCategoriesFragment:Content";
    private Bundle bundle;
    private GridView gridView;
    private Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            bundle = savedInstanceState.getBundle(KEY_CONTENT);
        }
        Log.e(KEY_CONTENT, "In AppCategoriesFragment.onCreate");
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(KEY_CONTENT, "In AppCategoriesFragment.onCreateView");
        View v = inflater.inflate(R.layout.fragment_app_categories, container, false);
        gridView = (GridView) v.findViewById(R.id.gv_app_categories);
        
        new GetDataTask(this, Constant.GETCLASSLIST).execute(""); // ��ȡӦ�÷����б�

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
            case Constant.GETCLASSLIST:
//                adapter = new PackageInfoAdapter(getActivity(), PackageInfo.parseJson( (JSONArray) ((JSONObject)JsonObject.get("data")).get("packList")));
//                gv.setAdapter(adapter);
                break;
            default:
                break;
        }
    }

    @Override
    public Handler GetHandle() {
        return handler;
    }
}
