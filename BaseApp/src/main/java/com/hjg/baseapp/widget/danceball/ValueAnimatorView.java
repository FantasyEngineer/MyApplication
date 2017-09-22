package com.hjg.baseapp.widget.danceball;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.hjg.baseapp.R;

/**
 * 小球弹跳view, 详情见动画-贝塞尔曲线-BezierLineActivity
 */

public class ValueAnimatorView extends View {

    private Paint paintPoint;//绘制端点两个空心小圆
    private int mPaintWidth = 10;//线的宽度
    private Paint paintPointWhite;//两个实心的小球
    private Paint paintCircle;

    private Point start;//左端点坐标
    private Point end;//右端点坐标

    private int mViewWidth;//view宽度
    private int mViewHeight;//view高度
    private float mBerSaiErY;//贝塞尔曲线的控制点的Y坐标
    private int mJumpHeight = 400;//小球跳起的最大高度，默认400dp，如果满足不了要求再根据view高度进行极端
    private float mY;//弹跳小球圆心坐标
    private ValueAnimator mAnimatorDown;
    private ValueAnimator mAnimatorUp;


    public ValueAnimatorView(Context context) {
        super(context);
        init(context);
    }

    public ValueAnimatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ValueAnimatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ValueAnimatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context) {
        paintPoint = new Paint();
        paintPoint.setColor(Color.WHITE);//颜色
        paintPoint.setStyle(Paint.Style.STROKE);//空心
        paintPoint.setStrokeWidth(mPaintWidth);//设置线宽度
        paintPoint.setAntiAlias(true);//抗锯齿方法

        //绘制两个实心的小球用于填充两个端点的空白处，颜色和背景色相同，造成透明效果
        paintPointWhite = new Paint();
        paintPointWhite.setColor(ContextCompat.getColor(context, R.color.orange));
        paintPointWhite.setStyle(Paint.Style.FILL);
        paintPointWhite.setStrokeWidth(mPaintWidth);
        paintPointWhite.setAntiAlias(true);
        //绘制弹跳小球的画笔，白色、填充
        paintCircle = new Paint();
        paintCircle.setColor(Color.WHITE);
        paintCircle.setStyle(Paint.Style.FILL);
        paintCircle.setStrokeWidth(mPaintWidth);
        paintCircle.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        //设置起点
        path.moveTo(start.getX(), mViewHeight / 2);
        path.quadTo(mViewWidth / 2, mBerSaiErY, end.getX(), end.getY());
//        Log.d("ValueAnimatorView", "mBerSaiErY:" + mBerSaiErY);
        canvas.drawPath(path, paintPoint);
        //绘制两个端点
        canvas.drawCircle(start.getX(), start.getY(), 12, paintPoint);
        canvas.drawCircle(end.getX(), end.getY(), 12, paintPoint);
//        //绘制两个实心的小球用于填充两个端点的空白处
        canvas.drawCircle(start.getX(), start.getY(), 12 - mPaintWidth / 2, paintPointWhite);
        canvas.drawCircle(end.getX(), end.getY(), 12 - mPaintWidth / 2, paintPointWhite);
//        //绘制弹跳的小球
        canvas.drawCircle(mViewWidth / 2, mY, mPointRadius, paintCircle);
    }

    private int mPointRadius = 40;//小球半径，40个像素
    private int mDownPx = 100;//绳子下降的距离，默认100px，如果高度不允许再进行计算压缩高度

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("ValueAnimatorView", "left:" + left);
        Log.d("ValueAnimatorView", "top:" + top);
        Log.d("ValueAnimatorView", "right:" + right);
        Log.d("ValueAnimatorView", "bottom:" + bottom);

        mY = mViewHeight / 2 - mJumpHeight;//小球圆心坐标（X坐标不变，Y坐标等于view高度的一半减去弹跳高度）
        mBerSaiErY = mViewHeight / 2;//贝塞尔曲线控制点坐标（X坐标不变，在最开始的时候，Y坐标等于view高度的一半）
        start = new Point(0 + 10 + mPaintWidth + getPaddingLeft(), mViewHeight / 2);//左端点
        end = new Point(mViewWidth - 10 - mPaintWidth - getPaddingRight(), mViewHeight / 2);//右端点
        //这里判断mJumpHeight能不能达到需要的高度，如果不行则根据view高度重新计算（跳起高度最大不能超过view高度的一半减去小球的半径）
        if (mJumpHeight > mViewHeight / 2 - mPointRadius / 2) {
            mJumpHeight = mViewHeight / 2 - mPointRadius;//为什么减去直径而不是半径，因为小球弹起的起点高度是从半径开始的
        }
        //为什么是3倍，因为这个过程中球上升的高度是100的3倍，就像我们默认是绳子下降100个像素，
        // 然后上升100个像素后球线分离，线再下降150个像素，线在上升50个像素到最后绳子静止，而这个过程中球上升的高度是300个像素
        if (mJumpHeight <= 3 * mDownPx) {//球上升的最大高度必须大于3倍的绳子下降最低距离，这里减去10个像素之差，否则绳子会失去弹性效果
            mDownPx = mJumpHeight / 3 - 10;
        }

        Log.d("ValueAnimatorView", "mJumpHeight:" + mJumpHeight);
        Log.d("ValueAnimatorView", "mDownPx:" + mDownPx);


        initAnimator();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("ValueAnimatorView", "w:" + w);
        Log.d("ValueAnimatorView", "h:" + h);

        mViewWidth = w;
        mViewHeight = h;
    }

    private int TIME = 1500;//动画执行时长（小球从最低点弹到最高点所用时间，也是从最高点降落到最低点所用时间）

    /**
     * 初始化动画
     */
    private void initAnimator() {
        mAnimatorDown = ValueAnimator.ofFloat(mViewHeight / 2 - mJumpHeight, mViewHeight / 2 + mDownPx);
        mAnimatorDown.setDuration(TIME);
        mAnimatorDown.setInterpolator(new AccelerateInterpolator());//加速下降
        mAnimatorDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mY = (float) valueAnimator.getAnimatedValue();
                if (mY < mViewHeight / 2 - mPointRadius - mPaintWidth / 2) {//小球下降，没有接触绳子
                    mBerSaiErY = mViewHeight / 2;//在这个过程中绳子没有发生变化
                } else if (mY >= mViewHeight / 2 - mPointRadius - mPaintWidth / 2 && mY <= mViewHeight / 2 + mDownPx) {
                    //在这个过程中，绳子贴着小球一块下降到最低点。
                    mBerSaiErY = getControlPointF(start, end,
                            new Point(mViewWidth / 2, mY + mPointRadius + mPaintWidth / 2)).getY();
                }
                invalidate();//重新绘制
            }
        });
        mAnimatorDown.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.d("ValueAnimatorView", "下降完成");
                up();//当下降完成后开始上升

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });


        mAnimatorUp = ValueAnimator.ofFloat(mViewHeight / 2 + mDownPx, mViewHeight / 2 - mJumpHeight);
        mAnimatorUp.setInterpolator(new DecelerateInterpolator());//减速上升
        mAnimatorUp.setDuration(TIME);
        mAnimatorUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mY = (float) valueAnimator.getAnimatedValue();
                if (mY >= mViewHeight / 2 - mDownPx - mPointRadius - mPaintWidth / 2
                        && mY <= mViewHeight / 2 + mDownPx) {//上升100个像素后球线分离
                    mBerSaiErY = getControlPointF(start, end,
                            new Point(mViewWidth / 2, mY + mPointRadius + mPaintWidth / 2)).getY();
                } else if (mY >= mViewHeight / 2 - mDownPx - (mDownPx + mDownPx / 2) - mPointRadius - mPaintWidth / 2
                        && mY < mViewHeight / 2 - mDownPx - mPointRadius - mPaintWidth / 2) {//线下降150个像素
                    //获取曲线上的中心点坐标
                    float mCenterY = (mViewHeight / 2 - mDownPx - mPointRadius - mPaintWidth / 2) * 2 - mY;
                    //根据中心点坐标获取曲线的控制点坐标Y
                    mBerSaiErY = getControlPointF(start, end, new Point(mViewWidth / 2, mCenterY + mPointRadius + mPaintWidth / 2)).getY();

                } else if (mY >= mViewHeight / 2 - mDownPx - (mDownPx + mDownPx / 2) - mDownPx / 2 - mPointRadius - mPaintWidth / 2
                        && mY < mViewHeight / 2 - mDownPx - (mDownPx + mDownPx / 2) - mPointRadius - mPaintWidth / 2) {//线上升50个像素
                    //获取曲线上的中心点坐标
                    float mCenterY = mY + 3 * mDownPx;
                    //根据中心点坐标获取曲线的控制点坐标Y
                    mBerSaiErY = getControlPointF(start, end, new Point(mViewWidth / 2, mCenterY + mPointRadius + mPaintWidth / 2)).getY();
                } else {//线静止
                    mBerSaiErY = mViewHeight / 2;
                }
                invalidate();//重新绘制

            }
        });
        mAnimatorUp.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Startdown();//当上升完成后开始下降
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }


    /**
     * 开启下降动画
     */
    public void Startdown() {
        mAnimatorDown.start();
    }

    /**
     * 开启上升动画
     */
    private void up() {
        mAnimatorUp.start();
    }

    /**
     * 根据 最高点，获取贝塞尔曲线的 控制点
     *
     * @param startPointF  开始点
     * @param endPointF    结束点
     * @param bezierPointF 最高点
     * @return 控制点
     */
    public static Point getControlPointF(Point startPointF, Point endPointF, Point bezierPointF) {
        //B(t)=(1-t)^2P0+2t(1-t)P1+t^2P2;
        Point controlPointF = new Point(0, 0);
        float tmp = 0.5F;
        float t = 0.5F;
        controlPointF.setX((bezierPointF.getX() - tmp * tmp * startPointF.getY() - t * t * endPointF.getY()) / (2 * t * tmp));
        controlPointF.setY((bezierPointF.getY() - tmp * tmp * startPointF.getY() - t * t * endPointF.getY()) / (2 * t * tmp));
        return controlPointF;
    }
}
