package com.hoyotech.ctgames.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.TaskCompleteAdapter;
import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.db.dao.AppDao;
import com.hoyotech.ctgames.util.TaskState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GGCoke on 13-12-3.
 */
public class TaskCompleteFragment extends Fragment {
    private static final String KEY_CONTENT = "TaskCompleteFragment:Content";
    private Bundle bundle;
    private ListView lv;
    private List<AppInfo> apps = new ArrayList<AppInfo>();
    private TaskCompleteAdapter adapter;
    private TaskCompleteReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            bundle = savedInstanceState.getBundle(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task_complete, container, false);
        lv = (ListView) v.findViewById(R.id.list_task_complete);
        // 获取已下载应用信息
        apps.clear();
        AppDao appDao = new AppDao(getActivity());
        apps = appDao.queryAppsByState(TaskState.STATE_TASK_COMPLETE);
        adapter = new TaskCompleteAdapter(getActivity(), apps);
        lv.setAdapter(adapter);

        if (null == receiver) {
            receiver = new TaskCompleteReceiver();
            IntentFilter filter = new IntentFilter(TaskInstallFragment.INTENT_FILTER_ACTION_NAME_TASK_COMPLETE);
            getActivity().registerReceiver(receiver, filter);
        }
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(KEY_CONTENT, bundle);
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        if (null != receiver) {
            getActivity().unregisterReceiver(receiver);
        }
    }

    private class TaskCompleteReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(TaskInstallFragment.INTENT_FILTER_ACTION_NAME_TASK_COMPLETE)) {
                apps.clear();
                AppDao appDao = new AppDao(getActivity());
                apps = appDao.queryAppsByState(TaskState.STATE_TASK_COMPLETE);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
