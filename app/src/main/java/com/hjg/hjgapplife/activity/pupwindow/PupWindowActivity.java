package com.hjg.hjgapplife.activity.pupwindow;

import android.view.View;

import com.hjg.hjgapplife.activity.base.BaseListActivity;

import razerdp.popup.AutoLocatedPopup;
import razerdp.popup.CommentPopup;
import razerdp.popup.CustomInterpolatorPopup;
import razerdp.popup.DialogPopup;
import razerdp.popup.FullScreenPopup;
import razerdp.popup.InputPopup;
import razerdp.popup.ListPopup;
import razerdp.popup.MenuPopup;
import razerdp.popup.ScalePopup;
import razerdp.popup.SlideFromBottomPopup;
import razerdp.popup.SlideFromTopPopup;

public class PupWindowActivity extends BaseListActivity {

    private DialogPopup dialogPopup;

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "PupWindow各种样式展示");
        topBarManage.setLeftBtnBack(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initDataList() {
        dataList.add("自动定位的popup，空间不足显示在上面");
        dataList.add("微信朋友圈评论弹窗");
        dataList.add("各种插值器的popup");
        dataList.add("客串dialog(不响应返回键不响应空白)");
        dataList.add("全屏的popup");
        dataList.add("InputPopup");
        dataList.add("ListPopup");
        dataList.add("MenuPopup");
        dataList.add("ScalePopup");
        dataList.add("SlideFromBottomPopup");
        dataList.add("SlideFromTopPopup");
    }

    @Override
    public void OnItemClick(int i) {
        switch (i) {
            case 0:
                new AutoLocatedPopup(activity).showPopupWindow(topBarManage.getTopTextView());
                break;
            case 1:
                new CommentPopup(activity).showPopupWindow();
                break;
            case 2:
                new CustomInterpolatorPopup(activity).showPopupWindow();
                break;
            case 3:
                dialogPopup = new DialogPopup(activity);
                dialogPopup.setTitleAndContent("温馨提示", "测试是否响应返回键和不响应返回键，测试点击外部是否消失和外部不消失");
                dialogPopup.setSingleBtn("知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogPopup.dismiss();
                    }
                });
                dialogPopup.showPopupWindow();
                break;
            case 4:
                new FullScreenPopup(activity).showPopupWindow();
                break;
            case 5:
                new InputPopup(activity).showPopupWindow();
                break;
            case 6:
                new ListPopup.Builder(activity)
                        .addItem(1, "1")
                        .addItem(2, "2")
                        .addItem(3, "3")
                        .addItem(4, "4").build().showPopupWindow();
                break;
            case 7:
                new MenuPopup(activity).showPopupWindow();
                break;
            case 8:
                new ScalePopup(activity).showPopupWindow();
                break;
            case 9:
                new SlideFromBottomPopup(activity).showPopupWindow();
                break;
            case 10:
                new SlideFromTopPopup(activity).showPopupWindow();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if (dialogPopup != null && dialogPopup.isShowing()) {
            if (dialogPopup.getBackPressEnable()) {
                dialogPopup.dismiss();
            } else {
                //不响应返回键
            }
        } else {
            super.onBackPressed();
        }
    }
}
