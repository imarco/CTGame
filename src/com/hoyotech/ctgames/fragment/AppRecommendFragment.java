package com.hoyotech.ctgames.fragment;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.AppInfoAdapter;
import com.hoyotech.ctgames.adapter.GalleryAdapter;
import com.hoyotech.ctgames.adapter.holder.TaskDownloadHolder;
import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.service.DownloadTask;
import com.hoyotech.ctgames.util.*;
import com.hoyotech.ctgames.viewdef.FlowIndicator;

/**
 * Created by GGCoke on 13-12-3.
 */
public class AppRecommendFragment extends Fragment implements GetDataCallback {
    private static final String KEY_CONTENT = "AppRecommendFragment:Content";
    private Bundle bundle;
    private Gallery gallery;
    private GridView gridView;
    private FlowIndicator indicator;
    private Timer timer;
    private final String TAG = "AppRecommendFragment";
    private List<AppInfo> apps = new ArrayList<AppInfo>();
    private AppInfoAdapter adapter;
    private DownloadReceiver mDownloadReceiver;
    private InstallReceiver mInstallReceiver;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            bundle = savedInstanceState.getBundle(KEY_CONTENT);
        }

        Log.e(KEY_CONTENT, "In AppRecommendFrament.onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(KEY_CONTENT, "In AppRecommendFrament.onCreateView");
        View v = inflater.inflate(R.layout.fragment_app_recommend, container, false);
        gallery = (Gallery) v.findViewById(R.id.gallery_app_recommend);
        gridView = (GridView) v.findViewById(R.id.gv_app_recommend);
        indicator = (FlowIndicator) v.findViewById(R.id.fi_app_commend);
        indicator.setCount(gallery.getCount());
        timer = new Timer();
        timer.scheduleAtFixedRate(new RefreshTask(), 0, CTGameConstans.APP_RECOMMEND_REFRESH_DURATION);

        //设置事件监听响应
        gallery.setOnItemClickListener(listener);
        gallery.setOnItemSelectedListener(selected_listener);

        //new GetDataTask(this, Constant.GETRECOMMENDLIST).execute("");// 获取推荐轮播的图片列表
        new GetDataTask(this, Constant.GETHOTAPPLIST).execute(""); // 获取热门应用列表

        adapter = new AppInfoAdapter(getActivity(), apps);
        gridView.setAdapter(adapter);

        if (mDownloadReceiver == null) {
            mDownloadReceiver = new DownloadReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(DownloadTask.ACTION_DOWNLOAD);
            getActivity().registerReceiver(mDownloadReceiver, filter);
        }

        if (mInstallReceiver == null) {
            mInstallReceiver = new InstallReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_PACKAGE_ADDED);
            filter.addAction(Intent.ACTION_PACKAGE_REPLACED);
            filter.addDataScheme("package");    // 这里需要设置scheme，否则接收不到
            getActivity().registerReceiver(mInstallReceiver, filter);
        }
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(KEY_CONTENT, bundle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
        if (mDownloadReceiver != null) {
            getActivity().unregisterReceiver(mDownloadReceiver);
        }

        if (mInstallReceiver != null) {
            getActivity().unregisterReceiver(mInstallReceiver);
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CTGameConstans.APP_RECOMMEND_REFRESH:
                    MotionEvent e1 = MotionEvent.obtain(SystemClock.uptimeMillis(),
                            SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN,
                            89.333336f, 265.33334f, 0);
                    MotionEvent e2 = MotionEvent.obtain(SystemClock.uptimeMillis(),
                            SystemClock.uptimeMillis(), MotionEvent.ACTION_UP,
                            300.0f, 238.00003f, 0);

                    gallery.onFling(e1, e2, -1300, 0);
                    break;
            }
        }
    };

    private class RefreshTask extends TimerTask {
        @Override
        public void run() {
            handler.sendEmptyMessage(CTGameConstans.APP_RECOMMEND_REFRESH);
        }
    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            switch (position) {
                case 0:

                    break;

                case 1:

                    break;

                case 2:

                    break;
            }
            Toast.makeText(getActivity(), position + "---", 0).show();
        }
    };

    AdapterView.OnItemSelectedListener selected_listener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View view, int position, long l) {
            indicator.setSeletion(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }

    };


	@Override
	public void AddData(String response, int flag) {
//		Log.v(TAG, response);
        JSONObject JsonObject = JSON.parseObject(response);
		switch(flag){
			case Constant.GETRECOMMENDLIST:
				Log.v(TAG, "GETRECOMMENDLIST");
		        GalleryAdapter adapter_g = new GalleryAdapter(getActivity(), (JSONArray)((JSONObject)JsonObject.get("data")).get("adList"));
		        gallery.setAdapter(adapter_g);
		        break;
			case Constant.GETHOTAPPLIST:
				Log.v(TAG, "GETHOTAPPLIST");
                apps = AppInfo.parseJson((JSONArray) ((JSONObject) JsonObject.get("data")).get("appList"));
                adapter.setListData(apps);
                adapter.notifyDataSetChanged();
		        break;
		    default:
		    	break;
		}
	}

	@Override
	public Handler GetHandle() {
		return handler;
	}

    private class DownloadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent downloadIntent) {

            handleIntent(downloadIntent);

        }

        private void handleIntent(Intent downloadIntent) {

            if (downloadIntent != null && downloadIntent.getAction().equals(DownloadTask.ACTION_DOWNLOAD)) {
                int state = downloadIntent.getIntExtra(TaskState.DOWNLOAD_STATE, -1);

                switch (state) {
                    case TaskState.STATE_PREPARE:
                        break;
                    case TaskState.STATE_DOWNLOADING:
                        break;
                    case TaskState.STATE_COMPLETE:
                        String url = downloadIntent.getStringExtra(TaskState.DOWNLOAD_URL);
                        for (AppInfo info : apps) {
                            if (info.getAppUrl().equals(url)) {
                                info.setState(TaskState.STATE_COMPLETE);
                                break;
                            }
                        }
                        adapter.notifyDataSetChanged();
                        break;
                    case TaskState.STATE_STOP:
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 安装完成监听接口
     */
    private class InstallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED) || intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
                String packageName = intent.getData().getSchemeSpecificPart();
                for (AppInfo app : apps) {
                    try {
                        String fileName = new File(new URL(app.getAppUrl()).getFile()).getName();
                        String apkName = CTGameConstans.CTGAME_APP_DOWNLOAD_DIR + fileName;
                        PackageManager pm = context.getPackageManager();
                        PackageInfo packageInfo = pm.getPackageArchiveInfo(apkName, PackageManager.GET_ACTIVITIES);
                        if (packageName.equals(packageInfo.packageName)) {
                            // 安装完成，adapter更新显示
                            app.setState(TaskState.STATE_INSTALLED);
                            adapter.notifyDataSetChanged();

                            // TODO 向服务器发送安装应用请求，由服务器判断是否是第一次安装
                            JSONObject request = new JSONObject();
                            JSONObject d = new JSONObject();
                            d.put("id", app.getAppId());
                            d.put("download", Constant.REQUEST_APP_TYPE_INSTALL);
                            d.put("downloadSize", 0);
                            request.put("type", CTGameConstans.REQUEST_TYPE_DOWNLOADAPP);
                            request.put("sessionId", StorageUtils.getSessionID());
                            request.put("versionId", CTGameConstans.VERSION);
                            request.put("phone", StorageUtils.getUserPhoneNumber());
                            request.put("data", d);
                            new GetDataTask(new GetDataCallback() {
                                @Override
                                public void AddData(String data, int flag) {
                                }

                                @Override
                                public Handler GetHandle() {
                                    return null;
                                }
                            }, Constant.DOWNLOADAPP).execute(request.toJSONString());
                            break;
                        }
                    } catch (MalformedURLException e) {}
                }
            }
        }
    }
}
