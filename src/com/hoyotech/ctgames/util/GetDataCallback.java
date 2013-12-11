package com.hoyotech.ctgames.util;

import android.os.Handler;

public interface GetDataCallback {

	void AddData(String data, int flag);

	Handler GetHandle();
}
