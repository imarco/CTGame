package com.hoyotech.ctgames.viewdef;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-15
 * Time: 下午12:40
 * 图片的高度匹配父组件，宽度自适应原始图片长宽比
 */
public class AspectRatioHeightImageView extends ImageView {

    public AspectRatioHeightImageView(Context context) {
        super(context);
    }

    public AspectRatioHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AspectRatioHeightImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = height * getDrawable().getIntrinsicWidth() / getDrawable().getIntrinsicHeight();
        setMeasuredDimension(width, height);
    }

}
