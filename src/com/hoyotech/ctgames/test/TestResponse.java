package com.hoyotech.ctgames.test;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

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

public static String GETAPPLISTBYPACK = "{" + "returnCode: '000', " + // 返回代码
		"message: '请求成功'," + // 返回信息描述
		"data: {" + 
		"id: '1'," + // 大礼包ID
		"name: 'aaa', " + // 礼包名称
		"picUrl: 'http://58.53.197.136/sp/image/t1.png', " + // 大礼包图片地址
		"size: '15', " + // 礼包大小
		"luckyBeansNum: '1', " + // 下载可获得幸运豆
		"lotteryNum: '1', " + // 下载可获得抽奖次数
		"description: '9999999999'," + // 大礼包描述
		
		"appList: " + "[" + "{" +
		
		"id: '0', " + // id
		"logoUrl: 'http://58.53.197.136/sp/image/t3.png', " + // 应用logo地址
		"name: '3333', " + // 应用名称
		"size: '10'," + // 应用大小
		"luckyBeansNum: '1', " + // 下载可获幸运豆
		"lotteryNum: '1' ," + // 下载可获抽奖次数
		"description: 'ffffff' ," + // 应用描述
		"appUrl: '' ," + // 应用下载地址
		"version: '1', " + // 应用版本信息
		"MD5: '', " + // md5数值
		"adUrl: '', " + // 最后一张截图的广告
		"picUrls: []" + // 截图地址

		"}," + "{" + 
		"id: '0', " + // id
		"logoUrl: 'http://58.53.197.136/sp/image/t3.png', " + // 应用logo地址
		"name: '3333', " + // 应用名称
		"size: '10'," + // 应用大小
		"luckyBeansNum: '1', " + // 下载可获幸运豆
		"lotteryNum: '1' ," + // 下载可获抽奖次数
		"description: 'ffffff' ," + // 应用描述
		"appUrl: '' ," + // 应用下载地址
		"version: '1', " + // 应用版本信息
		"MD5: '', " + // md5数值
		"adUrl: '', " + // 最后一张截图的广告
		"picUrls: []" + // 截图地址

		"}," +

		"{" + 
		"id: '0', " + // id
		"logoUrl: 'http://58.53.197.136/sp/image/t3.png', " + // 应用logo地址
		"name: '3333', " + // 应用名称
		"size: '10'," + // 应用大小
		"luckyBeansNum: '1', " + // 下载可获幸运豆
		"lotteryNum: '1' ," + // 下载可获抽奖次数
		"description: 'ffffff' ," + // 应用描述
		"appUrl: '' ," + // 应用下载地址
		"version: '1', " + // 应用版本信息
		"MD5: '', " + // md5数值
		"adUrl: '', " + // 最后一张截图的广告
		"picUrls: []" + // 截图地址

		"}," + "{" + 
		"id: '0', " + // id
		"logoUrl: 'http://58.53.197.136/sp/image/t3.png', " + // 应用logo地址
		"name: '3333', " + // 应用名称
		"size: '10'," + // 应用大小
		"luckyBeansNum: '1', " + // 下载可获幸运豆
		"lotteryNum: '1' ," + // 下载可获抽奖次数
		"description: 'ffffff' ," + // 应用描述
		"appUrl: '' ," + // 应用下载地址
		"version: '1', " + // 应用版本信息
		"MD5: '', " + // md5数值
		"adUrl: '', " + // 最后一张截图的广告
		"picUrls: []" + // 截图地址

		"}," + "{" + 
		"id: '0', " + // id
		"logoUrl: 'http://58.53.197.136/sp/image/t3.png', " + // 应用logo地址
		"name: '3333', " + // 应用名称
		"size: '10'," + // 应用大小
		"luckyBeansNum: '1', " + // 下载可获幸运豆
		"lotteryNum: '1' ," + // 下载可获抽奖次数
		"description: 'ffffff' ," + // 应用描述
		"appUrl: '' ," + // 应用下载地址
		"version: '1', " + // 应用版本信息
		"MD5: '', " + // md5数值
		"adUrl: '', " + // 最后一张截图的广告
		"picUrls: []" + // 截图地址

		"}," + "{" + 
		"id: '0', " + // id
		"logoUrl: 'http://58.53.197.136/sp/image/t3.png', " + // 应用logo地址
		"name: '3333', " + // 应用名称
		"size: '10'," + // 应用大小
		"luckyBeansNum: '1', " + // 下载可获幸运豆
		"lotteryNum: '1' ," + // 下载可获抽奖次数
		"description: 'ffffff' ," + // 应用描述
		"appUrl: '' ," + // 应用下载地址
		"version: '1', " + // 应用版本信息
		"MD5: '', " + // md5数值
		"adUrl: '', " + // 最后一张截图的广告
		"picUrls: []" + // 截图地址

		"}," + "{" + 
		"id: '0', " + // id
		"logoUrl: 'http://58.53.197.136/sp/image/t3.png', " + // 应用logo地址
		"name: '3333', " + // 应用名称
		"size: '10'," + // 应用大小
		"luckyBeansNum: '1', " + // 下载可获幸运豆
		"lotteryNum: '1' ," + // 下载可获抽奖次数
		"description: 'ffffff' ," + // 应用描述
		"appUrl: '' ," + // 应用下载地址
		"version: '1', " + // 应用版本信息
		"MD5: '', " + // md5数值
		"adUrl: '', " + // 最后一张截图的广告
		"picUrls: []" + // 截图地址

		"}," + "{" + 
		"id: '0', " + // id
		"logoUrl: 'http://58.53.197.136/sp/image/t3.png', " + // 应用logo地址
		"name: '3333', " + // 应用名称
		"size: '10'," + // 应用大小
		"luckyBeansNum: '1', " + // 下载可获幸运豆
		"lotteryNum: '1' ," + // 下载可获抽奖次数
		"description: 'ffffff' ," + // 应用描述
		"appUrl: '' ," + // 应用下载地址
		"version: '1', " + // 应用版本信息
		"MD5: '', " + // md5数值
		"adUrl: '', " + // 最后一张截图的广告
		"picUrls: []" + // 截图地址

		"}," + "{" + 
		"id: '0', " + // id
		"logoUrl: 'http://58.53.197.136/sp/image/t3.png', " + // 应用logo地址
		"name: '3333', " + // 应用名称
		"size: '10'," + // 应用大小
		"luckyBeansNum: '1', " + // 下载可获幸运豆
		"lotteryNum: '1' ," + // 下载可获抽奖次数
		"description: 'ffffff' ," + // 应用描述
		"appUrl: '' ," + // 应用下载地址
		"version: '1', " + // 应用版本信息
		"MD5: '', " + // md5数值
		"adUrl: '', " + // 最后一张截图的广告
		"picUrls: []" + // 截图地址

		"}," + "{" + 
		
		"id: '0', " + // id
		"logoUrl: 'http://58.53.197.136/sp/image/t3.png', " + // 应用logo地址
		"name: '', " + // 应用名称
		"size: '10'," + // 应用大小
		"luckyBeansNum: '1', " + // 下载可获幸运豆
		"lotteryNum: '1' ," + // 下载可获抽奖次数
		"description: 'ffffff' ," + // 应用描述
		"appUrl: '' ," + // 应用下载地址
		"version: '1', " + // 应用版本信息
		"MD5: '', " + // md5数值
		"adUrl: '', " + // 最后一张截图的广告
		"picUrls: []" + // 截图地址

		"}" + "]" + "}" + "}";;
		
    public static String getHotAppList() {
        JSONObject result = new JSONObject();
        try {
            JSONArray imgs = new JSONArray();
            imgs.add("http://appimg1.3g.qq.com/android_new/852/792852/16912286/icon_72.png");
            imgs.add("http://appimg1.3g.qq.com/android_new/140/1064140/16659626/icon_72.png");
            imgs.add("http://appimg1.3g.qq.com/android_new/203/563203/16939039/icon_72.png");

            JSONArray appList = new JSONArray();
            JSONObject app1 = new JSONObject();
            app1.put("appId", 1);
            app1.put("appLogoUrl", "http://appimg1.3g.qq.com/android_new/852/792852/16912286/icon_72.png");
            app1.put("appName","海豚浏览器");
            app1.put("appSize",13830717);
            app1.put("luckyBeansNum",10);
            app1.put("lotteryNum",2);
            app1.put("appDesc","谢谢大家的支持，使海豚浏览器从2009年末登录Android操作系统就一直受到大家的关注和喜爱海豚浏览器炫风版带来了全新界面，6大类多达205项功能优化，使你的智能手机有更卓越的上网体验 我们将桌面体验引入浏览器，网页或功能以图标形式展现，你可以随心拖拽图标，或者将图标累积成文件夹管理；我们引入了百宝箱，给你推荐各种有趣好玩的Web APP；我们进行了社交深度整合，将媒体属性和社交属性分离，我的海豚使您好友的状态不至于淹没在信息海洋中，微博新闻为你提供一个专属的无广告资讯频道；我们改进了浏览基础体验，启动速度加快，使你秒开浏览器，优化内存占用，更提供了多种贴心实用的小工具；我们优化了浏览器内核，游戏模式让你享受流畅网页游戏体验，夜间模式更柔和不刺眼我们的口号是没有白屏！；20项功能优化，你动心了么？ 更多细节改善在微博发布海豚热切希望大家能喜欢，也热切盼望大家能给提出更多建议好让海豚有更多改进和创新");
            app1.put("appUrl","http://122.193.23.63/down.myapp.com/android_new/852/792852/16912286/com.dolphin.browser.xf_133.apk");
            app1.put("version","10.4.0");
            app1.put("MD5","aaaaaaaaaaaaaaaa");
            app1.put("ad","");
            app1.put("appPicUrls",imgs);

            JSONObject app2 = new JSONObject();
            app2.put("appId", 2);
            app2.put("appLogoUrl", "http://appimg1.3g.qq.com/android_new/140/1064140/16659626/icon_72.png");
            app2.put("appName","地铁跑酷");
            app2.put("appSize",23152558);
            app2.put("luckyBeansNum",256);
            app2.put("lotteryNum",5);
            app2.put("appDesc","地铁跑酷（Subway Surfers）是一款冒险类游戏，游戏的背景设定在地铁铁轨上，玩家要帮助Jake和淘气的小伙伴们躲避警察的追捕，同时往来的地铁给游戏增加了挑战难度。游戏的画面可爱精致，是一部不错的作品。应用特色：竭尽全力冲刺！躲避迎面而来的火车！帮助高级、技巧娴熟以及毫无经验的新玩家逃避性情乖戾的检查员及其斗牛犬的检查。与冷静稳健的队友一起打造火车！装饰火车并绘制生动有趣的高清图像！悬浮滑板冲浪！装饰已启动的jetpack！闪电般快速猛击特技！挑战并帮助您的队友！ ");
            app2.put("appUrl","http://119.188.94.55/down.myapp.com/android_new/140/1064140/16659626/com.kiloo.subwaysurf_46.apk");
            app2.put("version","2.11.0");
            app2.put("MD5","bbbbbbbbbbbbbbb");
            app2.put("ad","");
            app2.put("appPicUrls",imgs);

            JSONObject app3 = new JSONObject();
            app3.put("appId", 3);
            app3.put("appLogoUrl", "http://appimg1.3g.qq.com/android_new/203/563203/16939039/icon_72.png");
            app3.put("appName","唱吧-你的手机KTV");
            app3.put("appSize",15162408);
            app3.put("luckyBeansNum",200);
            app3.put("lotteryNum",14);
            app3.put("appDesc","唱吧 - 最时尚且K歌效果最好的手机KTV 漂亮MM们最喜欢的K歌软件,安卓K歌神器 安装后手机立刻变成KTV,自带回音和混响效果 特性 放在口袋里的KTV -打开就能唱，附带上千首经典曲目 -你随时可以将你唱的歌保存起来 -出色的字幕效果，媲美KTV -支持分类点歌、歌星点歌等多种点歌方式 人气最高的K歌社区 -上传你的作品到唱吧里，让成千上万的人听到 -只有在这，你才能体会到数万人听你唱歌，送花，写评论的感觉 -不经意间，你可能就会成为有数万人关注的明星 发现每个城市的好声音 -完全自动按照城市分区，来看看国内不同地区的唱歌风格吧 -你可以进入任何一个区域，成为那里的明星 -你亦可以听听你家乡的人现在在唱什么歌 -你甚至可以到各个城市巡回演出 聪明好用的调音助手 -这里没有复杂的设置项，完全自动美化你的声音 -你也可以经过几次简单的点击，获得更动听的声音 -预置萌猫、娃娃音等多种有趣的声音特效等你来体验 全面的分享支持 -你可以在唱吧里唱首歌然后用微信发你的朋友们 -也可以直接分享给QQ空间、微博、人人网的好友们 合唱功能 -神奇的合唱功能加入，让你尽享一起唱歌的乐趣");
            app3.put("appUrl","http://60.210.9.55/down.myapp.com/android_new/203/563203/16939039/950125F287157C8078D1F0946D0CB080.apk");
            app3.put("version","4.6.6");
            app3.put("MD5","aaaaaaaaaaaaaaaa");
            app3.put("ad","");
            app3.put("appPicUrls",imgs);
            appList.add(app1);
            appList.add(app2);
            appList.add(app3);

            JSONObject data = new JSONObject();
            data.put("appList", appList);

            result.put("returnCode", "000");
            result.put("message", "请求成功");
            result.put("data", data);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    // 获取礼包列表
    public static String getPackList() {

        JSONObject result = new JSONObject();

        try {

            JSONArray packageList = new JSONArray();
            JSONObject package1 = new JSONObject();
            package1.put("id", 1);
            package1.put("name", "塔防游戏礼包");
            package1.put("picUrl","http://www.kmgtp.org/tianyutest/package_image_sample1.png");
            package1.put("description","好玩的");
            package1.put("size",104857600L);
            package1.put("luckyBeansNum",10);
            package1.put("lotteryNum",5);


            JSONObject package2 = new JSONObject();
            package2.put("id", 2);
            package2.put("name", "儿童游戏礼包");
            package2.put("picUrl","http://www.kmgtp.org/tianyutest/package_image_sample2.png");
            package2.put("description","仍然好玩");
            package2.put("size",314572800L);
            package2.put("luckyBeansNum",20);
            package2.put("lotteryNum",10);

            JSONObject package3 = new JSONObject();
            package3.put("id", 3);
            package3.put("name", "经典游戏礼包");
            package3.put("picUrl","http://www.kmgtp.org/tianyutest/package_image_sample3.png");
            package3.put("description","持续好玩");
            package3.put("size",314572800L);
            package3.put("luckyBeansNum",30);
            package3.put("lotteryNum",15);


            packageList.add(package1);
            packageList.add(package2);
            packageList.add(package3);

            JSONObject data = new JSONObject();
            data.put("packList", packageList);

            result.put("returnCode", "000");
            result.put("message", "请求成功");
            result.put("data", data);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result.toString();

    }
}
