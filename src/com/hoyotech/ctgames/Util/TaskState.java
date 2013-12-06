package com.hoyotech.ctgames.util;

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

    public static final String STATE = "state";

    // app task的各种状态码
    public static final int STATE_DOWNLOAD = 1;
    public static final int STATE_PAUSE = 2;
    public static final int STATE_CONTINUE = 3;
    public static final int STATE_INSTALL = 4;
    public static final int STATE_UPGRADE = 5;
    public static final int STATE_INSTALLING = 6;
    public static final int STATE_OPEN = 7;
    public static final int STATE_GET_PRIZE = 8;


    // app task的各种状态描述
    public static final String TEXT_DOWNLOAD = "下载";
    public static final String TEXT_PAUSE = "暂停";
    public static final String TEXT_CONTINUE = "继续";
    public static final String TEXT_INSTALL = "安装";
    public static final String TEXT_UPGRADE = "升级";
    public static final String TEXT_INSTALLING = "安装中";
    public static final String TEXT_OPEN = "打开";
    public static final String TEXT_GET_PRIZE = "获取奖励";

    private static final Map<Integer, String> map; // 设置键值对，便于提取

    static {
        map = new HashMap<Integer, String>();
        map.put(STATE_DOWNLOAD, TEXT_DOWNLOAD);
        map.put(STATE_PAUSE, TEXT_PAUSE);
        map.put(STATE_CONTINUE, TEXT_CONTINUE);
        map.put(STATE_INSTALL, TEXT_INSTALL);
        map.put(STATE_UPGRADE, TEXT_UPGRADE);
        map.put(STATE_INSTALLING, TEXT_INSTALL);
        map.put(STATE_OPEN, TEXT_OPEN);
        map.put(STATE_INSTALL, TEXT_INSTALLING);
        map.put(STATE_GET_PRIZE, TEXT_GET_PRIZE);
    }

    public static Map<Integer, String> getTaskStateMap()    {   return map; }

}
