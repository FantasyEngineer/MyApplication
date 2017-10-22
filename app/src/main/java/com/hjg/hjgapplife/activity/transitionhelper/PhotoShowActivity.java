package com.hjg.hjgapplife.activity.transitionhelper;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.blog.www.guideview.Guide;
import com.blog.www.guideview.GuideBuilder;
import com.bumptech.glide.Glide;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;
import com.hjg.hjgapplife.activity.guide.SimpleComponent;

import immortalz.me.library.TransitionsHeleper;
import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.method.InflateShowMethod;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoShowActivity extends BaseOthreRenderSwipActivity {


    private PhotoView iv_bigshow;
    private Button btn_trans;
    private Button btn_trans_no_spread;
    private boolean isShow = true;

    @Override
    protected void initTitle() {
        topBarManage.setVisibleTopbar(true);
        topBarManage.iniTop(true, "大图展示");
        topBarManage.setLeftBtnBack(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_photo_show;
    }

    protected void initView() {
        iv_bigshow = (PhotoView) findViewById(R.id.iv_bigshow);
        btn_trans = (Button) findViewById(R.id.btn_trans);
        btn_trans_no_spread = (Button) findViewById(R.id.btn_trans_no_spread);
        btn_trans.post(new Runnable() {
            @Override
            public void run() {
//                showGuideView();
            }
        });
    }

    @Override
    protected void initData() {
        initView();
        initAction();
        TransitionsHeleper.getInstance()
                .setShowMethod(new InflateShowMethod(this, R.layout.activity_rv_inflate) {
                    @Override
                    public void loadCopyView(InfoBean bean, final ImageView copyView) {
                        copyView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        Glide.with(activity).load(bean.getImgId()).into(copyView);
                    }

                    @Override
                    public void loadTargetView(InfoBean bean, ImageView targetView) {
                        targetView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        Glide.with(activity).load(bean.getImgId()).into(targetView);
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

//        iv_bigshow.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
//            @Override
//            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
//                Log.d("PhotoShowActivity", "setOnDoubleTapListener");
//                return false;
//            }
//
//            @Override
//            public boolean onDoubleTap(MotionEvent motionEvent) {
//                Log.d("PhotoShowActivity", "onDoubleTap");
//                return false;
//            }
//
//            @Override
//            public boolean onDoubleTapEvent(MotionEvent motionEvent) {
//                Log.d("PhotoShowActivity", "onDoubleTapEvent");
//                return false;
//            }
//        });
//        iv_bigshow.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
//            @Override
//            public void onViewTap(View view, float v, float v1) {
//                Log.d("PhotoShowActivity", "setOnViewTapListeneronViewTap");
//            }
//        });

        //检测是否点击图片上还是点击在图片外，要修改ScaleType
        iv_bigshow.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {//点击图片上
                Toast.makeText(activity, "点在了图片上", Toast.LENGTH_SHORT).show();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isShow) {
                            isShow = false;
                            topBarManage.dismiss();
                        } else {
                            isShow = true;
                            topBarManage.show();
                        }
                    }
                });

            }

            @Override
            public void onOutsidePhotoTap() {//点击图片外
                activity.finish();
            }
        });
        //长按的时候触发
//        iv_bigshow.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                Log.d("PhotoShowActivity", "setOnLongClickListener");
//                return false;
//            }
//        });
        //当图片被放大的时候触发
//        iv_bigshow.setOnMatrixChangeListener(new PhotoViewAttacher.OnMatrixChangedListener() {
//            @Override
//            public void onMatrixChanged(RectF rectF) {
//                Log.d("PhotoShowActivity", "onMatrixChanged");
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    Guide guide;

    public void showGuideView() {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(btn_trans)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {
            }
        });

        builder.addComponent(new SimpleComponent());
        guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(true);
        guide.show(this);
    }

    @Override
    public void finish() {
        super.finish();
        //渐出
        overridePendingTransition(0, R.anim.alpha_exit);
    }
}
