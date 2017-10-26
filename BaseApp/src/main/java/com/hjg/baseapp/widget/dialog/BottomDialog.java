package com.hjg.baseapp.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hjg.baseapp.R;
import com.hjg.baseapp.util.ScreenUtils;
import com.hjg.baseapp.widget.BounceScrollView;


/**
 * 从底部弹出的dialog，支持下滑退出dialog
 */

public  class BottomDialog {
    private Activity activity;
    private Dialog dialog;
    private int lastY, y;
    private int offsetY;

    public BottomDialog(Activity activity) {
        this.activity = activity;
    }

    public void showDialog(final String content) {
        View view = activity.getLayoutInflater().inflate(R.layout.bottom_dialog, null);
        final TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
        BounceScrollView bounceScrollView = (BounceScrollView) view.findViewById(R.id.bounceScrollView);
        bounceScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                y = (int) motionEvent.getY();
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastY = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        offsetY = y - lastY;
                        break;
                    case MotionEvent.ACTION_UP:
//                        if (offsetY > ScreenUtils.dp2px(activity, 200)) {
                        if (offsetY > ScreenUtils.getScreenHeight(activity) / 3) {
                            dialog.dismiss();
                        }
                        break;
                }
                return false;
            }
        });
        tvContent.setText(content);

        dialog = new Dialog(activity, R.style.CustomProgressDialog);
        dialog.setContentView(view);

        // 设置显示动画
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.bottom_show_dialog_anim_style);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        wl.gravity = Gravity.BOTTOM;
        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

}
