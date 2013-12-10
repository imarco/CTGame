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
import android.widget.Toast;

import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.TaskDownloadAdapter;
import com.hoyotech.ctgames.adapter.bean.AppInfo;
import com.hoyotech.ctgames.adapter.holder.TaskDownloadHolder;
import com.hoyotech.ctgames.service.TaskService;
import com.hoyotech.ctgames.util.CTGameConstans;
import com.hoyotech.ctgames.util.TaskState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GGCoke on 13-12-3.
 */
public class TaskDownloadFragment extends Fragment {
    private static final String KEY_CONTENT = "TaskDownloadFragment:Content";
    private static final String APP_INFO_URL = "http://localhost/doa/test.php";
    private DownloadReceiver mReceiver;
    private TaskDownloadAdapter adapter;
    private List<AppInfo> apps;
    private Bundle bundle;
    private ListView lv;

    public static final String INTENT_FILTER_ACTION_NAME_TASK_DOWNLOAD = "com.hoyitech.ctgames.fragment.TaskDownloadFragment";


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CTGameConstans.TASK_DOWNLOAD_GET_APP_INFO_SUCCESS:
                    String data = msg.getData().getString("result", "");
                    getApp(data);
                    adapter.setData(apps);
                    adapter.notifyDataSetChanged();
                    break;
                case CTGameConstans.TASK_DOWNLOAD_GET_APP_INFO_FAILED:
                    Toast.makeText(getActivity(), "Get app failed!!!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void getApp(String data) {
        try {
            JSONArray array = new JSONArray(data);
            if (null != array && array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    AppInfo info = getAppFromJSON(obj);
                    apps.add(info);
                }
            }

        } catch (JSONException e) {
            Toast.makeText(getActivity(), "解析数据错误", Toast.LENGTH_SHORT).show();
        }
    }

    private AppInfo getAppFromJSON(JSONObject obj) {
        AppInfo info = new AppInfo();
        try {
            info.setImg(obj.getString("image_path"));
            info.setUrl(obj.getString("url"));
            info.setAppName(obj.getString("name"));
            info.setAppSize(obj.getLong("size"));
            info.setState(obj.getInt("state"));
            info.setSummary(obj.getString("summary"));
            info.setPrizeCount(obj.getInt("prize_count"));
        } catch (JSONException e) {
            return null;
        }

        return info;
    }

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
        apps = new ArrayList<AppInfo>();
        adapter = new TaskDownloadAdapter(getActivity(), apps);

        lv.setAdapter(adapter);

        mReceiver = new DownloadReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(INTENT_FILTER_ACTION_NAME_TASK_DOWNLOAD);
        getActivity().registerReceiver(mReceiver, filter);
        TaskService.getTaskDownloadAppList(getActivity(), handler);
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

            if (downloadIntent != null && downloadIntent.getAction().equals(INTENT_FILTER_ACTION_NAME_TASK_DOWNLOAD)) {
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
