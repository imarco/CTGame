package com.hoyotech.ctgames.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.widget.LinearLayout;
import com.hoyotech.ctgames.R;


public class OrderProductFragment extends Fragment {

    String url = "http://g.hoyotech.com:27088/vas/index_android";
    String testURL = "http://192.168.88.85:3000/vas/index_android";

    LinearLayout baseLayout;
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        baseLayout = (LinearLayout) inflater.inflate(R.layout.fragment_order_product, null);
        webView = (WebView) baseLayout.findViewById(R.id.order_product_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(testURL);
        return baseLayout;
    }
}