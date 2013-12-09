package com.hoyotech.ctgames.activity;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.PackageInfoAdapter;
import com.hoyotech.ctgames.adapter.bean.AppPackageInfo;

import java.util.ArrayList;

/**
 * Created by GGCoke on 13-12-3.
 */
public class AppCollectionFragment extends Fragment {
    private static final String KEY_CONTENT = "AppCollectionFragment:Content";
    private Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            bundle = savedInstanceState.getBundle(KEY_CONTENT);
        }
        Log.e(KEY_CONTENT, "In AppCollectionFragment.onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(KEY_CONTENT, "In AppCollectionFragment.onCreateView");
        View v = inflater.inflate(R.layout.fragment_app_collection, container, false);

        //相关的资源变量的索取
        ListView lv = (ListView) v.findViewById(R.id.list_package_info);

        PackageInfoAdapter adapter = new PackageInfoAdapter(getPackageInfos(getActivity()), getActivity());

        lv.setAdapter(adapter);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(KEY_CONTENT, bundle);


    }

    public static ArrayList<AppPackageInfo> getPackageInfos(Context context) {
        ArrayList<AppPackageInfo> infos = new ArrayList<AppPackageInfo>();
        Drawable d = context.getResources().getDrawable(
                R.drawable.image_app_package);
        for (int i = 0; i < 3; i++) {
            AppPackageInfo packageInfo = new AppPackageInfo();
            packageInfo.setImg(d);
            packageInfo.setName("礼包" + i);
            packageInfo.setSize(i * i);
            packageInfo.setState(1);
            packageInfo.setPrizeCount(6 - i + i * i);
            packageInfo.setLuckybeanCount(300);
            infos.add(packageInfo);
        }

        return infos;
    }

}
