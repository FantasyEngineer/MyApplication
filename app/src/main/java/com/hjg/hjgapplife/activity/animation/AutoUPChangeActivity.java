package com.hjg.hjgapplife.activity.animation;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

public class AutoUPChangeActivity extends BaseOthreRenderSwipActivity {

    private ImageView iv, iv2;
    private ValueAnimator mAnimatorDown;
    private LinearLayout ll;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_auto_upchange;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "济南汽车站首页效果");
    }

    @Override
    protected void initData() {
        ll = (LinearLayout) findViewById(R.id.ll);
        iv = (ImageView) findViewById(R.id.iv);
        iv2 = (ImageView) findViewById(R.id.iv2);

        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.trans);
        ll.postDelayed(new Runnable() {
            @Override
            public void run() {
                ll.setAnimation(animation);
//                animation.start();
            }
        }, 200);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv.setVisibility(View.GONE);
                iv2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

//        initAnimator();
//        final ObjectAnimator animator = ObjectAnimator.ofFloat(iv, "translationY", 0, -dip2px(this, 400));
//        animator.setDuration(200);
//        iv.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                animator.start();
////                mAnimatorDown.start();
//            }
//        }, 2000);
//        animator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                iv.setVisibility(View.GONE);
//                iv2.setVisibility(View.VISIBLE);
//                ObjectAnimator animator1 = ObjectAnimator.ofFloat(iv2, "scaleX", 0, 1);
//                animator.setDuration(500);
//                animator.start();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
    }


    private void initAnimator() {
        mAnimatorDown = ValueAnimator.ofFloat(0, dip2px(this, 400));
        mAnimatorDown.setDuration(1000);
        mAnimatorDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int mY = (int) (float) valueAnimator.getAnimatedValue();
                Log.d("Main2Activity", "mY:" + mY);
                if (mY >= 200) {
                    iv2.setVisibility(View.VISIBLE);

                }
            }
        });
        mAnimatorDown.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                iv.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mAnimatorDown.start();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
