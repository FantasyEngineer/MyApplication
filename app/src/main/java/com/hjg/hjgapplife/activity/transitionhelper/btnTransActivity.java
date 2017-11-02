package com.hjg.hjgapplife.activity.transitionhelper;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.base.BaseActivity;

import immortalz.me.library.TransitionsHeleper;
import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.method.ColorShowMethod;
import immortalz.me.library.method.NoneShowMethod;

public class btnTransActivity extends BaseActivity {

    private ImageView iv_target;
    public static boolean isSpread = true;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//    }

    @Override
    protected void initTitle() {
        topBarManage.setVisibleTopbar(true);
        topBarManage.iniTop(true, "btn穿透");
    }


    @Override
    protected int getContentLayout() {
        return R.layout.activity_btn_trans;
    }

    @Override
    protected void initView() {
        iv_target = (ImageView) findViewById(R.id.iv_target);
        if (isSpread) {
            //扩散效果
            TransitionsHeleper.getInstance()
                    //copy的原来的老的View需要做出什么样的动作之后才能进入到新的界面，iv_target为空，则进入到页面之后，不做动作
                    .setShowMethod(new ColorShowMethod(R.color.gray, R.color.white) {
                        @Override
                        public void loadCopyView(InfoBean bean, ImageView copyView) {
                            AnimatorSet set = new AnimatorSet();
                            set.playTogether(
                                    ObjectAnimator.ofFloat(copyView, "rotation", 0, 180),
                                    ObjectAnimator.ofFloat(copyView, "scaleX", 1, 0),
                                    ObjectAnimator.ofFloat(copyView, "scaleY", 1, 0)
                            );
                            set.setInterpolator(new AccelerateInterpolator());
                            set.setDuration(duration / 4 * 5).start();
                        }

                        //进入到新的页面的时候最后填入的View做出的动作
                        @Override
                        public void loadTargetView(InfoBean bean, ImageView targetView) {
                            AnimatorSet set = new AnimatorSet();
                            set.playTogether(
                                    ObjectAnimator.ofFloat(targetView, "rotation", 0, 180)
                            );
                            set.setInterpolator(new AccelerateInterpolator());
                            set.setDuration(duration / 4 * 5).start();
                        }
                    })
                    .show(this, iv_target);
        } else {
            //不扩散效果
            TransitionsHeleper.getInstance()
                    .setShowMethod(new NoneShowMethod() {
                        @Override
                        public void loadCopyView(InfoBean bean, ImageView copyView) {
                            AnimatorSet set = new AnimatorSet();
                            set.playTogether(
                                    ObjectAnimator.ofFloat(copyView, "rotation", 0, 180),
                                    ObjectAnimator.ofFloat(copyView, "scaleX", 1, 0),
                                    ObjectAnimator.ofFloat(copyView, "scaleY", 1, 0)
                            );
                            set.setInterpolator(new AccelerateInterpolator());
                            set.setDuration(duration / 4 * 5).start();
                        }


                    })
                    .show(this, null);
        }


    }

    @Override
    protected void initData() {

    }
}
