package com.hjg.hjgapplife.zxing.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;

import com.hjg.baseapp.util.Logs;

import java.io.FileNotFoundException;

/**
 */
public class BitmapDecodeUtil {

    public static Bitmap decodeSampledBitmapFromUri(Context context, Uri uri, int requestSize) {
        ContentResolver resolver = context.getContentResolver();
        Bitmap bitmap = null;
        try {
            // 先解密图片边界，确定原始图片大小
            Options boundOptions = new Options();
            boundOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(resolver.openInputStream(uri), null, boundOptions);

            // 根据请求图片大小，计算实际解码的图片尺寸
            Options sizeOptions = new Options();
            sizeOptions.inJustDecodeBounds = false;
            sizeOptions.inSampleSize = calculateInSampleSize(boundOptions, requestSize, requestSize);
            bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri), null, sizeOptions);
        } catch (FileNotFoundException e) {
            Logs.e("BitmapDecodeUtil", e.getMessage());
        }
        return bitmap;
    }

    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}