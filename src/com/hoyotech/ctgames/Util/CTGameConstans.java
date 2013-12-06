package com.hoyotech.ctgames.util;

import android.os.Environment;

/**
 * 常量
 * Created by GGCoke on 13-12-6.
 */
public class CTGameConstans {
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

    /* SD卡根目录 */
    public static final String STORAGE_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
    /* 应用根目录 */
    public static final String CTGAME_ROOT = STORAGE_ROOT + "ctgames/";
    /* 应用最小需要空间 */
    public static final int STORAGE_THRESHOLD_LOW = 1024 *1024 *10;

}
