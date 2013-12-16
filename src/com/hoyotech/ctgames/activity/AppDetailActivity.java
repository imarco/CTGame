package com.hoyotech.ctgames.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import com.alibaba.fastjson.JSONObject;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.adapter.ImageListAdapter;
import com.hoyotech.ctgames.adapter.holder.TaskDownloadHolder;
import com.hoyotech.ctgames.db.bean.AppInfo;
import com.hoyotech.ctgames.db.dao.AppDao;
import com.hoyotech.ctgames.service.DownloadService;
import com.hoyotech.ctgames.service.DownloadTask;
import com.hoyotech.ctgames.util.*;
import com.hoyotech.ctgames.viewdef.SlideOnePageGallery;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-16
 * Time: 上午4:33
 * To change this template use File | Settings | File Templates.
 */
public class AppDetailActivity extends Activity implements View.OnClickListener {

    private AppInfo info;
    private AppInfo queriedAppInfo;
    private ImageView app_detail_image_app;
    private TextView app_detail_tv_app_name;
    private TextView app_detail_tv_app_version;
    private TextView app_detail_tv_app_size;
    private ImageView app_detail_image_down;
    private ImageView app_detail_image_up;
    private TextView app_detail_tv_summary;
    private SlideOnePageGallery app_detail_img_gallery;
    private TextView app_detail_tv_prize_count;
    private Button app_detail_btn_download;
    private TextView app_detail_tv_luckypean_count;
    private Button app_detail_btn_install;

    private DownloadReceiver mDownloadReceiver;
    private InstallReceiver mInstallReceiver;

