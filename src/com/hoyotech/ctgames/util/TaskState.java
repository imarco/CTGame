package com.hoyotech.ctgames.util;

import android.content.Context;
import android.widget.Button;
import com.hoyotech.ctgames.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-6
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 */
public class TaskState {

    public static final String DOWNLOAD_STATE = "state";
    public static final String DOWNLOAD_SPEED = "download_speed";
    public static final String DOWNLOAD_PROGRESS = "download_progress";
    public static final String DOWNLOAD_URL = "url";
    public static final String DOWNLOAD_ID = "id";
    public static final String DOWNLOAD_PAUSED = "paused";

    public static final int STATE_DOWNLOAD = 1;
    public static final int STATE_DOWNLOAD_ALL = 2;
    public static final int STATE_PAUSE = 3;
    public static final int STATE_PAUSE_ALL = 4;
    public static final int STATE_CONTINUE = 5;
    public static final int STATE_CONTINUE_ALL = 6;
    public static final int STATE_STOP = 7;
    public static final int STATE_INSTALL = 8;
    public static final int STATE_UPGRADE = 9;
    public static final int STATE_INSTALLING = 10;
    public static final int STATE_OPEN = 11;
    public static final int STATE_GET_PRIZE = 12;


    // app task的各种状态描述
    public static final String TEXT_DOWNLOAD = "下载";
    public static final String TEXT_DOWNLOAD_ALL = "下载所有";
    public static final String TEXT_PAUSE = "暂停";
    public static final String TEXT_PAUSE_ALL = "暂停所有";
    public static final String TEXT_CONTINUE = "继续";
    public static final String TEXT_CONTINUE_ALL = "继续所有";
    public static final String TEXT_STOP = "取消";
    public static final String TEXT_INSTALL = "安装";
    public static final String TEXT_UPGRADE = "升级";
    public static final String TEXT_INSTALLING = "安装中...";
    public static final String TEXT_OPEN = "打开";
    public static final String TEXT_GET_PRIZE = "获取奖励";

    // 只在礼包下载中使用到，便于在礼包中的应用进行操作为应用设置的模式
    public static final int MODE_SELECTION = 0;
    public static final int MODE_DOWNLOADING = 1;
    public static final int MODE_INSTALL = 2;

    private static final Map<Integer, String> map; // 设置键值对，便于提取

    static {
        map = new HashMap<Integer, String>();
        map.put(STATE_DOWNLOAD, TEXT_DOWNLOAD);
        map.put(STATE_DOWNLOAD_ALL, TEXT_DOWNLOAD_ALL);
        map.put(STATE_PAUSE, TEXT_PAUSE);
        map.put(STATE_PAUSE_ALL, TEXT_PAUSE_ALL);
        map.put(STATE_CONTINUE, TEXT_CONTINUE);
        map.put(STATE_CONTINUE_ALL, TEXT_CONTINUE_ALL);
        map.put(STATE_STOP, TEXT_STOP);
        map.put(STATE_INSTALL, TEXT_INSTALL);
        map.put(STATE_UPGRADE, TEXT_UPGRADE);
        map.put(STATE_INSTALLING, TEXT_INSTALLING);
        map.put(STATE_OPEN, TEXT_OPEN);
        map.put(STATE_GET_PRIZE, TEXT_GET_PRIZE);
    }

    /**
     * 设置应用中按钮的view形式
     * @param stateCode 当前按钮的状态，从TaskState中获取
     * @param context 上下文
     * @param button 按钮
     */
    public static void setButtonView(int stateCode, Context context, Button button) {
        switch (stateCode) {
            case STATE_DOWNLOAD:
            case STATE_CONTINUE:
            case STATE_PAUSE:
            case STATE_DOWNLOAD_ALL:
            case STATE_CONTINUE_ALL:
            case STATE_PAUSE_ALL:
            case STATE_GET_PRIZE:
            case STATE_STOP:
                button.setText(map.get(stateCode));
                button.setTextColor(context.getResources().getColor(R.color.tab_text_normal));
                button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.button_small_blue));
                break;
            case STATE_INSTALL:
                button.setText(map.get(stateCode));
                button.setTextColor(context.getResources().getColor(R.color.tab_text_normal));
                button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.button_small_green));
                break;
            case STATE_INSTALLING:
                button.setText(map.get(stateCode));
                button.setTextColor(context.getResources().getColor(R.color.tab_text));
                button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.button_small_white));
                break;
            default:
                break;
        }

    }

    public static Map<Integer, String> getTaskStateMap()    {   return map; }

}
