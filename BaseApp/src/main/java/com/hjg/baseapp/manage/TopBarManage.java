package com.hjg.baseapp.manage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjg.baseapp.R;
import com.hjg.baseapp.widget.MyRadioButton;

/**
 * 管理头部导航栏
 *
 * @author jiguo_hou
 */
public class TopBarManage {
    private Context context;
    private View topBarView;
    private MyRadioButton leftButton;
    private MyRadioButton rightButton;
    private TextView TopTextView;
    private ImageView TopImg;
    private RelativeLayout topBar;

    /**
     * 构造函数，传人需要管理的头部view
     *
     * @param context
     * @param view
     */
    public TopBarManage(Context context, View... view) {
        this.context = context;
        commonInit(view);
    }

    /**
     * 获取头部导航栏内部的控件信息
     *
     * @param view
     */
    private void commonInit(View... view) {
        this.topBarView = view[0];
        TopImg = (ImageView) topBarView.findViewById(R.id.ivTopImageTitle);
        TopTextView = (TextView) topBarView.findViewById(R.id.tvTopTextTitle);
        leftButton = (MyRadioButton) topBarView.findViewById(R.id.btnTopLeft);
        rightButton = (MyRadioButton) topBarView.findViewById(R.id.btnTopRight);
    }

    public void setTopBarPaddingTop(int padding) {
        topBarView.setPadding(0, padding, 0, 0);
    }

    //设置背景色
    public void setTopBarBackground(int color) {
        topBarView.setBackgroundColor(color);
    }

    //设置背景透明度
    public void setTopBarBackgroundAlpha(int alpha) {
        topBarView.getBackground().setAlpha(alpha);
    }

    /**
     * 顶部头部是否可见
     *
     * @param show
     */
    public void isVisibleTopbar(boolean show) {
        topBarView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public boolean getIsVisiable() {
        return topBarView.getVisibility() == View.VISIBLE ? true : false;
    }

    /**
     * 初始化导航栏中央文字
     *
     * @param txt
     */
    public void iniTop(boolean isShow, String txt) {
        if (!isShow) {
            return;
        }
        TopTextView.setVisibility(View.VISIBLE);
        TopTextView.setText(txt);
    }

    /**
     * 初始化导航栏图片（使用于首页顶部中央是图片的）
     *
     * @param resid
     */
    public void iniTop(boolean isShow, int resid) {
        if (!isShow) {
            return;
        }
        TopImg.setVisibility(View.VISIBLE);
        TopImg.setImageResource(resid);

    }

    public void setLeftBtnBack(boolean isShow, View.OnClickListener listener) {
        setLeftButtonImgAndTxt(isShow, context.getDrawable(R.mipmap.icon_back), "返回", listener);
    }

    /**
     * 设置导航栏左边按钮图片+文字
     *
     * @param drawable
     * @param isShow
     * @param listener
     */
    public void setLeftButtonImgAndTxt(boolean isShow, Drawable drawable, String txt, View.OnClickListener listener) {
        setButtonImgAndTxt(leftButton, drawable, txt, isShow, listener);
    }

    /**
     * 设置导航栏右边按钮图片
     *
     * @param drawable
     * @param isShow
     * @param listener
     */
    public void setRightButtonImgAndTxt(boolean isShow, Drawable drawable, String txt, View.OnClickListener listener) {
        setButtonImgAndTxt(rightButton, drawable, txt, isShow, listener);
    }


    private void setButtonImgAndTxt(MyRadioButton btn, Drawable drawable, String txt, boolean isShow, View.OnClickListener listener) {
        if (!isShow) {
            btn.setVisibility(View.GONE);
            return;
        }
        try {
            btn.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        } catch (Exception e) {
            btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        btn.setText(txt);
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(listener);
    }

    /**
     * 获取到中间标题控件
     *
     * @return
     */
    public TextView getTopTextView() {
        return TopTextView;
    }

    /**
     * 获取左边控件
     *
     * @return
     */
    public RadioButton getLeftBtn() {
        return leftButton;
    }

    /**
     * 获取右边控件
     *
     * @return
     */
    public RadioButton getRightBtn() {
        return rightButton;
    }
}