    private TextView tvTitle;
    private ImageView ivBack;
    private ImageView ivTask;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);

        Intent intent = getIntent();
        info = (AppInfo) intent.getExtras().getSerializable("appInfo");

        initViews();
        initViewData();
        bindViewEvents();
        setButtonState();

        if (mDownloadReceiver == null) {
            mDownloadReceiver = new DownloadReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(DownloadTask.ACTION_DOWNLOAD);
            registerReceiver(mDownloadReceiver, filter);
        }

        if (mInstallReceiver == null) {
            mInstallReceiver = new InstallReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_PACKAGE_ADDED);
            filter.addAction(Intent.ACTION_PACKAGE_REPLACED);
            filter.addDataScheme("package");    // 这里需要设置scheme，否则接收不到
            registerReceiver(mInstallReceiver, filter);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
        if (mDownloadReceiver != null) {
            unregisterReceiver(mDownloadReceiver);
        }

        if (mInstallReceiver != null) {
            unregisterReceiver(mInstallReceiver);
        }
    }

    // 初始化组件
    private void initViews() {
        tvTitle = (TextView) findViewById(R.id.action_bar_title);
        ivBack = (ImageView) findViewById(R.id.action_bar_button_back);
        ivTask = (ImageView) findViewById(R.id.action_bar_button_task);
        ivBack.setVisibility(View.VISIBLE);
        ivTask.setVisibility(View.VISIBLE);

        app_detail_image_app = (ImageView) findViewById(R.id.app_detail_image_app);
        app_detail_tv_app_name = (TextView) findViewById(R.id.app_detail_tv_app_name);
        app_detail_tv_app_version = (TextView) findViewById(R.id.app_detail_tv_app_version);
        app_detail_tv_app_size = (TextView) findViewById(R.id.app_detail_tv_app_size);
        app_detail_image_down = (ImageView) findViewById(R.id.app_detail_image_down);
        app_detail_image_up = (ImageView) findViewById(R.id.app_detail_image_up);
        app_detail_tv_summary = (TextView) findViewById(R.id.app_detail_tv_summary);
        app_detail_img_gallery = (SlideOnePageGallery) findViewById(R.id.app_detail_img_gallery);
        app_detail_tv_prize_count = (TextView) findViewById(R.id.app_detail_tv_prize_count);
        app_detail_btn_download = (Button) findViewById(R.id.app_detail_btn_download);
        app_detail_tv_luckypean_count = (TextView) findViewById(R.id.app_detail_tv_luckypean_count);
        app_detail_btn_install = (Button) findViewById(R.id.app_detail_btn_install);

    }

    // 给组件添加数据
    private void initViewData() {
        tvTitle.setText(R.string.play_app);
        CTGameImageLoader.loadImage(this, info.getAppLogoUrl(), app_detail_image_app);
        app_detail_tv_app_name.setText(info.getAppName());
        app_detail_tv_app_version.setText("(" + info.getVersion() + ")");
        app_detail_tv_app_size.setText("大小：" + StorageUtils.getSizeFormatted(info.getAppSize()));
        app_detail_tv_summary.setVisibility(View.VISIBLE);
        app_detail_tv_summary.setText(info.getAppDesc());
        app_detail_image_down.setVisibility(View.GONE);
        app_detail_image_up.setVisibility(View.VISIBLE);
        app_detail_tv_prize_count.setText(" 抽奖机会" + info.getLotteryNum() + "次");
        app_detail_tv_luckypean_count.setText(" 幸运豆" + info.getLuckyBeansNum());

        // 设置Gallary
        app_detail_img_gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });


        app_detail_img_gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(context, Integer.toString(position), Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        ImageListAdapter adapter = new ImageListAdapter(getApplicationContext());
        List<Integer> idList = new ArrayList<Integer>();
        idList.add(R.drawable.app_detail_image_sample1);
        idList.add(R.drawable.app_detail_image_sample2);
        idList.add(R.drawable.app_detail_image_sample3);
        adapter.setImageList(idList);
        app_detail_img_gallery.setAdapter(adapter);

    }

    // 绑定事件
    private void bindViewEvents() {
        ivBack.setOnClickListener(this);
        ivTask.setOnClickListener(this);
        app_detail_btn_download.setOnClickListener(this);
        app_detail_btn_install.setOnClickListener(this);
        app_detail_image_down.setOnClickListener(this);
        app_detail_image_up.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_bar_button_back:
                finish();
                break;
            case R.id.action_bar_button_task:
                Intent i = new Intent(this, TaskHomeActivity.class);
                startActivity(i);
                break;

            case R.id.app_detail_image_down:
                app_detail_tv_summary.setVisibility(View.VISIBLE);
                app_detail_tv_summary.setText(info.getAppDesc());
                app_detail_image_down.setVisibility(View.GONE);
                app_detail_image_up.setVisibility(View.VISIBLE);

                break;
            case R.id.app_detail_image_up:

                app_detail_tv_summary.setVisibility(View.GONE);
                app_detail_image_down.setVisibility(View.VISIBLE);
                app_detail_image_up.setVisibility(View.GONE);
                break;

            case R.id.app_detail_btn_download:
                if(app_detail_btn_download.isEnabled()) {
                    // 判断网络情况，如果不是3G则不允许下载
                    if (!NetworkUtils.isNetworkAvailable(this)) {
                        Toast.makeText(this, R.string.network_no_network, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!NetworkUtils.is3GNetwork(this)) {

                        new AlertDialog.Builder(this).setMessage(R.string.network_open_3g).setPositiveButton(R.string.network_open_3g_confirm,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 打开3G网络并关闭WIFI
                                        NetworkUtils.set3GNetworkStatus(AppDetailActivity.this, true);
                                        NetworkUtils.setWifiStatus(AppDetailActivity.this, false);
                                        info.setState(TaskState.STATE_DOWNLOADING);
                                        app_detail_btn_download.setEnabled(false);
                                        app_detail_btn_download.setBackground(getResources().getDrawable(R.drawable.button_grey));
                                        app_detail_btn_install.setEnabled(false);
                                        app_detail_btn_install.setBackground(getResources().getDrawable(R.drawable.button_grey));

                                        // 通知service开始下载
                                        Intent downloadIntet = new Intent(DownloadService.DOWNLOAD_SERVICE_NAME);
                                        downloadIntet.putExtra(TaskState.DOWNLOAD_STATE, TaskState.STATE_DOWNLOADING);
                                        downloadIntet.putExtra(TaskState.DOWNLOAD_URL, info.getAppUrl());
                                        downloadIntet.putExtra("action", DownloadTask.ACTION_DOWNLOAD);
                                        AppDetailActivity.this.startService(downloadIntet);

                                        // 将下载任务放入数据库备用
                                        AppDao appDao = new AppDao(AppDetailActivity.this);
                                        appDao.addApp(info);
                                    }
                                }).setNegativeButton(R.string.network_open_3g_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                    } else {
                        info.setState(TaskState.STATE_DOWNLOADING);
                        app_detail_btn_download.setEnabled(false);
                        app_detail_btn_download.setBackground(getResources().getDrawable(R.drawable.button_grey));
                        app_detail_btn_install.setEnabled(false);
                        app_detail_btn_install.setBackground(getResources().getDrawable(R.drawable.button_grey));

                        // 通知service开始下载
                        Intent downloadIntet = new Intent(DownloadService.DOWNLOAD_SERVICE_NAME);
                        downloadIntet.putExtra(TaskState.DOWNLOAD_STATE, TaskState.STATE_DOWNLOADING);
                        downloadIntet.putExtra(TaskState.DOWNLOAD_URL, info.getAppUrl());
                        downloadIntet.putExtra("action", DownloadTask.ACTION_DOWNLOAD);
                        AppDetailActivity.this.startService(downloadIntet);

                        // 将下载任务放入数据库备用
                        AppDao appDao = new AppDao(AppDetailActivity.this);
                        appDao.addApp(info);
                    }
                }
                break;
            case R.id.app_detail_btn_install:
                if(app_detail_btn_install.isEnabled()) {
                    info.setState(TaskState.STATE_INSTALLING);
                    // 数据库更新应用状态
                    AppDao appDao = new AppDao(this);
                    ContentValues values = new ContentValues();
                    values.put(AppInfo.APPINFO_STATE, TaskState.STATE_INSTALLED);
                    appDao.updateApp(values, AppInfo.APPINFO_APPURL + "=?", new String[] {info.getAppUrl()});

                    // 安装应用，安装成功后再向服务器发送安装成功的请求
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        String fileName = new File(new URL(info.getAppUrl()).getFile()).getName();
                        Uri uri = Uri.fromFile(new File(CTGameConstans.CTGAME_APP_DOWNLOAD_DIR + fileName));
                        intent.setDataAndType(uri,"application/vnd.android.package-archive");
                        startActivity(intent);
                    } catch (MalformedURLException e) {
                    }
                }

                break;
            default:
                break;
        }
    }


    private void setButtonState() {

        AppDao dao = new AppDao(this);
        queriedAppInfo = dao.queryAppByUrl(info.getAppUrl());

        if(queriedAppInfo != null) {

            // 下载中或者暂停时，下载按钮灰掉
            if(queriedAppInfo.getState() == TaskState.STATE_DOWNLOADING || queriedAppInfo.getState() == TaskState.STATE_PAUSED) {
                setDownloadButtonEnabledOrNot(false);
                setInstallButtonEnabledOrNot(false);
            }

            // 下载完成一个状态
            if(queriedAppInfo.getState() == TaskState.STATE_COMPLETE) {
                setDownloadButtonEnabledOrNot(true);
                setInstallButtonEnabledOrNot(true);
            }

            // 完成安装一个状态
            if(queriedAppInfo.getState() == TaskState.STATE_INSTALLED) {
                setDownloadButtonEnabledOrNot(true);
                setInstallButtonEnabledOrNot(false);
            }

            // 任务完成一个状态
            if(queriedAppInfo.getState() == TaskState.STATE_TASK_COMPLETE) {
                setDownloadButtonEnabledOrNot(true);
                setInstallButtonEnabledOrNot(false);
            }

            // 如果曾经下载过，就要改文字
            setDownloadAgainOrNot(queriedAppInfo.isHasDownloaded());

        } else {
            setDownloadButtonEnabledOrNot(true);
            setInstallButtonEnabledOrNot(false);
            setDownloadAgainOrNot(false);
        }

    }


    ///////以下代码全部是下载的按钮的状态变化
    public void setDownloadAgainOrNot(boolean isAgain) {
        String text = (isAgain) ? "再次下载" : "下载";
        app_detail_btn_download.setText(text);
    }

    /**
     * 设置下载按钮是否有效
     * @param enabled 是否有效
     */
    public void setDownloadButtonEnabledOrNot(boolean enabled) {
        if(enabled) {
            app_detail_btn_download.setEnabled(true);
            if(queriedAppInfo != null) {
                TaskState.setButtonView(queriedAppInfo.getState(), this, app_detail_btn_download);
            } else {
                TaskState.setButtonView(TaskState.STATE_PREPARE, this, app_detail_btn_download);
            }

        } else {
            app_detail_btn_download.setEnabled(false);
            app_detail_btn_download.setBackground(getResources().getDrawable(R.drawable.button_grey));
        }


    }

    /**
     * 设置安装按钮是否有效
     * @param enabled 是否有效
     */
    public void setInstallButtonEnabledOrNot(boolean enabled) {
        if(enabled) {
            app_detail_btn_install.setEnabled(true);
            if(queriedAppInfo != null) {
                TaskState.setButtonView(queriedAppInfo.getState(), this, app_detail_btn_install);
            } else {
                app_detail_btn_install.setBackground(getResources().getDrawable(R.drawable.button_grey));
            }

        } else {
            app_detail_btn_install.setEnabled(false);
            app_detail_btn_install.setBackground(getResources().getDrawable(R.drawable.button_grey));
        }


    }


    private class DownloadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent downloadIntent) {

            handleIntent(downloadIntent);

        }

        private void handleIntent(Intent downloadIntent) {

            if (downloadIntent != null && downloadIntent.getAction().equals(DownloadTask.ACTION_DOWNLOAD)) {
                int state = downloadIntent.getIntExtra(TaskState.DOWNLOAD_STATE, -1);
                String url = downloadIntent.getStringExtra(TaskState.DOWNLOAD_URL);

                switch (state) {
                    case TaskState.STATE_PREPARE:
                        // 不存在的状态
                        break;
                    case TaskState.STATE_DOWNLOADING:
                        if (info.getAppUrl().equals(url)) {

                        }
                        break;
                    case TaskState.STATE_COMPLETE:
                        if (info.getAppUrl().equals(url)) {
                            app_detail_btn_download.setEnabled(true);
                            app_detail_btn_download.setText("再次下载");
                            app_detail_btn_download.setBackground(getResources().getDrawable(R.drawable.button_orange));
                            app_detail_btn_install.setEnabled(true);
                            app_detail_btn_install.setBackground(getResources().getDrawable(R.drawable.button_green));
                        }
                        break;
                    case TaskState.STATE_STOP:
                        // 不存在的状态
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
                // 设置安装按钮不能点击
                app_detail_btn_install.setEnabled(false);
                app_detail_btn_install.setBackground(getResources().getDrawable(R.drawable.button_grey));
                String packageName = intent.getData().getSchemeSpecificPart();
                try {
                    String fileName = new File(new URL(info.getAppUrl()).getFile()).getName();
                    String apkName = CTGameConstans.CTGAME_APP_DOWNLOAD_DIR + fileName;
                    PackageManager pm = context.getPackageManager();
                    PackageInfo packageInfo = pm.getPackageArchiveInfo(apkName, PackageManager.GET_ACTIVITIES);
                    if (packageName.equals(packageInfo.packageName)) {

                        // TODO 向服务器发送安装应用请求，由服务器判断是否是第一次安装
                        JSONObject request = new JSONObject();
                        JSONObject d = new JSONObject();
                        d.put("id", info.getAppId());
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

                        // 打开应用
                        // 数据库更新应用状态
                        AppDao dao = new AppDao(context);
                        ContentValues cv = new ContentValues();
                        cv.put(AppInfo.APPINFO_STATE, TaskState.STATE_OPENED);
                        dao.updateApp(cv, AppInfo.APPINFO_APPURL + "=?", new String[] {info.getAppUrl()});
                    }
                } catch (MalformedURLException e) {
                }
            }
        }
    }
}