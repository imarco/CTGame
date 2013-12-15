package com.hoyotech.ctgames.activity;

import android.app.Activity;
import android.os.Bundle;
import com.hoyotech.ctgames.R;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-12
 * Time: 下午11:51
 * To change this template use File | Settings | File Templates.
 */
public class TestActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_more);
    }
}