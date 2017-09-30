package com.hjg.hjgapplife.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.hjg.baseapp.util.StringUtils;
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
    private String flag;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_gifshow_function_acitivity;
    }

    @Override
    protected void initTitle() {
        hideTopBar();
    }

    @Override
    protected void initData() {
        String gifName = "";
        flag = getIntent().getStringExtra("flag");
        if (StringUtils.isBlank(flag)) {
            Toast.makeText(activity, "未声明来源", Toast.LENGTH_SHORT).show();
            return;
        }
        //根据页面不同，加载不同的GIF
        switch (flag) {
            case "crop":
                gifName = "crop.gif";
                break;

        }
        GifDrawable gifDrawable = null;
        try {
            gifDrawable = new GifDrawable(getAssets(), gifName);
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


    /**
     * 供外部调用跳转到本activity
     *
     * @param context
     * @param flag
     */
    public static void startActivityToGIFView(Context context, String flag) {
        Intent intent = new Intent(context, GIFshowFunctionAcitivity.class);
        intent.putExtra("flag", flag);
        context.startActivity(intent);
    }


}
