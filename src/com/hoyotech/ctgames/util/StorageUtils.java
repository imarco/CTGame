package com.hoyotech.ctgames.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.text.DecimalFormat;

/**
 * 存储相关公用方法
 * Created by GGCoke on 13-12-6.
 */
public class StorageUtils {
    /**
     * 是否挂载了SD卡
     * @return
     */
    public static boolean hasSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean isSDCardReadOnly() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }

    /**
     * 获取SD卡剩余空间
     * @return
     */
    public static long getAvailableStorage() {
        long avaliable = 0L;
        try {
            String storageDir = Environment.getExternalStorageDirectory().toString();
            StatFs stat = new StatFs(storageDir);
            avaliable = ((long) stat.getAvailableBlocks() * (long) stat.getBlockSize());
        } catch (RuntimeException e) {
            avaliable = 0L;
        }
        return avaliable;
    }

    /**
     * SD卡剩余空间大小是否足够所需
     * @param storageNeeded 需要空间
     * @return 足够需求返回true，否则返回false
     */
    public static boolean hasEnoughStorage(long storageNeeded) {
        return getAvailableStorage() >= storageNeeded;
    }

    /**
     * 格式化文件大小
     * @param size
     * @return
     */
    public static String getSizeFormatted(long size) {
        if (size / (1024 * 1024) > 0) {
            float tmpSize = (float) (size) / (float)(1024 * 1024);
            DecimalFormat df = new DecimalFormat("#.##");
            return "" + df.format(tmpSize) + "MB";
        } else if (size / 1024 > 0) {
            return "" + (size / 1024) + "KB";
        } else {
            return "" + size + "B";
        }
    }

    /**
     * 安装APK，后面需要改成静默安装方式
     * @param context
     * @param url
     */
    public static void installAPK(Context context, final String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(url)), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setClassName("com.android.packageinstaller", "com.android.packageinstaller.PackageInstallerActivity");
        context.startActivity(intent);
    }

    /**
     * 删除文件，如果文件为目录，则递归删除目录中所有目录及文件
     * @param file
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(File file) {
        boolean result = true;
        if (file.exists()) {
            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    result &= delete(f);
                }
                result &= file.delete();
            } else if (file.isFile()) {
                result &= file.delete();
            }
        }

        return result;
    }

    public static String getSessionID() {
        return "aslgkfdhaohga";
    }

    public static String getUserPhoneNumber() {
        return "15527015802";
    }

    public static String getIMSI() {
        return "";
    }

    public static String getPhoneType() {
        return "";
    }
}
