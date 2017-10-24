package com.hjg.baseapp.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/10/24 0024.
 */

public class UpView extends FrameLayout {
    private int x, y;
    private int moveX, moveY;
    private int offsetX, offsetY;
    private int lastX, lastY;
    private int finalOffsetX, finalOffsetY;
    private Scroller mScroller;
    private float rawY;
    private float downY;
    private float finalY;
    Context context;

    public UpView(@NonNull Context context) {
        super(context);
        mScroller = new Scroller(context);
        this.context = context;
    }

    public UpView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        this.context = context;

    }

    public UpView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
        this.context = context;

    }

    public UpView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mScroller = new Scroller(context);
        this.context = context;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        y = (int) event.getY();
        rawY = event.getRawY();
        Log.d("UpView", "onTouchEvent=================;" + rawY);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                //第一次的位置downY
                downY = rawY;
                Log.d("UpView", "ACTION_DOWN=================;" + downY);
                break;
            case MotionEvent.ACTION_MOVE:
                offsetY = y - lastY;
                Log.d("UpView", "========ACTION_MOVE=========;" + offsetY);
                layout(getLeft(), getTop() + offsetY, getRight(), getBottom() + offsetY);
                //处理移动事件
                break;
            case MotionEvent.ACTION_UP:
                finalY = Math.abs(rawY - downY);
                Log.d("UpView", "=================ACTION_UP;" + event.getRawY());
                if (finalY < 500) {
                } else {
                }
//                smoothScrollTo(0, -200);
                //处理松开事件
                break;
        }
        // 事件处理完毕
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {
        //设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    //调用此方法滚动到目标位置
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

}
