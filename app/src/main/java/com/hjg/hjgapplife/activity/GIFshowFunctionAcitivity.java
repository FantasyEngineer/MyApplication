package com.hjg.hjgapplife.activity;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;
import com.hjg.hjgapplife.activity.webview.WebViewActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class GIFshowFunctionAcitivity extends BaseOthreRenderSwipActivity {


    @BindView(R.id.gifImageView)
    GifImageView gifImageView;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_gifshow_function_acitivity;
    }

    @Override
    protected void initTitle() {
        hideTopBar();
//        topBarManage.iniTop(true, "GIF展示效果");
    }

    @Override
    protected void initData() {
        try {
            GifDrawable gifDrawable = new GifDrawable(getAssets(), "crop.gif");
            gifImageView.setBackground(gifDrawable);
            gifDrawable.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.gifImageView)
    public void onViewClicked() {
        finish();

        WebViewActivity.startActivityToWebView(activity, "https://github.com/fengyuanchen/cropper", "图片裁剪");
    }


}
