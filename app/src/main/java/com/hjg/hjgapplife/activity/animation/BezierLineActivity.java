package com.hjg.hjgapplife.activity.animation;

import com.hjg.baseapp.widget.danceball.ValueAnimatorView;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;

import butterknife.BindView;

public class BezierLineActivity extends BaseOthreRenderSwipActivity {


    @BindView(R.id.valueAnimatorView)
    ValueAnimatorView valueAnimatorView;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_bezier_line;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "贝塞尔曲线的使用");
    }

    @Override
    protected void initData() {
        valueAnimatorView.post(new Runnable() {
            @Override
            public void run() {
                valueAnimatorView.Startdown();
            }
        });
    }
}
