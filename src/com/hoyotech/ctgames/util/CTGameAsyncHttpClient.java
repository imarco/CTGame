package com.hoyotech.ctgames.util;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by GGCoke on 13-12-10.
 */
public class CTGameAsyncHttpClient {
    private static final String BASE_URL = "";

    private static AsyncHttpClient client = new AsyncHttpClient();

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static void get(Context context, String url, RequestParams params, final Handler handler) {
        post(context, url, params, handler);
    }

    public static void post(Context context, String url, RequestParams params, final Handler handler) {
        client.post(context, getAbsoluteUrl(url), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONArray response) {
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                if (statusCode == HttpStatus.SC_OK && null != response && response.length() > 0) {
                    // 响应返回200OK，请求成功
                    try {
                        JSONObject result = response.getJSONObject(0);
                        if (result.getString("returnCode").equals(CTGameConstans.RESPONSE_RETURN_CODE_SUCCESS)) {
                            // 请求成功
                            msg.what = CTGameConstans.RESPONSE_SUCCESS;
                            // 获取data数据主体部分
                            bundle.putString("data", result.getString("data"));
                        } else {
                            // 请求异常，返回异常信息
                            msg.what = CTGameConstans.RESPONSE_FAILED;
                            bundle.putString("errorMsg", result.getString("message"));
                        }
                    } catch (JSONException e) {
                        // 解析json错误
                        msg.what = CTGameConstans.RESPONSE_FAILED;
                        bundle.putString("errorMsg", "数据异常，请重试");
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
                } else {
                    // 响应返回非200，网络异常
                    msg.what = CTGameConstans.RESPONSE_FAILED;
                    bundle.putString("errorMsg", "网络异常，请重试");
                }
                msg.setData(bundle);
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(int statusCode, Throwable e, JSONObject errorResponse) {
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                msg.what = CTGameConstans.RESPONSE_FAILED;
                bundle.putString("errorMsg", "网络异常，请重试");
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });
    }


}
