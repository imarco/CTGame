package com.hoyotech.ctgames.util;

public class Constant {
	
	/*接口类型*/
	public static final int CHECKUPDATE 		= 11; // 检查更新
	public static final int GETCAPTCHA 			= 12; // 获取验证码
	public static final int SIGNIN 				= 13; // 登录
	public static final int GETUSERINFO 		= 14; // 获取用户信息
	public static final int GETADINFO 			= 15; // 获取广告位上的广告信息
	public static final int GETRECOMMENDLIST 	= 16; // 获取推荐轮播列表
	public static final int GETHOTAPPLIST 		= 17; // 获取热门应用列表
	public static final int GETCLASSLIST 		= 18; // 获取分类列表
	public static final int GETAPPLISTBYCLASS 	= 19; // 根据类别获取应用列表
	public static final int GETPACKLIST 		= 20; // 获取大礼包列表
	public static final int GETAPPLISTBYPACK 	= 21; // 根据礼包获取应用列表
	public static final int GETDRAWPICLIST 		= 22; // 获取转盘上的奖品列表
	public static final int LOTTERY 			= 23; // 抽奖
	public static final int DOWNLOADAPP 		= 24; // 下载应用
	public static final int DOWNLOADPACK		= 25; // 下载大礼包
	public static final int GETGOODSLIST 		= 26; // 获取可兑换的商品列表
	public static final int LUCKYBEANEXCHANGE 	= 27; // 积分兑换
	public static final int GETPRODUCTLIST 		= 28; // 获取可订购的产品列表
	public static final int ORDERPRODUCT 		= 29; // 订购产品
	public static final int GETMARQUEECONTENT 	= 30; // 获取跑马灯内容
	public static final int FEEDBACK 			= 31; // 意见反馈
	public static final int REPLY 				= 32; // 获取反馈回复的列表

    /* 安装应用请求类型，0表示下载，1表示下载中，2表示下载完成，3表示下载错误，4表示安装，5表示打开，6表示领取奖励 */
    public static final int REQUEST_APP_TYPE_START_DOWNLOAD = 0;
    public static final int REQUEST_APP_TYPE_DOWNLOADING = 1;
    public static final int REQUEST_APP_TYPE_DOWNLOAD_COMPLETED = 2;
    public static final int REQUEST_APP_TYPE_DOWNLOAD_ERROR = 3;
    public static final int REQUEST_APP_TYPE_INSTALL = 4;
    public static final int REQUEST_APP_TYPE_OPEN = 5;
    public static final int REQUEST_APP_TYPE_GET_PRIZE = 6;
}
