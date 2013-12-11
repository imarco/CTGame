package com.hoyotech.ctgames.util;

import android.os.AsyncTask;

public class GetDataTask extends AsyncTask<String, Integer, String> {

	private GetDataCallback mCallback = null;
	private int mFlag;

	public GetDataTask(GetDataCallback callback, int flag) {

		mCallback = callback;
		mFlag = flag;
	}

	@Override
	protected String doInBackground(String... params) {

		(mCallback).GetHandle().post(new Runnable() {
			public void run() {
			}
		});
		return getDate(params[0]);
	}
	
	@Override
	protected void onPostExecute(final String result) {

		if (result != null && result.length() > 0) {
			mCallback.AddData(result, mFlag);
		} else {
			mCallback.GetHandle().post(new Runnable() {
				public void run() {

				}
			});
		}
		super.onPostExecute(result);
	}

	private String getDate(String request) {
		String response = null;
//		response = http.HttpPost(url);
		switch(mFlag){
		case Constant.GETRECOMMENDLIST: // 获取推荐轮播列表
			response = 
				"{"+
					"returnCode: '000', "+//返回代码
					"message: '请求成功',"+ //返回信息描述
					"data: {"+
						"adList: "+
						"["+
							"{"+
								"adId: '1',"+ 			//广告ID
								"adType: '0', "+			//广告类型
								"adName: '1', "+			//广告名称
								"adUrl: 'http://58.53.197.136/sp/image/t1.png', "+			//广告图片的URL
								"luckyBeansNum: '10',"+ 	//幸运豆数量
								"targetUrl: '10002156' "+		//广告跳转地址
							"},"+
							"{"+
								"adId: '2',"+ 			//广告ID
								"adType: '0', "+			//广告类型
								"adName: '1', "+			//广告名称
								"adUrl: 'http://58.53.197.136/sp/image/t2.png', "+			//广告图片的URL
								"luckyBeansNum: '10',"+ 	//幸运豆数量
								"targetUrl: '10002156' "+		//广告跳转地址
							"},"+
							"{"+
								"adId: '3',"+ 			//广告ID
								"adType: '0', "+			//广告类型
								"adName: '1', "+			//广告名称
								"adUrl: 'http://58.53.197.136/sp/image/t3.png', "+			//广告图片的URL
								"luckyBeansNum: '10',"+ 	//幸运豆数量
								"targetUrl: '10002156' "+		//广告跳转地址
							"}"+
						"]"+
					"}"+
				"}";
			break;
		case Constant.GETHOTAPPLIST: // 获取热门应用列表
			response = "abc";
			
			
			break;
		default:
			response = "";
			break;
		}
		
		
		return response;
	}
}
