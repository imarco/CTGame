package com.hoyotech.ctgames.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.protocol.HTTP;

public class http {

	public static String HttpPost(String inputParam) {
		String strResponse = null;
		try {
			URL url = new URL("");
			try {
				HttpURLConnection Connection = (HttpURLConnection) url.openConnection();
				Connection.setFollowRedirects(true);
				Connection.setDoOutput(true);
				Connection.setDoInput(true);
				Connection.setUseCaches(false);
				Connection.setRequestMethod("POST");
				Connection.setRequestProperty("Content-Type", "text/xml");
				Connection.connect();

				if (inputParam != null) {
					OutputStream os = Connection.getOutputStream();
					os.write(inputParam.getBytes(HTTP.UTF_8));
					os.flush();
					os.close();
				}
				InputStream content = Connection.getInputStream();
				try {
					strResponse = new String(StreamsUtils.readByteArrayFromStream(content), HTTP.UTF_8);
				} catch (Exception e) {
					System.out.println("http.HttpPost()" + e.toString());
					e.printStackTrace();
				}
			} catch (IOException e) {
				System.out.println("http.HttpPost()2" + e.toString());
				e.printStackTrace();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return strResponse;
	}

	public static void sse() {

		System.out.println("begin send");

		String sessionid = "05095F523D89EA4C4B87AC59FD916173";

		String inputParam = "{\"body\":{\"mobile\":\"13397182355\",\"imei\":\"1234567890\"},\"head\":{\"devid\":\"\",\"plat\":\"1\",\"serialno\":\"1376279019092\",\"tradeType\":\"LOGIN\",\"ua\":\"U8860\",\"version\":\"0.0.1\"}}";
		// String inputParam =
		// "{\"body\":{\"mobile\":\"13397182355\",\"imei\":\"1234567890\",\"randCode\":\"487875\"},\"head\":{\"devid\":\"\",\"plat\":\"1\",\"serialno\":\"1376279019092\",\"tradeType\":\"LOGIN\",\"ua\":\"U8860\",\"version\":\"0.0.1\"}}";
		// String inputParam =
		// "{\"body\":{\"eggType\":\"1\"},\"head\":{\"devid\":\"\",\"plat\":\"1\",\"serialno\":\"1376279019092\",\"tradeType\":\"BREAKEGG\",\"ua\":\"U8860\",\"version\":\"0.0.1\",\"sessionid\":\""
		// + sessionid + "\"}}";
		// String inputParam =
		// "{\"body\":{\"queryFlow\":\"2\"},\"head\":{\"devid\":\"\",\"plat\":\"1\",\"serialno\":\"1376279019092\",\"tradeType\":\"GETUSERINFO\",\"ua\":\"U8860\",\"version\":\"0.0.1\",\"sessionid\":\""
		// + sessionid + "\"}}";
		// String inputParam =
		// "{\"body\":{\"queryFlow\":\"2\"},\"head\":{\"devid\":\"\",\"plat\":\"1\",\"serialno\":\"1376279019092\",\"tradeType\":\"DOWNLOADOVER\",\"ua\":\"U8860\",\"version\":\"0.0.1\",\"sessionid\":\""
		// + sessionid + "\"}}";
		// String inputParam =
		// "{\"body\":{\"keyword\":\"\",\"pageIndex\":\"1\",\"pageSize\":\"10\"},\"head\":{\"devid\":\"\",\"plat\":\"1\",\"serialno\":\"1376279019092\",\"tradeType\":\"QUERYPRIZELIST\",\"ua\":\"U8860\",\"version\":\"0.0.1\",\"sessionid\":\""
		// + sessionid +"\"}}";

		URL url = null;
		HttpURLConnection httpConn = null;
		OutputStream output = null;
		OutputStreamWriter outr = null;
		try {
			url = new URL("http://game.hb10008.cn:8080/server/inter!unityAndroid.do");

			httpConn = (HttpURLConnection) url.openConnection();
			HttpURLConnection.setFollowRedirects(true);
			httpConn.setDoOutput(true);
			httpConn.setRequestMethod("POST");
			httpConn.setRequestProperty("Content-Type", "text/html");
			httpConn.setRequestProperty("contentType", "utf-8");
			httpConn.connect();
			output = httpConn.getOutputStream();
			outr = new OutputStreamWriter(output, "utf-8");

			outr.write(inputParam.toString().toCharArray(), 0, inputParam.toString().length());
			outr.flush();
			outr.close();

			System.out.println("send ok");
			int code = httpConn.getResponseCode();
			System.out.println("code " + code);
			System.out.println(httpConn.getResponseMessage());

			String sCurrentLine = "";
			String sTotalString = "";
			if (code == 200) {
				java.io.InputStream is = httpConn.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				while ((reader.readLine()) != null)
					if (sCurrentLine.length() > 0)
						sTotalString = sTotalString + sCurrentLine.trim();
				System.out.println("response2-----:" + sTotalString);

			} else {
				sTotalString = "Զ�̷���������ʧ��,�������:" + code;

			}
			System.out.println("response:" + sTotalString);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
