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
    public static final String DOWNLOAD_APPINFO = "download_appinfo";
    public static final String DOWNLOAD_URL = "url";
    public static final String DOWNLOAD_APPID = "id";
    public static final String DOWNLOAD_PAUSED = "paused";

    public static final int APP_DOWNLOADED_NEVER = 0;
    public static final int APP_DOWNLOADED_HAS = 1;

    public static final int STATE_START = 0;            // 启动下载service
    public static final int STATE_PREPARE = 1;          // 应用未下载，对应文字为下载
    public static final int STATE_DOWNLOADING = 2;      // 下载中，对应文字暂停   //////在数据库中有
    public static final int STATE_PAUSED = 3;           // 暂停中，对应文字继续   //////在数据库中有
    public static final int STATE_COMPLETE = 4;         // 下载完成，对应文字安装 //////在数据库中有
    public static final int STATE_INSTALLING = 5;      // 安装中，对应文字安装中
    public static final int STATE_INSTALLED = 6;        // 安装完成，显示文字打开 //////在数据库中有
    public static final int STATE_OPENED = 7;           // 完成打开，显示文字获取奖励  ///////在数据库中有
    public static final int STATE_CONTINUE = 8;
    public static final int STATE_DOWNLOAD_ALL = 9;
    public static final int STATE_PAUSE_ALL = 10;
    public static final int STATE_CONTINUE_ALL = 11;
    public static final int STATE_STOP = 12;            // 取消下载,暂时没用到
    public static final int STATE_TASK_COMPLETE = 13;   // 任务已完成  //////在数据库中有

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
        map.put(STATE_PREPARE, TEXT_DOWNLOAD);
        map.put(STATE_DOWNLOADING, TEXT_PAUSE);
        map.put(STATE_PAUSED, TEXT_CONTINUE);
        map.put(STATE_COMPLETE, TEXT_INSTALL);
        map.put(STATE_INSTALLING, TEXT_INSTALLING);
        map.put(STATE_INSTALLED, TEXT_OPEN);
        map.put(STATE_OPENED, TEXT_GET_PRIZE);
        map.put(STATE_DOWNLOAD_ALL, TEXT_DOWNLOAD_ALL);
        map.put(STATE_PAUSE_ALL, TEXT_PAUSE_ALL);
        map.put(STATE_CONTINUE_ALL, TEXT_CONTINUE_ALL);
        map.put(STATE_TASK_COMPLETE, TEXT_GET_PRIZE);
    }

    /**
     * 设置应用中按钮的view形式
     * @param stateCode 当前按钮的状态，从TaskState中获取
     * @param context 上下文
     * @param button 按钮
     */
    public static void setButtonView(int stateCode, Context context, Button button) {
        switch (stateCode) {
            case STATE_PREPARE:
            case STATE_DOWNLOADING:
            case STATE_PAUSED:
            case STATE_OPENED:
            case STATE_DOWNLOAD_ALL:
            case STATE_PAUSE_ALL:
            case STATE_CONTINUE_ALL:
                System.out.println("bottom: " + button.getPaddingBottom());
                System.out.println("top: " + button.getPaddingTop());
                System.out.println("left: " + button.getPaddingLeft());
                System.out.println("right: " + button.getPaddingRight());
                button.setText(map.get(stateCode));
                button.setTextColor(context.getResources().getColor(R.color.new_text_grey_color));
                button.setBackground(context.getResources().getDrawable(R.drawable.button_green));
                System.out.println("bottom: " + button.getPaddingBottom());
                System.out.println("top: " + button.getPaddingTop());
                System.out.println("left: " + button.getPaddingLeft());
                System.out.println("right: " + button.getPaddingRight());
                break;
            case STATE_COMPLETE:
                button.setText(map.get(stateCode));
                button.setTextColor(context.getResources().getColor(R.color.new_text_grey_color));
                button.setBackground(context.getResources().getDrawable(R.drawable.button_green));
                break;
            case STATE_INSTALLING:
                button.setText(map.get(stateCode));
                button.setTextColor(context.getResources().getColor(R.color.new_text_grey_color));
                button.setBackground(context.getResources().getDrawable(R.drawable.button_grey));
                button.setEnabled(false);    // 安装中不能点击button
                break;
            case STATE_TASK_COMPLETE:
                button.setText(map.get(stateCode));
                button.setTextColor(context.getResources().getColor(R.color.new_text_grey_color));
                button.setBackground(context.getResources().getDrawable(R.drawable.button_orange));
                break;
            case STATE_INSTALLED:
                button.setText(map.get(stateCode));
                button.setTextColor(context.getResources().getColor(R.color.new_text_grey_color));
                button.setBackground(context.getResources().getDrawable(R.drawable.button_green));
            default:
                break;
        }

    }



    public static Map<Integer, String> getTaskStateMap()    {   return map; }

}
