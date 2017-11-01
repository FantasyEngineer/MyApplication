package com.hjg.hjgapplife.activity.ScreenFit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.flyco.dialog.widget.NormalDialog;
import com.hjg.baseapp.util.ScreenUtils;
import com.hjg.baseapp.widget.dialog.BottomDialog;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOtherRenderListActivity;
import com.hjg.hjgapplife.activity.webview.WebViewActivity;

public class ScreenFitMainActivity extends BaseOtherRenderListActivity {


    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "屏幕适配");
    }

    @Override
    public void initDataList() {
        dataList.add("自动生成dimens文件夹");
        dataList.add("获取屏幕相关属性");
        dataList.add("屏幕适配案例");
        dataList.add("屏幕适配相关博客");
    }

    @Override
    public void OnItemClick(int i) {
        switch (i) {
            case 0:
                NormalDialog normalDialog = new NormalDialog(activity);
                normalDialog.dividerColor(activity.getResources().getColor(R.color.orange));
                normalDialog.titleLineColor(activity.getResources().getColor(R.color.orange));
                normalDialog.titleTextColor(activity.getResources().getColor(R.color.orange));
                normalDialog.btnTextColor(activity.getResources().getColor(R.color.black));
                normalDialog.content("工程中有一个GenerateValueFiles文件，可以直接运行其中的main方法，得到主流机型分辨率下对应的dp对应px值，可以减少屏幕适配的工作量。");
                normalDialog.btnNum(1);
                normalDialog.setOnBtnClickL(() -> {
                    normalDialog.dismiss();
                });
                normalDialog.btnText("知道了");
                normalDialog.title("温馨提示");
                normalDialog.setCanceledOnTouchOutside(false);
                //展示动画
                normalDialog.show(R.style.show_dialog_scale_anim_style);
                break;
            case 1:
                StringBuilder stringBuilder = new StringBuilder();
                BottomDialog bottomDialog = new BottomDialog(activity);
                stringBuilder.append("当前手机适配的value文件夹是：" + getResources().getString(R.string.value) + "; \n");
                stringBuilder.append("屏幕高分辨率：" + ScreenUtils.getScreenHeight(activity) + "; ");
                stringBuilder.append("屏幕宽分辨率：" + ScreenUtils.getScreenWidth(activity) + "; \n");
                stringBuilder.append("\n屏幕密度Desity(DesityDPI / 160)：" + ScreenUtils.getDesity(activity) + "（相当于1dp = " + ScreenUtils.getDesity(activity) + "px; \n");
                stringBuilder.append("屏幕像素密度DesityDPI：" + ScreenUtils.getDesityDPI(activity) + "; \n");
                stringBuilder.append("手机尺寸（斜对角长度）：" + ScreenUtils.getScreenSize(activity) + "; \n");
                stringBuilder.append("手机屏幕像素密度PPI(官方介绍)：" + ScreenUtils.getScreenPPI(activity) + "; \n");
                stringBuilder.append("\n注意事项：\n");
                stringBuilder.append("ldpi->240*320分辨率下120dpi，所以1dp=1*0.75 px；\n");
                stringBuilder.append("mdpi->480*320分辨率下160dpi，所以1dp=1px；\n");
                stringBuilder.append("hdpi->480*800分辨率下240dpi，所以1dp=1.5px；\n");
                stringBuilder.append("xhdpi->720*1280分辨率下320dpi，所以1dp=2px；\n");
                stringBuilder.append("xxhdpi->1080*1920分辨率下480dpi，所以1dp=3px；\n");
                stringBuilder.append("xxxhdpi->3840×2160 4k超清 目前无具体数据；\n");
                bottomDialog.showDialog(stringBuilder.toString());
                break;
            case 2:
                startActivity(new Intent(activity, ScreenFitDemoActivity.class));
                break;
            case 3:
                WebViewActivity.startActivityToWebView(activity, "http://blog.csdn.net/zhangjin12312/article/details/78329811", "屏幕适配方案");
                break;
        }
    }
}
