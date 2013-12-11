package com.hoyotech.ctgames.service;

import android.content.Context;
import android.os.Handler;

import com.hoyotech.ctgames.util.CTGameAsyncHttpClient;
import com.hoyotech.ctgames.util.CTGameConstans;
import com.hoyotech.ctgames.util.StorageUtils;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 数据接口类
 * Created by GGCoke on 13-12-11.
 */
public class CTGameRequest {
    /**
     * 检查更新
     *
     * @param context
     * @param handler
     */
    public static void checkUpdate(Context context, final Handler handler) {
        String url = "";
        JSONObject data = new JSONObject();
        sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_CHECKUPDATE, data);
    }

    /**
     * 获取验证码
     *
     * @param context
     * @param handler
     */
    public static void getCaptcha(Context context, final Handler handler) {
        String url = "";
        JSONObject data = new JSONObject();
        sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_GETCAPTCHA, data);
    }

    /**
     * 用户登录
     *
     * @param context
     * @param handler
     * @param captcha
     */
    public static void signin(Context context, final Handler handler, String captcha) {
        String url = "";
        JSONObject data = new JSONObject();
        try {
            data.put("captcha", captcha);
            data.put("IMSI", StorageUtils.getIMSI());
            data.put("phoneType", StorageUtils.getPhoneType());
            sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_SIGNIN, data);
        } catch (JSONException e) {
        }
    }

    /**
     * 获取用户信息
     *
     * @param context
     * @param handler
     * @param isQueryFlow
     */
    public static void queryUserinfo(Context context, final Handler handler, boolean isQueryFlow) {
        String url = "";
        JSONObject data = new JSONObject();
        try {
            data.put("isQueryFlow", isQueryFlow);
            sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_QUERYUSERINFO, data);
        } catch (JSONException e) {
        }
    }

    /**
     * 获取广告位上的广告信息
     *
     * @param context
     * @param handler
     * @param adSpaceCode
     */
    public static void getAdInfo(Context context, final Handler handler, String adSpaceCode) {
        String url = "";
        JSONObject data = new JSONObject();
        try {
            data.put("adSpaceCode", adSpaceCode);
            sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_GETADINFO, data);
        } catch (JSONException e) {
        }
    }

    /**
     * 获取推荐轮播列表
     *
     * @param context
     * @param handler
     * @param adSpaceCode
     */
    public static void getRecommendList(Context context, final Handler handler, String adSpaceCode) {
        String url = "";
        JSONObject data = new JSONObject();
        try {
            data.put("adSpaceCode", adSpaceCode);
            sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_GETRECOMMENDLIST, data);
        } catch (JSONException e) {
        }
    }

    /**
     * 获取热门应用程序列表
     *
     * @param context
     * @param handler
     * @param pageIndex 请求页数
     * @param pageSize  每页显示数
     */
    public static void getHotAppList(Context context, final Handler handler, int pageIndex, int pageSize) {
        String url = "http://115.156.216.80/doa/test.php";
        JSONObject data = new JSONObject();
        try {
            data.put("keyword", "");
            data.put("classId", "");
            data.put("pageIndex", pageIndex);
            data.put("pageSize", pageSize);
            sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_GETHOTAPPLIST, data);
        } catch (JSONException e) {
        }
    }

    /**
     * 获取APP分类列表
     *
     * @param context
     * @param handler
     */
    public static void getClassList(Context context, final Handler handler) {
        String url = "";
        JSONObject data = new JSONObject();
        sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_GETCLASSLIST, data);
    }

    /**
     * 根据类别获取应用程序列表
     *
     * @param context
     * @param handler
     * @param classId   分类ID
     * @param pageIndex 请求页数
     * @param pageSize  每页显示数
     */
    public static void getAppListByClass(Context context, final Handler handler, int classId, int pageIndex, int pageSize) {
        String url = "";
        JSONObject data = new JSONObject();
        try {
            data.put("keyword", "");
            data.put("classId", classId);
            data.put("pageIndex", pageIndex);
            data.put("pageSize", pageSize);
            sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_GETAPPLISTBYCLASS, data);
        } catch (JSONException e) {
        }
    }

    /**
     * 获取大礼包列表
     *
     * @param context
     * @param handler
     */
    public static void getPackList(Context context, final Handler handler) {
        String url = "";
        JSONObject data = new JSONObject();
        sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_GETPACKLIST, data);
    }

    /**
     * 根据礼包获取程序列表
     *
     * @param context
     * @param handler
     * @param packId  礼包ID
     */
    public static void getAppListByPack(Context context, final Handler handler, int packId) {
        String url = "";
        JSONObject data = new JSONObject();
        try {
            data.put("packId", packId);
            sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_GETAPPLISTBYPACK, data);
        } catch (JSONException e) {
        }
    }

    /**
     * 获取大转盘图片列表
     *
     * @param context
     * @param handler
     */
    public static void getDrawPicList(Context context, final Handler handler) {
        String url = "";
        JSONObject data = new JSONObject();
        sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_GETDRAWPICLIST, data);
    }

    /**
     * 发送抽奖请求
     *
     * @param context
     * @param handler
     */
    public static void lottery(Context context, final Handler handler) {
        String url = "";
        JSONObject data = new JSONObject();
        sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_LOTTERY, data);
    }

    /**
     * 下载请求
     * @param context
     * @param handler
     * @param appId 应用ID
     * @param downloadStatus 下载状态（0开始下载;1下载中;2下载完成;3下载出错）
     * @param downloadSize 已下载大小
     */
    public static void downloadApp(Context context, final Handler handler, long appId, int downloadStatus, long downloadSize) {
        String url = "";
        JSONObject data = new JSONObject();
        try {
            data.put("appId", appId);
            data.put("download", downloadStatus);
            data.put("downloadSize", downloadSize);
            sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_DOWNLOADAPP, data);
        } catch (JSONException e) {
        }
    }

    public static void downloadPack(Context context, final Handler handler, int giftPacksId) {
        // TODO 下载大礼包接口
    }

    /**
     * 获取兑换奖品列表
     * @param context
     * @param handler
     */
    public static void getGoodsList(Context context, final Handler handler) {
        String url = "";
        JSONObject data = new JSONObject();
        sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_GETGOODSLIST, data);
    }

    /**
     * 幸运豆兑换
     * @param context
     * @param handler
     * @param goodsId 兑换商品ID
     */
    public static void luckybeanExchange(Context context, final Handler handler, int goodsId) {
        String url = "";
        JSONObject data = new JSONObject();
        try {
            data.put("goodsId", goodsId);
            sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_LUCKYBEANEXCHANGE, data);
        } catch (JSONException e) {
        }
    }

    /**
     * 获取产品列表
     * @param context
     * @param handler
     * @param pageIndex 请求的页数
     * @param pageSize 每页显示数
     */
    public static void getProductList(Context context, final Handler handler, int pageIndex, int pageSize) {
        String url = "";
        JSONObject data = new JSONObject();
        try {
            data.put("keyword", "");
            data.put("classId", "");
            data.put("pageIndex", pageIndex);
            data.put("pageSize", pageSize);
            sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_GETPRODUCTLIST, data);
        } catch (JSONException e) {
        }
    }

    /**
     * 订购产品
     * @param context
     * @param handler
     * @param productId 产品ID
     */
    public static void orderProduct(Context context, final Handler handler, int productId) {
        String url = "";
        JSONObject data = new JSONObject();
        try {
            data.put("productId", productId);
            sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_ORDERPRODUCT, data);
        } catch (JSONException e) {
        }
    }

    /**
     * 获取跑马灯数据
     * @param context
     * @param handler
     * @param marqueeCode
     */
    public static void getMarqueeContent(Context context, final Handler handler, String marqueeCode) {
        String url = "";
        JSONObject data = new JSONObject();
        try {
            data.put("marqueeCode", marqueeCode);
            sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_GETMARQUEECONTENT, data);
        } catch (JSONException e) {
        }
    }

    /**
     * 意见反馈
     * @param context
     * @param handler
     * @param content 意见内容
     */
    public static void feedback(Context context, final Handler handler, String content) {
        String url = "";
        JSONObject data = new JSONObject();
        try {
            data.put("content", content);
            sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_FEEDBACK, data);
        } catch (JSONException e) {
        }
    }

    public static void reply(Context context, final Handler handler) {
        String url = "";
        JSONObject data = new JSONObject();
        sendRequest(context, handler, url, CTGameConstans.REQUEST_TYPE_REPLY, data);
    }

    /**
     * 封装请求进行发送
     *
     * @param context
     * @param handler
     * @param url
     * @param type
     * @param data
     */
    private static void sendRequest(Context context, final Handler handler, String url, String type, JSONObject data) {
        JSONObject request = new JSONObject();
        try {
            request.put("type", type);
            request.put("sessionId", StorageUtils.getSessionID());
            request.put("versionId", CTGameConstans.VERSION);
            request.put("phone", StorageUtils.getUserPhoneNumber());
            request.put("data", data);
            RequestParams params = new RequestParams();
            params.add("request", request.toString());
            CTGameAsyncHttpClient.post(context, url, params, handler);
        } catch (JSONException e) {
        }
    }
}
