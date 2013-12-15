package com.hoyotech.ctgames.activity;

import android.app.Activity;
import android.os.Bundle;
import com.hoyotech.ctgames.R;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-16
 * Time: 上午12:49
 * To change this template use File | Settings | File Templates.
 */
public class MyAppActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_app_list);
    }
}