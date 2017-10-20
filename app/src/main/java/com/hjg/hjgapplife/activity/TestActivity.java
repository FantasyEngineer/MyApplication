package com.hjg.hjgapplife.activity;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class TestActivity extends BaseOthreRenderSwipActivity {

    @BindView(R.id.btn_trans1)
    Button btn_trans1;
    @BindView(R.id.btn_trans2)
    Button btn_trans2;
    @BindView(R.id.tv_bar)
    TextView tvBar;
    @BindView(R.id.tv_bar1)
    TextView tvBar1;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    @BindView(R.id.tv_scale1)
    TextView tvScale1;
    @BindView(R.id.tv_scale2)
    TextView tvScale2;
    @BindView(R.id.btn_scale)
    Button btnScale;
    @BindView(R.id.tv_rotate1)
    TextView tvRotate1;
    @BindView(R.id.tv_rotate2)
    TextView tvRotate2;
    @BindView(R.id.btn_rotate)
    Button btnRotate;
    private Animation animationExit;
    private Animation animationEnter;
    private Animation animationScale;
    private Animation animationScaleSmall;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "测试头部消失");
    }

    @Override
    protected void initData() {
//        animation = AnimationUtils.loadAnimation(activity, R.anim.top_exit);
//        tvBar.setAnimation(animation);
//        animation.start();
        //向上位移的动画
        animationExit = AnimationUtils.loadAnimation(activity, R.anim.top_exit);
        animationExit.setInterpolator(new AccelerateDecelerateInterpolator());
        //向下位移的动画
        animationEnter = AnimationUtils.loadAnimation(activity, R.anim.top_enter);
        animationEnter.setInterpolator(new BounceInterpolator());
        //缩放的动画
        animationScale = AnimationUtils.loadAnimation(activity, R.anim.anim_scale_big);
        animationScaleSmall = AnimationUtils.loadAnimation(activity, R.anim.anim_scale_small);

    }


    boolean isShow = true;
    boolean open = true;
    boolean isSmall = true;

    @OnClick({R.id.btn_trans1, R.id.btn_trans2, R.id.btn_scale, R.id.btn_rotate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_trans1:
                if (isShow) {
                    isShow = false;
                    topBarManage.dismiss();
                } else {
                    isShow = true;
                    topBarManage.show();
                }
                break;
            case R.id.btn_trans2:
                if (open) {
                    open = false;
                    transUp();
                } else {
                    open = true;
                    transDown();
                }
                break;

            case R.id.btn_scale:
                if (isSmall) {
                    btnScale.setText("缩小");
                    isSmall = false;
                    scaleBig();
                } else {
                    btnScale.setText("放大");
                    isSmall = true;
                    scaleSmall();
                }
                break;
            case R.id.btn_rotate:
                Log.d("TestActivity", "btn_rotate");
                rorate();
                break;
        }
    }


    //向上位移
    public void transUp() {
        tvBar.startAnimation(animationExit);
        //改变view的位置参数
        ObjectAnimator.ofFloat(tvBar1, "translationY", 0, -200)
                .setDuration(1000)
                .start();
    }

    //向下位移
    public void transDown() {
        tvBar.startAnimation(animationEnter);

        //改变view的位置参数
        ObjectAnimator.ofFloat(tvBar1, "translationY", -200, 0)
                .setDuration(1000)
                .start();
    }

    public void scaleBig() {
        tvScale1.setVisibility(View.VISIBLE);
        tvScale2.setVisibility(View.VISIBLE);

        tvScale1.startAnimation(animationScale);
        ObjectAnimator.ofFloat(tvScale1, "scaleX", 0, 1)
                .setDuration(1000)
                .start();
        ObjectAnimator.ofFloat(tvScale2, "scaleY", 0, 1)
                .setDuration(1000)
                .start();
    }

    public void scaleSmall() {
        tvScale1.startAnimation(animationScaleSmall);
        ObjectAnimator.ofFloat(tvScale1, "scaleX", 1, 0)
                .setDuration(1000)
                .start();
        ObjectAnimator.ofFloat(tvScale2, "scaleY", 1, 0)
                .setDuration(1000)
                .start();
    }

    public void rorate() {
//        ObjectAnimator.ofFloat(tvRotate2, "rotation", 0, 180)
//                .setDuration(1000)
//                .start();
//        ObjectAnimator.ofFloat(tvRotate2, "rotationX", 0, 180)
//                .setDuration(1000)
//                .start();
        ObjectAnimator.ofFloat(tvRotate2, "rotationY", 0, 180)
                .setDuration(1000)
                .start();
    }


}
