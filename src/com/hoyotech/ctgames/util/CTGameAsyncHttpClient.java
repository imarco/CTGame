package com.hoyotech.ctgames.util;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by GGCoke on 13-12-10.
 */
public class CTGameAsyncHttpClient {
    private static final String BASE_URL = "";

    private static AsyncHttpClient client = new AsyncHttpClient();

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static void get(Context context, String url, RequestParams params, AsyncHttpResponseHandler handler) {
        client.get(context, getAbsoluteUrl(url), params, handler);
    }

    public static void post(Context context, String url, RequestParams params, AsyncHttpResponseHandler handler) {
        client.post(context, getAbsoluteUrl(url), params, handler);
    }
}
