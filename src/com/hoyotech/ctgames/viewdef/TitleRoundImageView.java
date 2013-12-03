package com.hoyotech.ctgames.viewdef;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 自定义圆角矩形图片类
 * 
 * @author Tian
 * 
 */
public class TitleRoundImageView extends ImageView {

    public TitleRoundImageView(Context context) {
        super(context);
    }

    public TitleRoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleRoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        Path clipPath = new Path();
        Path clipPath2 = new Path();
        int w = this.getWidth();
        int h = this.getHeight();

        clipPath.addRoundRect(new RectF(0, 0, w, h * 2), 15f, 15f,
                Path.Direction.CW);
        clipPath2.addRect(new RectF(0, 0, w, h), Path.Direction.CW);

        canvas.clipPath(clipPath);
        canvas.clipPath(clipPath2);
        super.onDraw(canvas);
    }
}