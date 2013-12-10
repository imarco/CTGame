package com.hoyotech.ctgames.service;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hoyotech.ctgames.fragment.TaskDownloadFragment;
import com.hoyotech.ctgames.util.CTGameAsyncHttpClient;
import com.hoyotech.ctgames.util.CTGameConstans;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Task模块数据接口
 * Created by GGCoke on 13-12-10.
 */
public class TaskService {

    public static void getTaskDownloadAppList(Context context, final Handler handler) {
        String url = "http://www.ichangge.net/ctgame.php";
        CTGameAsyncHttpClient.post(context, url, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, org.json.JSONArray response) {
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("result", response.toString());
                if (statusCode == HttpStatus.SC_OK && null != response && response.length() >0) {
                    msg.what = CTGameConstans.TASK_DOWNLOAD_GET_APP_INFO_SUCCESS;
                } else {
                    msg.what = CTGameConstans.TASK_DOWNLOAD_GET_APP_INFO_FAILED;
                }
                msg.setData(data);
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(int statusCode, Throwable e, JSONObject errorResponse) {
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("result", "status:" + statusCode + ";response:" + e.getMessage());
                msg.what = CTGameConstans.TASK_DOWNLOAD_GET_APP_INFO_FAILED;
                msg.setData(data);
                handler.sendMessage(msg);
            }
        });
    }
}
