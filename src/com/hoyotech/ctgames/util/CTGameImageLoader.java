package com.hoyotech.ctgames.util;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by GGCoke on 13-12-10.
 */
public class CTGameImageLoader {
    private static final ImageLoader imageLoader = ImageLoader.getInstance();

    public static void loadImage(Context context, String url, ImageView view) {
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        imageLoader.displayImage(url, view);
    }
}
