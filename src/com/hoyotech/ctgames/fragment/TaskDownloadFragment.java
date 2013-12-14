package com.hoyotech.ctgames.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.TaskDownloadAdapter;
import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.adapter.holder.TaskDownloadHolder;
import com.hoyotech.ctgames.db.dao.AppDao;
import com.hoyotech.ctgames.service.DownloadTask;
import com.hoyotech.ctgames.util.Constant;
import com.hoyotech.ctgames.util.GetDataCallback;
import com.hoyotech.ctgames.util.TaskState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GGCoke on 13-12-3.
 */
public class TaskDownloadFragment extends Fragment implements GetDataCallback {
    private static final String KEY_CONTENT = "TaskDownloadFragment:Content";
    private DownloadReceiver mReceiver;
    private TaskDownloadAdapter adapter;
    private List<AppInfo> apps = new ArrayList<AppInfo>();
    private Bundle bundle;
    private ListView lv;

    public static final String INTENT_FILTER_ACTION_NAME_TASK_DOWNLOAD = "com.hoyitech.ctgames.fragment.TaskDownloadFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            bundle = savedInstanceState.getBundle(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task_download, container, false);
        lv = (ListView) v.findViewById(R.id.list_task_download);

        // TODO 获取用户流量信息
//        new GetDataTask(this, Constant.GETUSERINFO).execute();

        mReceiver = new DownloadReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadTask.ACTION_DOWNLOAD);
        getActivity().registerReceiver(mReceiver, filter);

        // 获取正在下载的应用列表
        AppDao appDao = new AppDao(getActivity());
        List<AppInfo> downloading = appDao.queryAppsByState(TaskState.STATE_DOWNLOADING);
        List<AppInfo> paused = appDao.queryAppsByState(TaskState.STATE_PAUSED);
        if (null != downloading && downloading.size() > 0) {
            apps.addAll(downloading);
        }
        if (null != paused && paused.size() > 0) {
            apps.addAll(paused);
        }
        adapter = new TaskDownloadAdapter(getActivity(), apps);
        lv.setAdapter(adapter);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(KEY_CONTENT, bundle);
    }

    @Override
    public void onDestroy() {
        if(mReceiver != null)
            getActivity().unregisterReceiver(mReceiver);
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void AddData(String data, int flag) {
        switch (flag) {
            // TODO 添加用户流量信息
            case Constant.GETUSERINFO:
                break;
        }
    }

    @Override
    public Handler GetHandle() {
        return null;
    }


    public class DownloadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent downloadIntent) {

            handleIntent(downloadIntent);

        }

        private void handleIntent(Intent downloadIntent) {

            if (downloadIntent != null && downloadIntent.getAction().equals(DownloadTask.ACTION_DOWNLOAD)) {
                int state = downloadIntent.getIntExtra(TaskState.DOWNLOAD_STATE, -1);
                String url;
                View convertView;
                TaskDownloadHolder holder;

                switch (state) {
                    case TaskState.STATE_PREPARE:

                        break;
                    case TaskState.STATE_DOWNLOADING:
                        url = downloadIntent.getStringExtra(TaskState.DOWNLOAD_URL);
                        convertView = lv.findViewWithTag(url);
                        if (null != convertView) {
                            holder = new TaskDownloadHolder(convertView);
                            holder.updateProgress(getActivity(), downloadIntent.getStringExtra(TaskState.DOWNLOAD_SPEED),
                                    downloadIntent.getStringExtra(TaskState.DOWNLOAD_PROGRESS));
                        }
                        break;
                    case TaskState.STATE_COMPLETE:
                        url = downloadIntent.getStringExtra(TaskState.DOWNLOAD_URL);
                        convertView = lv.findViewWithTag(url);
                        if (null != convertView) {
                            holder = new TaskDownloadHolder(convertView);
                            apps.remove(holder.info);
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case TaskState.STATE_STOP:
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
