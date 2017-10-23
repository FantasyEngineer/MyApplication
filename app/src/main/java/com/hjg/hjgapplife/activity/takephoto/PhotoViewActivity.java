package com.hjg.hjgapplife.activity.takephoto;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipFinalActivity;
import com.hjg.hjgapplife.activity.baseRender.LayoutConstans;

import butterknife.BindView;
import immortalz.me.library.TransitionsHeleper;
import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.method.InflateShowMethod;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoViewActivity extends BaseOthreRenderSwipFinalActivity {


    @BindView(R.id.photoView)
    PhotoView photoView;
    private boolean isShow = true;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_photo_view;
    }

    @Override
    protected int getIncludeLayoutType() {
        return LayoutConstans.RL;
    }

    @Override
    protected void initTitle() {
        topBarManage.setVisibleTopbar(true);
        topBarManage.iniTop(true, "photoView展示");
    }

    @Override
    protected void initData() {
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
                .show(activity, photoView);


        //检测是否点击图片上还是点击在图片外，要修改ScaleType
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {//点击图片上
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
    }


//    public static void startActivity(Activity activity, String url) {
//        activity.startActivity(new Intent(activity, PhotoViewActivity.class));
//        activity.overridePendingTransition(R.anim.alpha_enter, 0);
//    }

    @Override
    public void finish() {
        super.finish();
        //渐出
        overridePendingTransition(0, R.anim.alpha_exit);
    }
}
