package com.hjg.baseapp.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.hjg.baseapp.R;
import com.hjg.baseapp.widget.GifView;


/**
 * GIFView
 */

public class GifDialog extends Dialog {

    private String message = "";

    private TextView mTextView;

    public GifDialog(Context context) {
        this(context, R.style.CustomProgressDialog);
        setCanceledOnTouchOutside(false);
    }

    public GifDialog(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.layout_gif_dialog);
        initialViews();
    }

    private void initialViews() {
        mTextView = (TextView) findViewById(R.id.tv_httpdialog);
        mTextView.setText("请稍候...");
        GifView gif = (GifView) findViewById(R.id.gif_http);
        gif.setMovieResource(R.mipmap.loading);
    }

    public void setMessage(String message) {
        this.message = message;
        mTextView.setText(message);
    }

    public void setColor() {
        mTextView.setTextColor(Color.RED);
    }

    public String getMessage() {
        return message;
    }
}
