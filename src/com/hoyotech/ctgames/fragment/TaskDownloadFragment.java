package com.hoyotech.ctgames.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.TaskDownloadAdapter;
import com.hoyotech.ctgames.util.DataUtils;
import com.hoyotech.ctgames.util.NetworkUtils;

/**
 * Created by GGCoke on 13-12-3.
 */
public class TaskDownloadFragment extends Fragment {
    private static final String KEY_CONTENT = "TaskDownloadFragment:Content";
    private Bundle bundle;

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
        ListView lv = (ListView) v.findViewById(R.id.list_task_download);

        TaskDownloadAdapter adapter = new TaskDownloadAdapter(getActivity(), DataUtils.getTaskDownloadInfos(getActivity()));

        lv.setAdapter(adapter);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(KEY_CONTENT, bundle);


    }



}
