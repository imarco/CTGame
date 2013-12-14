package com.hoyotech.ctgames.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hoyotech.ctgames.R;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-14
 * Time: 下午2:24
 * To change this template use File | Settings | File Templates.
 */
public class BigWheelFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_big_wheel, null);
    }
}
