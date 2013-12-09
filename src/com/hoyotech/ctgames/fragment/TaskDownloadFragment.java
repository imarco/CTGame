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
import com.hoyotech.ctgames.adapter.TaskDownloadAdapter;
import com.hoyotech.ctgames.adapter.holder.TaskDownloadHolder;
import com.hoyotech.ctgames.util.DataUtils;
import com.hoyotech.ctgames.util.TaskState;

/**
 * Created by GGCoke on 13-12-3.
 */
public class TaskDownloadFragment extends Fragment {
    private static final String KEY_CONTENT = "TaskDownloadFragment:Content";
    private Bundle bundle;
    private ListView lv;

    private DownloadReceiver mReceiver;
    public static final String INTENT_FILTER_ACTION_NAME = "com.hoyitech.ctgames.fragment.TaskDownloadFragment";

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

        TaskDownloadAdapter adapter = new TaskDownloadAdapter(getActivity(), DataUtils.getTaskDownloadInfos(getActivity()));

        lv.setAdapter(adapter);

        mReceiver = new DownloadReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(INTENT_FILTER_ACTION_NAME);
        getActivity().registerReceiver(mReceiver, filter);

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


    public class DownloadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent downloadIntent) {

            handleIntent(downloadIntent);

        }

        private void handleIntent(Intent downloadIntent) {

            if (downloadIntent != null && downloadIntent.getAction().equals(INTENT_FILTER_ACTION_NAME)) {
                int state = downloadIntent.getIntExtra(TaskState.DOWNLOAD_STATE, -1);
                String url;
                View convertView;
                TaskDownloadHolder holder;

                switch (state) {
                    case TaskState.STATE_DOWNLOAD:
                        break;
                    case TaskState.STATE_PROGRESS:
                        url = downloadIntent.getStringExtra(TaskState.DOWNLOAD_URL);
                        convertView = lv.findViewWithTag(url);
                        holder = new TaskDownloadHolder(convertView);
                        holder.updateProgress(getActivity(), downloadIntent.getStringExtra(TaskState.DOWNLOAD_SPEED),
                                downloadIntent.getStringExtra(TaskState.DOWNLOAD_PROGRESS));
                        break;
                    case TaskState.STATE_INSTALL:
                        url = downloadIntent.getStringExtra(TaskState.DOWNLOAD_URL);
                        convertView = lv.findViewWithTag(url);
                        holder = new TaskDownloadHolder(convertView);
                        TaskState.setButtonView(TaskState.STATE_INSTALL, getActivity(), holder.btnOptions);
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
