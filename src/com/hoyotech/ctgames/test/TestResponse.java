package com.hoyotech.ctgames.test;

/**
 * 测试类
 * 模拟服务器端返回的数据，待删
 * @author Hzibo
 *
 */
public class TestResponse {

	public static String GETRECOMMENDLIST = 
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
	
	public static String GETHOTAPPLIST = "abc";
}
