package com.hoyotech.ctgames.util;

import android.content.Context;

import com.hoyotech.ctgames.R;

import java.util.Map;

import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

/**
 * 社会化分享操作类
 * Created by GGCoke on 13-12-10.
 */
public class SocialShareUtils {

    /**
     * 进行分享
     * @param context
     * @param silent                        是否后台分享，true为无编辑界面后台分享，false为有编辑界面分享
     * @param platform                      需要分享的平台，null则为应用配置的所有可分享平台
     * @param params                        分享的内容，其中text字段为必填，其他字段选填
     * @param platformActionListener        分享时的callback，实现了分享成功，失败和取消时的操作
     * @param shareContentCustomizeCallback 分享过程callback，为不同平台定义差别化分享内容
     */
    public static void share(Context context, boolean silent, String platform,
                             Map<String, String> params,
                             PlatformActionListener platformActionListener,
                             ShareContentCustomizeCallback shareContentCustomizeCallback) {
        if (null == params || params.size() <= 0 || !params.containsKey("text")) return;
        final OnekeyShare oks = new OnekeyShare();

        if (params.containsKey("address")) {
            oks.setAddress(params.get("address"));
        }

        if (params.containsKey("title")) {
            oks.setTitle(params.get("title"));
        }

        if (params.containsKey("titleUrl")) {
            oks.setTitleUrl(params.get("titleUrl"));
        }

        if (params.containsKey("imagePath")) {
            oks.setImagePath(params.get("imagePath"));
        }

        if (params.containsKey("imageUrl")) {
            oks.setImageUrl(params.get("imageUrl"));
        }

        if (params.containsKey("filePath")) {
            oks.setFilePath(params.get("filePath"));
        }

        if (params.containsKey("comment")) {
            oks.setComment(params.get("comment"));
        }

        if (params.containsKey("site")) {
            oks.setSite(params.get("site"));
        }

        if (params.containsKey("siteUrl")) {
            oks.setSiteUrl(params.get("siteUrl"));
        }

        if (params.containsKey("venueName")) {
            oks.setVenueName(params.get("venueName"));
        }

        if (params.containsKey("venueDescription")) {
            oks.setVenueDescription("venueDescription");
        }

        if (params.containsKey("latitude")) {
            oks.setLatitude(Long.parseLong(params.get("latitude")));
        }

        if (params.containsKey("longitude")) {
            oks.setLongitude(Long.parseLong(params.get("longitude")));
        }

        oks.setNotification(R.drawable.ic_launcher, context.getString(R.string.app_name));
        oks.setSilent(silent);
        oks.setText(params.get("text"));

        if (null != platform && !"".equals(platform)) {
            oks.setPlatform(platform);
        }

        if (null != platformActionListener) {
            oks.setCallback(platformActionListener);
        }

        if (null != shareContentCustomizeCallback) {
            oks.setShareContentCustomizeCallback(shareContentCustomizeCallback);
        }
        oks.show(context);
    }
}
