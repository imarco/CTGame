package com.hoyotech.ctgames.util;

import com.hoyotech.ctgames.test.TestResponse;

import android.os.AsyncTask;
import android.os.Handler;

public class GetDataTask extends AsyncTask<String, Integer, String> {

	private GetDataCallback mCallback = null;
	private int mFlag;

	public GetDataTask(GetDataCallback callback, int flag) {

		mCallback = callback;
		mFlag = flag;
	}

	@Override
    protected String doInBackground(String... params) {
        Handler handler = mCallback.GetHandle();
        if (null != handler) {
            handler.post(new Runnable() {
                public void run() {
                }
            });
        }

        return getDate(params[0]);
    }

    @Override
	protected void onPostExecute(final String result) {

		if (result != null && result.length() > 0) {
			mCallback.AddData(result, mFlag);
		} else {
            Handler handler = mCallback.GetHandle();
            if (null != handler) {
                handler.post(new Runnable() {
                    public void run() {
                    }
                });
            }
		}
		super.onPostExecute(result);
	}

	private String getDate(String request) {
		String response = null;
//		response = http.HttpPost(url);
		switch(mFlag){
		case Constant.GETRECOMMENDLIST: // 获取推荐轮播列表
			response = TestResponse.GETRECOMMENDLIST;
			break;
		case Constant.GETHOTAPPLIST: // 获取热门应用列表
			response = TestResponse.getHotAppList();
			break;
		default:
			response = "";
			break;
		}
		
		
		return response;
	}
}
