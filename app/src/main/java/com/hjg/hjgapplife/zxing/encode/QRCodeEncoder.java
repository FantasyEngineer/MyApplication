/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hjg.hjgapplife.zxing.encode;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.EnumMap;
import java.util.Map;

/**
 * 根据字符串生成二维码图片
 * 
 * @author tongxu_li Copyright (c) 2014 Shanghai P&C Information Technology Co.,
 *         Ltd.
 */
public class QRCodeEncoder {

	private static final String TAG = QRCodeEncoder.class.getSimpleName();

	private static final int WHITE = 0xFFFFFFFF;
	private static final int BLACK = 0xFF000000;

	private BarcodeFormat format;
	private int dimension;
	private Map<EncodeHintType, Object> encodeHints;
	private Bitmap bitIcon;// 二维码中心logo

	/**
	 * 默认二维码大小600
	 * 
	 * @author flueky zkf@yitong.com.cn
	 * @date 2015-3-25 下午4:23:24
	 */
	public QRCodeEncoder() {
		this(600);
	}

	public QRCodeEncoder(int dimension) {
		this(BarcodeFormat.QR_CODE, null, dimension);
	}

	public QRCodeEncoder(BarcodeFormat format,
			Map<EncodeHintType, Object> hints, int dimension) {
		this.format = format;
		this.encodeHints = hints;
		if (encodeHints == null) {
			encodeHints = new EnumMap<EncodeHintType, Object>(
					EncodeHintType.class);
			encodeHints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			encodeHints.put(EncodeHintType.ERROR_CORRECTION,
					ErrorCorrectionLevel.H);
		}
		this.dimension = dimension;
	}

	public void setBitIcon(Bitmap bitIcon) {
		this.bitIcon = bitIcon;
	}

	/**
	 * 生成二维码图片
	 */
	public Bitmap encodeAsBitmap(String contents) throws WriterException {
		String contentsToEncode = contents;
		if (contentsToEncode == null) {
			return null;
		}

		BitMatrix result;
		try {
			result = new MultiFormatWriter().encode(contentsToEncode, format,
					dimension, dimension, encodeHints);
		} catch (IllegalArgumentException iae) {
			// Unsupported format
			return null;
		}
		int width = result.getWidth();
		int height = result.getHeight();
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			int offset = y * width;
			for (int x = 0; x < width; x++) {
				pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
			}
		}
		Bitmap qrCodeBit = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		qrCodeBit.setPixels(pixels, 0, width, 0, 0, width, height);
		if (bitIcon != null) {
			bitIcon = compress(bitIcon, 60, 60);
			// 二维码和logo合并
			Bitmap bitmap = Bitmap.createBitmap(qrCodeBit.getWidth(),
					qrCodeBit.getHeight(), qrCodeBit.getConfig());
			Canvas canvas = new Canvas(bitmap);
			// 二维码
			canvas.drawBitmap(qrCodeBit, 0, 0, null);
			// logo绘制在二维码中央
			canvas.drawBitmap(bitIcon,
					qrCodeBit.getWidth() / 2 - bitIcon.getWidth() / 2,
					qrCodeBit.getHeight() / 2 - bitIcon.getHeight() / 2, null);
			qrCodeBit = bitmap;
		}
		return qrCodeBit;
	}

	/**
	 * 根据尺寸，缩放图片
	 * 
	 * @author flueky zkf@yitong.com.cn
	 * @date 2015-3-25 下午4:47:06
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	public Bitmap compress(Bitmap bitmap, int w, int h) {
		Bitmap BitmapOrg = bitmap;
		int width = BitmapOrg.getWidth();
		int height = BitmapOrg.getHeight();
		int newWidth = w;
		int newHeight = h;

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
				height, matrix, true);
		return resizedBitmap;
	}

}
