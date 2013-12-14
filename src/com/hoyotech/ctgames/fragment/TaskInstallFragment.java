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
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.TaskInstallAdapter;
import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.db.dao.AppDao;
import com.hoyotech.ctgames.util.CTGameConstans;
import com.hoyotech.ctgames.util.DataUtils;
import com.hoyotech.ctgames.util.GetDataCallback;
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
            getActivity().registerReceiver(receiver, filter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(KEY_CONTENT, "In TaskInstallFragment.onCreateView");
        View v = inflater.inflate(R.layout.fragment_task_install, container, false);
        lv = (ListView) v.findViewById(R.id.list_task_install);

        // 获取已下载且任务未完成的应用信息
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
                        if (packageName.equalsIgnoreCase(packageInfo.packageName)) {
                            // 安装完成，adapter更新显示
                            app.setState(TaskState.STATE_INSTALLED);
                            adapter.notifyDataSetChanged();

                            // TODO 向服务器发送安装应用请求
                            break;
                        }
                    } catch (MalformedURLException e) {}
                }
            }
        }
    }
}
