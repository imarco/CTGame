package com.hoyotech.ctgames.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoyotech.ctgames.R;

/**
 * 看视频页面
 * @author Tian
 *
 */
public class AwardFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_award, container, false);
//        Button button = (Button) v.findViewById(R.id.btn_app_get_info);


        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.btn_app_get_info:
//                break;
        }
    }
}