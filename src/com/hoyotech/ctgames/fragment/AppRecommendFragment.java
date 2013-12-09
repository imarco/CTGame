package com.hoyotech.ctgames.fragment;

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

import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.AppInfoAdapter;
import com.hoyotech.ctgames.adapter.GalleryAdapter;
import com.hoyotech.ctgames.util.CTGameConstans;
import com.hoyotech.ctgames.util.DataUtils;
import com.hoyotech.ctgames.util.NetworkUtils;
import com.hoyotech.ctgames.viewdef.FlowIndicator;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by GGCoke on 13-12-3.
 */
public class AppRecommendFragment extends Fragment {
    private static final String KEY_CONTENT = "AppRecommendFragment:Content";
    private Bundle bundle;
    private Gallery gallery;
    private GridView gridView;
    private FlowIndicator indicator;
    private Timer timer;


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

        GalleryAdapter adapter_g = new GalleryAdapter(getActivity());
        gallery.setAdapter(adapter_g);

        //设置事件监听响应
        gallery.setOnItemClickListener(listener);
        gallery.setOnItemSelectedListener(selected_listener);


        AppInfoAdapter adapter = new AppInfoAdapter(DataUtils.getAppInfos(getActivity()), getActivity());

        gridView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(KEY_CONTENT, bundle);
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
}
