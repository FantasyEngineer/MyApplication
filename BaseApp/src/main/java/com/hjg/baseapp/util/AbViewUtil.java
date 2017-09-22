/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hjg.baseapp.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hjg.baseapp.R;
import com.hjg.baseapp.util.glide.GlideRoundTransform;

import java.io.InputStream;

/**
 * © 2012 amsoft.cn 名称：AbViewUtil.java 描述：View工具类.
 * 
 * @author 还如一梦中
 * @version v1.0
 * @date：2013-01-17 下午11:52:13
 */

@SuppressLint("NewApi")
public class AbViewUtil {

	/**
	 * 无效值
	 */
	public static final int INVALID = Integer.MIN_VALUE;

	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/**
	 * 计算GridView高度
	 */
	public static int setGridViewHeightBasedOnChildren(GridView listView,
			int col) {
		// 获取listview的adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return 0;
		}
		// 固定列宽，有多少列
		int totalHeight = 0;
		// i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
		// listAdapter.getCount()小于等于8时计算两次高度相加
		for (int i = 0; i < listAdapter.getCount(); i += col) {
			// 获取listview的每一个item
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			// 获取item的高度和
			totalHeight += listItem.getMeasuredHeight();
		}

		// 获取listview的布局参数
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		// 设置高度
		params.height = totalHeight + listView.getVerticalSpacing()
				* (listView.getCount());
		// 设置margin
		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		// 设置参数
		listView.setLayoutParams(params);
		return totalHeight;
	}

	/**
	 * 计算listView高度
	 * 
	 * @param listView
	 */
	public static void setListViewHeight(ListView listView) {
		if (listView == null)
			return;
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	/**
	 * 计算listView高度
	 * 
	 * @param listView
	 */

	public static void setListViewHeight(ListView listView, Context context) {
		if (listView == null)
			return;
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	/**
	 * 测量这个view，最后通过getMeasuredWidth()获取宽度和高度.
	 * 
	 * @param v
	 *            要测量的view
	 * @return 测量过的view
	 */
	public static void measureView(View v) {
		ViewGroup.LayoutParams p = v.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		v.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * 描述：dip转换为px.
	 * 
	 * @param context
	 *            the context
	 * @param dipValue
	 *            the dip value
	 * @return px值
	 */
	public static float dip2px(Context context, float dipValue) {
		DisplayMetrics mDisplayMetrics = AbAppUtil.getDisplayMetrics(context);
		return applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue,
				mDisplayMetrics);
	}

	/**
	 * 描述：px转换为dip.
	 * 
	 * @param context
	 *            the context
	 * @param pxValue
	 *            the px value
	 * @return dip值
	 */
	public static float px2dip(Context context, float pxValue) {
		DisplayMetrics mDisplayMetrics = AbAppUtil.getDisplayMetrics(context);
		return pxValue / mDisplayMetrics.density;
	}

	/**
	 * 描述：sp转换为px.
	 * 
	 * @param context
	 *            the context
	 * @param spValue
	 *            the sp value
	 * @return sp值
	 */
	public static float sp2px(Context context, float spValue) {
		DisplayMetrics mDisplayMetrics = AbAppUtil.getDisplayMetrics(context);
		return applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
				mDisplayMetrics);
	}

	/**
	 * 描述：px转换为sp.
	 * 
	 * @param context
	 *            the context
	 * @param pxValue
	 *            the sp value
	 * @return sp值
	 */
	public static float px2sp(Context context, float pxValue) {
		DisplayMetrics mDisplayMetrics = AbAppUtil.getDisplayMetrics(context);
		return pxValue / mDisplayMetrics.scaledDensity;
	}

	/**
	 * 描述：根据屏幕大小缩放.
	 * 
	 * @param context
	 *            the context
	 * @param value
	 *            the px value
	 * @return the int
	 */
	public static int scale(Context context, float value) {
		DisplayMetrics mDisplayMetrics = AbAppUtil.getDisplayMetrics(context);
		return scale(mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels,
				value);
	}

	/**
	 * 描述：根据屏幕大小缩放.
	 * 
	 * @param displayWidth
	 *            the display width
	 * @param displayHeight
	 *            the display height
	 * @param pxValue
	 *            the px value
	 * @return the int
	 */
	/** UI设计的基准宽度. */
	public static int uiWidth = 720;

	/** UI设计的基准高度. */
	public static int uiHeight = 1080;

	public static int scale(int displayWidth, int displayHeight, float pxValue) {
		float scale = 1;
		try {
			float scaleWidth = (float) displayWidth / uiWidth;
			float scaleHeight = (float) displayHeight / uiHeight;
			scale = Math.min(scaleWidth, scaleHeight);
		} catch (Exception e) {
		}
		return Math.round(pxValue * scale + 0.5f);
	}

	/**
	 * TypedValue官方源码中的算法，任意单位转换为PX单位
	 * 
	 * @param unit
	 *            TypedValue.COMPLEX_UNIT_DIP
	 * @param value
	 *            对应单位的值
	 * @param metrics
	 *            密度
	 * @return px值
	 */
	public static float applyDimension(int unit, float value,
			DisplayMetrics metrics) {
		switch (unit) {
		case TypedValue.COMPLEX_UNIT_PX:
			return value;
		case TypedValue.COMPLEX_UNIT_DIP:
			return value * metrics.density;
		case TypedValue.COMPLEX_UNIT_SP:
			return value * metrics.scaledDensity;
		case TypedValue.COMPLEX_UNIT_PT:
			return value * metrics.xdpi * (1.0f / 72);
		case TypedValue.COMPLEX_UNIT_IN:
			return value * metrics.xdpi;
		case TypedValue.COMPLEX_UNIT_MM:
			return value * metrics.xdpi * (1.0f / 25.4f);
		}
		return 0;
	}

	/**
	 * 按比例缩放View，以布局中的尺寸为基准
	 * 
	 * @param view
	 */
	public static void scaleView(View view) {
		if (view instanceof TextView) {
			TextView textView = (TextView) view;
			setTextSize(textView, textView.getTextSize());
		}

		ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) view
				.getLayoutParams();
		if (null != params) {
			int width = INVALID;
			int height = INVALID;
			if (params.width != ViewGroup.LayoutParams.WRAP_CONTENT
					&& params.width != ViewGroup.LayoutParams.MATCH_PARENT) {
				width = params.width;
			}

			if (params.height != ViewGroup.LayoutParams.WRAP_CONTENT
					&& params.height != ViewGroup.LayoutParams.MATCH_PARENT) {
				height = params.height;
			}

			// size
			setViewSize(view, width, height);

			// Padding
			setPadding(view, view.getPaddingLeft(), view.getPaddingTop(),
					view.getPaddingRight(), view.getPaddingBottom());
		}

		// Margin
		if (view.getLayoutParams() instanceof MarginLayoutParams) {
			MarginLayoutParams mMarginLayoutParams = (MarginLayoutParams) view
					.getLayoutParams();
			if (mMarginLayoutParams != null) {
				setMargin(view, mMarginLayoutParams.leftMargin,
						mMarginLayoutParams.topMargin,
						mMarginLayoutParams.rightMargin,
						mMarginLayoutParams.bottomMargin);
			}
		}

	}

	/**
	 * 缩放文字大小
	 *
	 * @param textView
	 *            button
	 * @param size
	 *            sp值
	 * @return
	 */
	public static void setSPTextSize(TextView textView, float size) {
		float scaledSize = scale(textView.getContext(), size);
		textView.setTextSize(scaledSize);
	}

	/**
	 * 缩放文字大小,这样设置的好处是文字的大小不和密度有关， 能够使文字大小在不同的屏幕上显示比例正确
	 *
	 * @param textView
	 *            button
	 * @param sizePixels
	 *            px值
	 * @return
	 */
	public static void setTextSize(TextView textView, float sizePixels) {
		float scaledSize = scale(textView.getContext(), sizePixels);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaledSize);
	}

	/**
	 * 缩放文字大小
	 *
	 * @param context
	 * @param textPaint
	 * @param sizePixels
	 *            px值
	 * @return
	 */
	public static void setTextSize(Context context, TextPaint textPaint,
                                   float sizePixels) {
		float scaledSize = scale(context, sizePixels);
		textPaint.setTextSize(scaledSize);
	}

	/**
	 * 缩放文字大小
	 *
	 * @param context
	 * @param paint
	 * @param sizePixels
	 *            px值
	 * @return
	 */
	public static void setTextSize(Context context, Paint paint,
                                   float sizePixels) {
		float scaledSize = scale(context, sizePixels);
		paint.setTextSize(scaledSize);
	}

	/**
	 * 设置View的PX尺寸
	 *
	 * @param view
	 *            如果是代码new出来的View，需要设置一个适合的LayoutParams
	 * @param widthPixels
	 * @param heightPixels
	 */
	public static void setViewSize(View view, int widthPixels, int heightPixels) {
		int scaledWidth = scale(view.getContext(), widthPixels);
		int scaledHeight = scale(view.getContext(), heightPixels);
		ViewGroup.LayoutParams params = view.getLayoutParams();
		if (params == null) {
			return;
		}
		if (widthPixels != INVALID) {
			params.width = scaledWidth;
		}
		if (heightPixels != INVALID) {
			params.height = scaledHeight;
		}
		view.setLayoutParams(params);
	}

	/**
	 * 设置PX padding.
	 *
	 * @param view
	 *            the view
	 * @param left
	 *            the left padding in pixels
	 * @param top
	 *            the top padding in pixels
	 * @param right
	 *            the right padding in pixels
	 * @param bottom
	 *            the bottom padding in pixels
	 */
	public static void setPadding(View view, int left, int top, int right,
                                  int bottom) {
		int scaledLeft = scale(view.getContext(), left);
		int scaledTop = scale(view.getContext(), top);
		int scaledRight = scale(view.getContext(), right);
		int scaledBottom = scale(view.getContext(), bottom);
		view.setPadding(scaledLeft, scaledTop, scaledRight, scaledBottom);
	}

	/**
	 * 设置 PX margin.
	 *
	 * @param view
	 *            the view
	 * @param left
	 *            the left margin in pixels
	 * @param top
	 *            the top margin in pixels
	 * @param right
	 *            the right margin in pixels
	 * @param bottom
	 *            the bottom margin in pixels
	 */
	public static void setMargin(View view, int left, int top, int right,
                                 int bottom) {
		int scaledLeft = scale(view.getContext(), left);
		int scaledTop = scale(view.getContext(), top);
		int scaledRight = scale(view.getContext(), right);
		int scaledBottom = scale(view.getContext(), bottom);

		if (view.getLayoutParams() instanceof MarginLayoutParams) {
			MarginLayoutParams mMarginLayoutParams = (MarginLayoutParams) view
					.getLayoutParams();
			if (mMarginLayoutParams != null) {
				if (left != INVALID) {
					mMarginLayoutParams.leftMargin = scaledLeft;
				}
				if (right != INVALID) {
					mMarginLayoutParams.rightMargin = scaledRight;
				}
				if (top != INVALID) {
					mMarginLayoutParams.topMargin = scaledTop;
				}
				if (bottom != INVALID) {
					mMarginLayoutParams.bottomMargin = scaledBottom;
				}
				view.setLayoutParams(mMarginLayoutParams);
			}
		}

	}

	/**
	 *  设置圆角图片
	 * @param imageView imageView ID
	 * @param imgUrl 路径
	 * @param imgRadian	弧度
	 */
	public static void filletImageView(Context context, ImageView imageView, String imgUrl, int imgRadian){

		Glide.with(context).load(imgUrl).error(R.mipmap.ic_luncher).transform(new GlideRoundTransform(context,imgRadian)).into(imageView);

	}
}
