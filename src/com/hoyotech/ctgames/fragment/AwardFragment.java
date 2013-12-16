package com.hoyotech.ctgames.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.widget.LinearLayout;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.util.CTGameConstans;

/**
 * 看视频页面
 * @author Tian
 *
 */
public class AwardFragment extends Fragment {

    String url = "http://g.hoyotech.com:27088/redeem/index_android";
    String testURL = "http://192.168.88.85:3000/redeem/index_android";

    LinearLayout baseLayout;
    WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        baseLayout = (LinearLayout) inflater.inflate(R.layout.fragment_award, null);
        webView = (WebView) baseLayout.findViewById(R.id.award_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(CTGameConstans.AWARD_WEB_URL);
        return baseLayout;
    }
}