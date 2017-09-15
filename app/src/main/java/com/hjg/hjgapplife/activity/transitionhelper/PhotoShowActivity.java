package com.hjg.hjgapplife.activity.transitionhelper;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hjg.baseapp.util.GlideCatchUtil;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;

import immortalz.me.library.TransitionsHeleper;
import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.method.InflateShowMethod;

public class PhotoShowActivity extends BaseOthreRenderSwipActivity {


    private ImageView iv_bigshow;
    private Button btn_trans;
    private Button btn_trans_no_spread;

    @Override
    protected void initTitle() {
        topBarManage.isVisibleTopbar(true);
        topBarManage.iniTop(true, "大图展示");
        topBarManage.setLeftBtnBack(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

//    @Override
//    protected int setBarColor() {
//        return R.color.darkorange;
//    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_photo_show;
    }

    protected void initView() {
        iv_bigshow = (ImageView) findViewById(R.id.iv_bigshow);
        btn_trans = (Button) findViewById(R.id.btn_trans);
        btn_trans_no_spread = (Button) findViewById(R.id.btn_trans_no_spread);
    }

    @Override
    protected void initData() {
        initView();
        initAction();
        TransitionsHeleper.getInstance()
                .setShowMethod(new InflateShowMethod(this, R.layout.activity_rv_inflate) {
                    @Override
                    public void loadCopyView(InfoBean bean, final ImageView copyView) {
                        GlideCatchUtil.loadResImg(activity, bean.getImgId(), copyView);
                    }

                    @Override
                    public void loadTargetView(InfoBean bean, ImageView targetView) {
                        GlideCatchUtil.loadResImg(activity, bean.getImgId(), targetView);
                    }
                })
                .show(activity, iv_bigshow);
    }

    public void initAction() {
        //扩散点击
        btn_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTransActivity.isSpread = true;
                TransitionsHeleper.startActivity(activity, btnTransActivity.class, btn_trans);
            }
        });
        //不扩散点击
        btn_trans_no_spread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTransActivity.isSpread = false;
                TransitionsHeleper.startActivity(activity, btnTransActivity.class, btn_trans_no_spread);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
