package com.hoyotech.ctgames.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.AppInstallAdapter;
import com.hoyotech.ctgames.util.CTGameUtils;

/**
 * Created by GGCoke on 13-12-3.
 */
public class AppCategoriesFragment extends Fragment {
    private static final String KEY_CONTENT = "AppCategoriesFragment:Content";
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
        View v = inflater.inflate(R.layout.fragment_app_categories, container, false);

        ListView lv = (ListView) v.findViewById(R.id.list_app_cat);

        AppInstallAdapter adapter = new AppInstallAdapter(CTGameUtils.getAPPInstallInfos(getActivity()), getActivity());

        lv.setAdapter(adapter);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(KEY_CONTENT, bundle);
    }
}
