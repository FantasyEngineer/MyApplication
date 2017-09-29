package com.hjg.baseapp.widget.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.hjg.baseapp.R;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


public class LoadingProgressDialog extends ProgressDialog {

	private AnimationDrawable mAnimation;
	private Context mContext;
	private GifImageView mImageView;
	private String mLoadingTip;
	private TextView mLoadingTv;
	private GifDrawable gifDrawable;

	public LoadingProgressDialog(Context context, String content) {
		super(context);
		this.mContext = context;
		this.mLoadingTip = content;
		setCanceledOnTouchOutside(false);
		setCancelable(true);
	}
	public LoadingProgressDialog(Context context, int theme, String content) {
		super(context,theme);
		this.mContext = context;
		this.mLoadingTip = content;
		setCanceledOnTouchOutside(false);
		setCancelable(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initData();
	}

	private void initData() {
		try {
			gifDrawable = new GifDrawable(mContext.getAssets(),"loading.gif");
			mImageView.setBackground(gifDrawable);
			gifDrawable.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		mAnimation = (AnimationDrawable) mImageView.getDrawable();
//		mImageView.post(new Runnable() {
//			@Override
//			public void run() {
//				mAnimation.start();
//
//			}
//		});
		if(mLoadingTip.equals("")){
			mLoadingTv.setText("加载中...");
		}else{
			mLoadingTv.setText(mLoadingTip);
		}

	}

	public void setContent(String str) {
		mLoadingTv.setText(str);
	}

	private void initView() {
		setContentView(R.layout.progress_dialog);
		mLoadingTv = (TextView) findViewById(R.id.loadingTv);
		mImageView = (GifImageView) findViewById(R.id.loadingIv);
	}

	@Override
	public void cancel() {
		super.cancel();
//		不能回收，因为webview里面公用一个dialog，如果被回收了，就会GG,可以在dialog显示的时候判断是否被回收，如果被回收再创建
//		gifDrawable.recycle();
	}
}
