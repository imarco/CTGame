package com.hoyotech.ctgames.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoyotech.ctgames.R;

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
        Log.e(KEY_CONTENT, "In AppCategoriesFragment.onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(KEY_CONTENT, "In AppCategoriesFragment.onCreateView");
        View v = inflater.inflate(R.layout.fragment_app_categories, container, false);


        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(KEY_CONTENT, bundle);
    }
}
