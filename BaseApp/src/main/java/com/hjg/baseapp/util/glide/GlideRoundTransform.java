package com.hjg.baseapp.util.glide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/*
 * @创建者     master
 * @创建时间   2016/11/2 10:30
 * @描述       下载图片转换层圆角图片的方法
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class GlideRoundTransform extends BitmapTransformation {

	/**
	 *使用方法
	 * private RequestManager glideRequest;
	 * glideRequest = Glide.with(this);
	 * glideRequest.load("https://www.baidu.com/img/bdlogo.png").transform(new GlideRoundTransform(context)).into(imageView);
	 *
	 */

	private static float radius = 6f;

	public GlideRoundTransform(Context context) {
		this(context, 4);
	}

	public GlideRoundTransform(Context context, int dp) {
		super(context);
		this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
	}

	@Override
	protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
		return roundCrop(pool, toTransform);
	}

	private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
		if (source == null)
			return null;
		Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
		if (result == null) {
			result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
		}
		Canvas canvas = new Canvas(result);
		Paint paint = new Paint();
		paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
		paint.setAntiAlias(true);
		RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
		canvas.drawRoundRect(rectF, radius, radius, paint);
		return result;
	}

	@Override
	public String getId() {
		return getClass().getName() + Math.round(radius);
	}
}
