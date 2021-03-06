package com.hoyotech.ctgames.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.PackageDownloadAdapter;
import com.hoyotech.ctgames.adapter.PackageInfoAdapter;
import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.util.CTGameImageLoader;
import com.hoyotech.ctgames.util.Constant;
import com.hoyotech.ctgames.util.DataUtils;
import com.hoyotech.ctgames.util.GetDataCallback;
import com.hoyotech.ctgames.util.GetDataTask;
import com.hoyotech.ctgames.util.NetworkUtils;

/**
 * Created with IntelliJ IDEA. User: Tian Date: 13-12-6 Time: 下午9:39 To change
 * this template use File | Settings | File Templates.
 */
public class PackageDetailActivity extends Activity implements GetDataCallback,OnClickListener {

    private static final String KEY_CONTENT = "PackageDetailActivity:Content";
    private Bundle bundle;


	ListView packagedetaiListView;// 礼包详情列表
	PackageDownloadAdapter adapter;// 礼包详情列表适配器
	TextView PackgeName;//
	TextView PackgeSize;// 礼包大小
	TextView PackgeDice;// 列表描述
	TextView PrizeCount;// 列表抽奖次数
	TextView LuckypeanCount;// 幸运豆数量
	ImageView PackgeImage;// 礼包图片
	
	Handler mHandler;
	String packname;
	String packID;
	String packImageURL;
	Button openDownButton;// 开始下载按钮
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_package_detail);
		
		
	}

	protected void onStart() {
		super.onStart();
		Intent intent=getIntent();
		 packname=intent.getStringExtra("packagename");
		 packID=intent.getStringExtra("packagID");
		 packImageURL=intent.getStringExtra("packimageurl");
		InitControlView();
		SetControlViewClickListener();
		GetDateFormSever();
	}

	/**
	 * 控件初始化
	 */
	public void InitControlView() {
		mHandler = new Handler();
		PackgeName=(TextView) findViewById(R.id.action_bar_title);
		openDownButton = (Button) findViewById(R.id.btn_package_download);
		PackgeImage = (ImageView) findViewById(R.id.image_package);
		packagedetaiListView = (ListView) findViewById(R.id.list_package_app);
		PackgeSize = (TextView) findViewById(R.id.tv_package_size);
		PackgeDice = (TextView) findViewById(R.id.tv_package_summary);
		PrizeCount = (TextView) findViewById(R.id.tv_prize_count);
		LuckypeanCount = (TextView) findViewById(R.id.tv_luckypean_count);
		PackgeName.setText(packname);
		CTGameImageLoader.loadImage(PackageDetailActivity.this, packImageURL, PackgeImage);
	}

	/**
	 * 设置控件监听
	 */
	public void SetControlViewClickListener() {
		openDownButton.setOnClickListener(new ClickListener());
	}

	/**
	 * 从服务器获取json数据
	 */
	public void GetDateFormSever() {
		GetDataTask task = new GetDataTask(PackageDetailActivity.this,
				Constant.GETAPPLISTBYPACK);
		task.execute("");
	}

	private class ClickListener implements OnClickListener {
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.btn_package_download:
				// 此处按钮监听请根据实际情况进行逻辑修改
		
				
				break;
			default:
				break;
			}

		}

	}

	@Override
	public void AddData(String data, int flag) {

		if (data != null) {
			System.out.println("PackageDetailActivity.AddData()" + data);
			//
			try {
				JSONObject jsonObject = new JSONObject(data);
				JSONObject mJsonObject = jsonObject.getJSONObject("data");
				PackgeDice.setText(mJsonObject.getString("description"));
				PackgeSize.setText(mJsonObject.getString("size") + "MB");
				PrizeCount.setText("抽奖机会 "+mJsonObject.getString("lotteryNum"));
				if (null != LuckypeanCount) {
					LuckypeanCount.setText("幸运豆 "+mJsonObject
							.getString("luckyBeansNum"));
				}
				JSONArray mJsonArray = mJsonObject.getJSONArray("appList");
				List<AppInfo> mAppInfos = new ArrayList<AppInfo>();
				for (int i = 0; i < mJsonArray.length(); i++) {
					AppInfo mAppInfo = new AppInfo();
					mAppInfo.setAppId(Long.parseLong(mJsonArray
							.getJSONObject(i).getString("id")));
					mAppInfo.setAppLogoUrl(mJsonArray.getJSONObject(i)
							.getString("logoUrl"));
					mAppInfo.setAppName(mJsonArray.getJSONObject(i).getString(
							"name"));
					mAppInfo.setAppSize(Long.parseLong(mJsonArray
							.getJSONObject(i).getString("size")));
					mAppInfo.setLuckyBeansNum(Integer.parseInt(mJsonArray
							.getJSONObject(i).getString("luckyBeansNum")));
					mAppInfo.setLotteryNum((Integer.parseInt(mJsonArray
							.getJSONObject(i).getString("lotteryNum"))));
					mAppInfo.setAppDesc((mJsonArray.getJSONObject(i)
							.getString("description")));
					mAppInfo.setAppUrl((mJsonArray.getJSONObject(i)
							.getString("appUrl")));
					mAppInfo.setVersion((mJsonArray.getJSONObject(i)
							.getString("version")));
					mAppInfo.setMD5((mJsonArray.getJSONObject(i)
							.getString("MD5")));
					mAppInfo.setAd((mJsonArray.getJSONObject(i)
							.getString("adUrl")));
					// mAppInfo.setAppPicUrls(((mJsonArray.getJSONArray(i))));
					mAppInfos.add(mAppInfo);
				}
				adapter = new PackageDownloadAdapter(mAppInfos, PackageDetailActivity.this);// 初始化大礼包列表适配器
				packagedetaiListView.setAdapter(adapter);
			} catch (JSONException e) {
				System.out.println("JSONException  " + e);
				e.printStackTrace();
			}
		}
	}

	public Handler GetHandle() {
		// TODO Auto-generated method stub
		return mHandler;
	}

	@Override
	public void onClick(View v) {
	Intent intent;
		switch (v.getId()) {
        // 点击actionbar home按钮
        case R.id.action_bar_button_back:
    		getApplicationContext().sendBroadcast(new Intent("finish"));
    		this.finish();
            break;
        // 点击actionbar 任务管理按钮
        case R.id.action_bar_button_task:
            intent = new Intent(this, TaskHomeActivity.class);
            startActivity(intent);
            break;
    }
	}
}