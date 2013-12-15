package com.hoyotech.ctgames.viewdef;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-15
 * Time: 下午12:17
 * 图片的宽度匹配父组件，高度自适应原始图片长宽比
 */
public class AspectRatioWidthImageView extends ImageView {

    public AspectRatioWidthImageView(Context context) {
        super(context);
    }

    public AspectRatioWidthImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AspectRatioWidthImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * getDrawable().getIntrinsicHeight() / getDrawable().getIntrinsicWidth();
        setMeasuredDimension(width, height);
    }
}
