package com.hjg.hjgapplife.activity.animation;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

//仿淘宝点击购物参数选择效果
public class RotationActivity extends BaseOthreRenderSwipActivity {

    private RelativeLayout firstView;
    private LinearLayout secondView;
    private int fHeight = 2;
    private int sHeight = 2;
    private Button mShowButton;
    private Button mHiddenButton;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_rotation;
    }

    @Override
    protected void initTitle() {
        topBarManage.isVisibleTopbar(false);
    }

    @Override
    protected void initData() {
        firstView = (RelativeLayout) findViewById(R.id.first_view);
        secondView = (LinearLayout) findViewById(R.id.second_view);
        mShowButton = (Button) findViewById(R.id.btn_anim3_show);
        mHiddenButton = (Button) findViewById(R.id.btn_anim3_hidden);
        firstView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                fHeight = firstView.getHeight();
                firstView.getViewTreeObserver()
                        .removeGlobalOnLayoutListener(this);
            }
        });
        secondView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                sHeight = secondView.getHeight();
                secondView.getViewTreeObserver()
                        .removeGlobalOnLayoutListener(this);
            }
        });

        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initShowAnim();
            }
        });
        mHiddenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initHiddenAnim();
            }
        });
    }


    private void initShowAnim() {
        ObjectAnimator fViewScaleXAnim = ObjectAnimator.ofFloat(firstView, "scaleX", 1.0f, 0.8f);
        fViewScaleXAnim.setDuration(350);
        ObjectAnimator fViewScaleYAnim = ObjectAnimator.ofFloat(firstView, "scaleY", 1.0f, 0.8f);
        fViewScaleYAnim.setDuration(350);
        ObjectAnimator fViewAlphaAnim = ObjectAnimator.ofFloat(firstView, "alpha", 1.0f, 0.5f);
        fViewAlphaAnim.setDuration(350);
        ObjectAnimator fViewRotationXAnim = ObjectAnimator.ofFloat(firstView, "rotationX", 0f, 10f);
        fViewRotationXAnim.setDuration(200);
        ObjectAnimator fViewResumeAnim = ObjectAnimator.ofFloat(firstView, "rotationX", 10f, 0f);
        fViewResumeAnim.setDuration(150);
        fViewResumeAnim.setStartDelay(200);
        ObjectAnimator fViewTransYAnim = ObjectAnimator.ofFloat(firstView, "translationY", 0, -0.1f * fHeight);
        fViewTransYAnim.setDuration(350);
        ObjectAnimator sViewTransYAnim = ObjectAnimator.ofFloat(secondView, "translationY", sHeight, 0);
        sViewTransYAnim.setDuration(350);
        sViewTransYAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                secondView.setVisibility(View.VISIBLE);
            }
        });
        AnimatorSet showAnim = new AnimatorSet();
        showAnim.playTogether(fViewScaleXAnim, fViewRotationXAnim, fViewResumeAnim, fViewTransYAnim, fViewAlphaAnim, fViewScaleYAnim, sViewTransYAnim);
        showAnim.start();
    }

    private void initHiddenAnim() {
        ObjectAnimator fViewScaleXAnim = ObjectAnimator.ofFloat(firstView, "scaleX", 0.8f, 1.0f);
        fViewScaleXAnim.setDuration(350);
        ObjectAnimator fViewScaleYAnim = ObjectAnimator.ofFloat(firstView, "scaleY", 0.8f, 1.0f);
        fViewScaleYAnim.setDuration(350);
        ObjectAnimator fViewAlphaAnim = ObjectAnimator.ofFloat(firstView, "alpha", 0.5f, 1.0f);
        fViewAlphaAnim.setDuration(350);
        ObjectAnimator fViewRotationXAnim = ObjectAnimator.ofFloat(firstView, "rotationX", 0f, 10f);
        fViewRotationXAnim.setDuration(200);
        ObjectAnimator fViewResumeAnim = ObjectAnimator.ofFloat(firstView, "rotationX", 10f, 0f);
        fViewResumeAnim.setDuration(150);
        fViewResumeAnim.setStartDelay(200);
        ObjectAnimator fViewTransYAnim = ObjectAnimator.ofFloat(firstView, "translationY", -0.1f * fHeight, 0);
        fViewTransYAnim.setDuration(350);
        ObjectAnimator sViewTransYAnim = ObjectAnimator.ofFloat(secondView, "translationY", 0, sHeight);
        sViewTransYAnim.setDuration(350);
        sViewTransYAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                secondView.setVisibility(View.INVISIBLE);
            }
        });
        AnimatorSet showAnim = new AnimatorSet();
        showAnim.playTogether(fViewScaleXAnim, fViewRotationXAnim, fViewResumeAnim, fViewTransYAnim, fViewAlphaAnim, fViewScaleYAnim, sViewTransYAnim);
        showAnim.start();
    }

}
