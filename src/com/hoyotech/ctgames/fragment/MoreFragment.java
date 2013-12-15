package com.hoyotech.ctgames.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.activity.MyAppActivity;

/**
 * 个人中心页面
 * @author Tian
 *
 */
public class MoreFragment extends Fragment implements View.OnClickListener{

    private ImageView iamgeMyapp;
    private LinearLayout baseLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        baseLayout = (LinearLayout) inflater.inflate(R.layout.fragment_more, null);
        iamgeMyapp = (ImageView) baseLayout.findViewById(R.id.image_myzone_app);

        iamgeMyapp.setOnClickListener(this);
        return baseLayout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.image_myzone_app:
                Intent intent = new Intent(getActivity(), MyAppActivity.class);
                startActivity(intent);
                break;
        }
    }
}