package com.hoyotech.ctgames.util;

import android.os.Environment;

/**
 * 常量
 * Created by GGCoke on 13-12-6.
 */
public class CTGameConstans {
    /* DEBUG模式 */
    public static final boolean DEBUG = true;

    /* 应用版本 */
    public static final String VERSION = "2.0.0.0";

    /* viewpager是否可以滑动 */
    public static final boolean CTGAME_VIEWPAGER_SCROLL_YES = true;
    public static final boolean CTGAME_VIEWPAGER_SCROLL_NO = false;

    /* 精品推荐页面刷新标示 */
    public static final int APP_RECOMMEND_REFRESH = 0;
    /* 精品推荐页面刷新间隔 */
    public static final int APP_RECOMMEND_REFRESH_DURATION = 5000;

    // 测试用apk url
    public static String[] url = {
            "http://img.yingyonghui.com/apk/16457/com.rovio.angrybirdsspace.ads.1332528395706.apk",
            "http://img.yingyonghui.com/apk/15951/com.galapagossoft.trialx2_winter.1328012793227.apk",
            "http://cdn1.down.apk.gfan.com/asdf/Pfiles/2012/3/26/181157_0502c0c3-f9d1-460b-ba1d-a3bad959b1fa.apk",
            "http://static.nduoa.com/apk/258/258681/com.gameloft.android.GAND.GloftAsp6.asphalt6.apk",
            "http://cdn1.down.apk.gfan.com/asdf/Pfiles/2011/12/5/100522_b73bb8d2-2c92-4399-89c7-07a9238392be.apk",
            "http://file.m.163.com/app/free/201106/16/com.gameloft.android.TBFV.GloftGTHP.ML.apk"};

    /* 下载常量 */

    /* SD卡根目录 */
    public static final String STORAGE_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";

    /* 应用根目录 */
    public static final String CTGAME_ROOT = STORAGE_ROOT + "ctgames/";

    /* 下载应用目录 */
    public static final String CTGAME_APP_DOWNLOAD_DIR = CTGAME_ROOT + "apps/";

    /* 应用最小需要空间 */
    public static final int STORAGE_THRESHOLD_LOW = 1024 *1024 *10;

    /* 所有任务最大数，包括，等待下载，正在下载和暂停状态 */
    public static final int MAX_DOWNLOAD_TASK_COUNT = 10;
    /* 同时下载任务最大数 */
    public static final int MAX_DOWNLOADIND_TASK_COUNT = 3;

    /* 连接超时时间 */
    public static final int CONNECTION_TIMEOUT = 5 * 1000;

    /* 下载缓存 8K */
    public static final int DOWNLOAD_BUFFER_SIZE = 1024 * 1024;

    /* 是否只允许3G下载 */
    public static final boolean DOWNLOAD_ONLY_3G_YES = true;
    public static final boolean DOWNLOAD_ONLY_3G_NO = false;

    /* 下载过程出现的异常 */
    public static final int DOWNLOAD_ERROR_NO_NETWORK = 0;
    public static final int DOWNLOAD_ERROR_FILE_ALREADY_EXIST = 1;
    public static final int DOWNLOAD_ERROR_NO_MEMORY = 2;
    public static final int DOWNLOAD_ERROR_CONNECTION_TIMEOUT = 3;
    public static final int DOWNLOAD_ERROR_WITH_WIFI = 4;

    /* 添加下载任务结果 */
    public static final int DOWNLOAD_TASK_ADD_OK = 0;
    public static final int DOWNLOAD_TASK_ADD_NO_SDCARD = 1;
    public static final int DOWNLOAD_TASK_ADD_SDCARD_READONLY = 2;
    public static final int DOWNLOAD_TASK_ADD_TASKQUEUE_FULL = 3;
    public static final int DOWNLOAD_TASK_ADD_OTHER_ERROR = 4;

    /* 请求结果 */
    public static final int RESPONSE_SUCCESS = 0;
    public static final int RESPONSE_FAILED = 1;

    /* 响应返回代码 */
    public static final String RESPONSE_RETURN_CODE_SUCCESS = "000";


    /* 接口请求类型 */
    public static final String REQUEST_TYPE_CHECKUPDATE = "CHECKUPDATE";
    public static final String REQUEST_TYPE_GETCAPTCHA = "GETCAPTCHA";
    public static final String REQUEST_TYPE_SIGNIN = "SIGNIN";
    public static final String REQUEST_TYPE_QUERYUSERINFO = "QUERYUSERINFO";
    public static final String REQUEST_TYPE_GETADINFO = "GETADINFO";
    public static final String REQUEST_TYPE_GETRECOMMENDLIST = "GETRECOMMENDLIST";
    public static final String REQUEST_TYPE_GETHOTAPPLIST = "GETHOTAPPLIST";
    public static final String REQUEST_TYPE_GETCLASSLIST = "GETCLASSLIST";
    public static final String REQUEST_TYPE_GETAPPLISTBYCLASS = "GETAPPLISTBYCLASS";
    public static final String REQUEST_TYPE_GETPACKLIST = "GETPACKLIST";
    public static final String REQUEST_TYPE_GETAPPLISTBYPACK = "GETAPPLISTBYPACK";
    public static final String REQUEST_TYPE_GETDRAWPICLIST = "GETDRAWPICLIST";
    public static final String REQUEST_TYPE_LOTTERY = "LOTTERY";
    public static final String REQUEST_TYPE_DOWNLOADAPP = "DOWNLOADAPP";
    public static final String REQUEST_TYPE_DOWNLOADPACK = "DOWNLOADPACK";
    public static final String REQUEST_TYPE_GETGOODSLIST = "GETGOODSLIST";
    public static final String REQUEST_TYPE_LUCKYBEANEXCHANGE = "LUCKYBEANEXCHANGE";
    public static final String REQUEST_TYPE_GETPRODUCTLIST = "GETPRODUCTLIST";
    public static final String REQUEST_TYPE_ORDERPRODUCT = "ORDERPRODUCT";
    public static final String REQUEST_TYPE_GETMARQUEECONTENT = "GETMARQUEECONTENT";
    public static final String REQUEST_TYPE_FEEDBACK = "FEEDBACK";
    public static final String REQUEST_TYPE_REPLY = "REPLY";
}
