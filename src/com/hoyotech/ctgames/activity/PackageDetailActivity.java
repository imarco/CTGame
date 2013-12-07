package com.hoyotech.ctgames.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.PackageDownloadAdapter;
import com.hoyotech.ctgames.util.DataUtils;
import com.hoyotech.ctgames.util.NetworkUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-6
 * Time: 下午9:39
 * To change this template use File | Settings | File Templates.
 */
public class PackageDetailActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.activity_package_detail);

        ListView lv = (ListView)findViewById(R.id.list_package_app);

        PackageDownloadAdapter adapter = new PackageDownloadAdapter(DataUtils.getPackageAppInfos(this), this);

        lv.setAdapter(adapter);
    }
}