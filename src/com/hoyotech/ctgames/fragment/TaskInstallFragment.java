package com.hoyotech.ctgames.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.TaskInstallAdapter;
import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.db.dao.AppDao;
import com.hoyotech.ctgames.util.CTGameConstans;
import com.hoyotech.ctgames.util.Constant;
import com.hoyotech.ctgames.util.DataUtils;
import com.hoyotech.ctgames.util.GetDataCallback;
import com.hoyotech.ctgames.util.GetDataTask;
import com.hoyotech.ctgames.util.StorageUtils;
import com.hoyotech.ctgames.util.TaskState;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GGCoke on 13-12-3.
 */
public class TaskInstallFragment extends Fragment  {
    private static final String KEY_CONTENT = "TaskInstallFragment:Content";
    private ListView lv;
    private Bundle bundle;
    private List<AppInfo> apps = new ArrayList<AppInfo>();
    private TaskInstallAdapter adapter;
    private InstallReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(KEY_CONTENT, "In TaskInstallFragment.onCreate");
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            bundle = savedInstanceState.getBundle(KEY_CONTENT);
        }
        if (null == receiver) {
            receiver = new InstallReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_PACKAGE_ADDED);
            filter.addAction(Intent.ACTION_PACKAGE_REPLACED);
            filter.addDataScheme("package");    // 这里需要设置scheme，否则接收不到
            getActivity().registerReceiver(receiver, filter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(KEY_CONTENT, "In TaskInstallFragment.onCreateView");
        View v = inflater.inflate(R.layout.fragment_task_install, container, false);
        lv = (ListView) v.findViewById(R.id.list_task_install);

        // 获取已下载且任务未完成的应用信息
        apps.clear();
        AppDao appDao = new AppDao(getActivity());
        apps.addAll(appDao.queryAppsByState(TaskState.STATE_COMPLETE));
        apps.addAll(appDao.queryAppsByState(TaskState.STATE_INSTALLED));
        apps.addAll(appDao.queryAppsByState(TaskState.STATE_OPENED));
        adapter = new TaskInstallAdapter(getActivity(), apps);
        lv.setAdapter(adapter);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(KEY_CONTENT, bundle);
    }

    @Override
    public void onDestroy () {
        if (null != receiver) {
            getActivity().unregisterReceiver(receiver);
        }
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 遍历应用列表，如果用户在安装时点击了取消，需要将appInfo的state设置成STATE_COMPLETE，不然
        // 依然显示安装中
        PackageManager pm = getActivity().getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (AppInfo appInfo : apps) {
            // 状态为安装中的应用如果不在已安装列表中，说明用户点击了取消安装
            if (appInfo.getState() == TaskState.STATE_INSTALLING) {
                boolean installed = false;
                try {
                    String fileName = new File(new URL(appInfo.getAppUrl()).getFile()).getName();
                    String apkName = CTGameConstans.CTGAME_APP_DOWNLOAD_DIR + fileName;
                    PackageInfo packageInfo = pm.getPackageArchiveInfo(apkName, PackageManager.GET_ACTIVITIES);
                    for (PackageInfo app : packages) {
                        // TODO 添加通过签名判断是否是通过本应用安装的
                        if (app.packageName.equals(packageInfo.packageName)) {
                            installed = true;
                            break;
                        }
                    }

                    if (!installed) {
                        appInfo.setState(TaskState.STATE_COMPLETE);
                    }
                } catch (MalformedURLException e) {}
            }
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 安装完成监听接口
     */
    private class InstallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED) || intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
                String packageName = intent.getData().getSchemeSpecificPart();
                for (AppInfo app : apps) {
                    try {
                        String fileName = new File(new URL(app.getAppUrl()).getFile()).getName();
                        String apkName = CTGameConstans.CTGAME_APP_DOWNLOAD_DIR + fileName;
                        PackageManager pm = context.getPackageManager();
                        PackageInfo packageInfo = pm.getPackageArchiveInfo(apkName, PackageManager.GET_ACTIVITIES);
                        if (packageName.equals(packageInfo.packageName)) {
                            // 安装完成，adapter更新显示
                            app.setState(TaskState.STATE_INSTALLED);
                            adapter.notifyDataSetChanged();

                            // TODO 向服务器发送安装应用请求，由服务器判断是否是第一次安装
                            JSONObject request = new JSONObject();
                            JSONObject d = new JSONObject();
                            d.put("id", app.getAppId());
                            d.put("download", Constant.REQUEST_APP_TYPE_INSTALL);
                            d.put("downloadSize", 0);
                            request.put("type", CTGameConstans.REQUEST_TYPE_DOWNLOADAPP);
                            request.put("sessionId", StorageUtils.getSessionID());
                            request.put("versionId", CTGameConstans.VERSION);
                            request.put("phone", StorageUtils.getUserPhoneNumber());
                            request.put("data", d);
                            new GetDataTask(new GetDataCallback() {
                                @Override
                                public void AddData(String data, int flag) {
                                }

                                @Override
                                public Handler GetHandle() {
                                    return null;
                                }
                            }, Constant.DOWNLOADAPP).execute(request.toJSONString());
                            break;
                        }
                    } catch (MalformedURLException e) {}
                }
            }
        }
    }
}
