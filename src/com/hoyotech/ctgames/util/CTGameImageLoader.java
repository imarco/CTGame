package com.hoyotech.ctgames.util;

import android.content.Context;
import android.widget.ImageView;

import com.hoyotech.ctgames.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by GGCoke on 13-12-10.
 */
public class CTGameImageLoader {
    private static final ImageLoader imageLoader = ImageLoader.getInstance();
    private static final DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisc(true)
            .build();

    public static void loadImage(Context context, String url, ImageView view) {
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }
        imageLoader.displayImage(url, view, options);
    }
}
