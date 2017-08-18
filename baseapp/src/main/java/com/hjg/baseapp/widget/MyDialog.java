package com.hjg.baseapp.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hjg.baseapp.R;

/**
 * 万能Dialog(只需要传递页面View进来，设置位置便拥有Dialog的效果)
 *
 * @author xiejinxiong
 */
public class MyDialog extends Dialog {

    /**
     * 设置Dialog布局排版
     *
     * @author xiejinxiong
     */
    public static enum DialogGravity {
        LEFTTOP, RIGHTTOP, CENTERTOP, CENTER, LEFTBOTTOM, RIGHTBOTTOM, CENTERBOTTOM
    }

    /**
     * Dialog的Window
     */
    private Window dialogWindow;
    /**
     * Dialog的布局数据
     */
    private WindowManager.LayoutParams dialogLayoutParams;

    public MyDialog(Context context) {
        this(context, R.style.MyDialogStyleBottom);
    }

    public MyDialog(Context context, int theme) {
        super(context, theme);
        initDialog();
    }

    /**
     * 初始化Dialog
     */
    private void initDialog() {
        dialogWindow = getWindow();
        dialogLayoutParams = dialogWindow.getAttributes();
        // 设置点击透明背景是否退出
        setCanceledOnTouchOutside(false);
    }


    /**
     * 设置Dialog布局
     *
     * @param layoutView
     */
    public void setLayoutView(View layoutView) {
        dialogWindow.setContentView(layoutView);
    }

    /**
     * 设置动画
     */
    public void setAnimations(int animations) {
        dialogWindow.setWindowAnimations(animations);
    }

    /**
     * 设置透明度
     * 从0到1
     *
     * @param alpha
     */
    public void setBgAlpha(float alpha) {
        dialogWindow.setDimAmount(alpha);
    }

    /**
     * 设置Dialog布局排版
     *
     * @param dialogGravity
     */
    public void setDialogGravity(DialogGravity dialogGravity) {

        switch (dialogGravity) {
            case LEFTTOP:
                dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
                break;
            case RIGHTTOP:
                dialogWindow.setGravity(Gravity.RIGHT | Gravity.TOP);
                break;
            case CENTERTOP:
                dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
                break;
            case CENTER:
                dialogWindow.setGravity(Gravity.CENTER);
                break;
            case LEFTBOTTOM:
                dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
                break;
            case RIGHTBOTTOM:
                dialogWindow.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
                break;
            case CENTERBOTTOM:
                dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                break;
            default:
                break;
        }
    }

    /**
     * 设置布局视图以及排版
     *
     * @param layoutView
     * @param dialogGravity
     */
    public void setLayoutGravity(View layoutView, DialogGravity dialogGravity) {
        setLayoutView(layoutView);
        setDialogGravity(dialogGravity);
    }

    /**
     * 设置Dialog布局
     *
     * @param layoutView
     * @param dialogGravity
     * @param height
     * @param width
     */
    public void setLayout(View layoutView, DialogGravity dialogGravity,
                          int height, int width) {
        setLayoutGravity(layoutView, dialogGravity);
        setLayoutHeightWidth(height, width);
    }

    /**
     * 设置布局的X，Y轴坐标
     *
     * @param x
     * @param y
     */
    public void setLayoutXY(int x, int y) {

        dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        dialogLayoutParams.x = x;
        dialogLayoutParams.y = y;
        dialogWindow.setAttributes(dialogLayoutParams);
    }

    /**
     * 设置布局的宽高
     *
     * @param height
     * @param width
     */
    public void setLayoutHeightWidth(int height, int width) {

        dialogLayoutParams.height = height;
        dialogLayoutParams.width = width;
        dialogWindow.setAttributes(dialogLayoutParams);
    }
}
