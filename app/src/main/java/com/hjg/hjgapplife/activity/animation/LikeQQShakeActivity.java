package com.hjg.hjgapplife.activity.animation;

import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipFinalActivity;
import com.hjg.hjgapplife.activity.baseRender.LayoutConstans;

import butterknife.BindView;
import butterknife.OnClick;

public class LikeQQShakeActivity extends BaseOthreRenderSwipFinalActivity {

    @BindView(R.id.btn_shake)
    Button btnShake;
    @BindView(R.id.ll_shake)
    LinearLayout ll_shake;
    private AnimationSet animation1;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_like_qqshake;
    }

    @Override
    protected int getIncludeLayoutType() {
        return LayoutConstans.LL;
    }

    @Override
    protected void initTitle() {
        topBarManage.setVisibleTopbar(true);
        topBarManage.iniTop(true, "震动屏幕");
    }

    @Override
    protected void initData() {
        animation1 = (AnimationSet) AnimationUtils.loadAnimation(activity, R.anim.anim_shake);
        animation1.setInterpolator(new CycleInterpolator(2));
//        btnShake.post(new Runnable() {
//            @Override
//            public void run() {
//            }
//        });
    }

    @OnClick(R.id.btn_shake)
    public void onViewClicked() {
        vibrate(1200);
        getWindow().getDecorView().getRootView().findViewById(R.id.ll_fistLayer).startAnimation(animation1);
//        btnShake.startAnimation(animation1);
    }
}
