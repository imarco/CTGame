package com.hoyotech.ctgames.viewdef;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义ViewPager，可设置是否允许滑动
 * Created by GGCoke on 13-12-6.
 */
public class CTGameViewPager extends ViewPager {
    private boolean canScroll = true;

    public CTGameViewPager(Context context) {
        super(context);
    }

    public CTGameViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollable(boolean canScroll) {
        this.canScroll = canScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return canScroll ? super.onInterceptTouchEvent(ev) : false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return canScroll ? super.onTouchEvent(ev) : false;
    }
}
