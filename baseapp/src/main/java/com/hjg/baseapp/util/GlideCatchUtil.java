package com.hjg.baseapp.util;//package cn.com.xizhi.linktown.c;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Looper;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.File;
import java.math.BigDecimal;

/**
 * Glide图片加载Util
 */

public class GlideCatchUtil {
    private static GlideCatchUtil instance;

    public static GlideCatchUtil getInstance() {
        if (null == instance) {
            instance = new GlideCatchUtil();
        }
        return instance;
    }

    // 获取Glide磁盘缓存大小
    public String getCacheSize(Context context) {
        try {
            return getFormatSize(Double.valueOf(FileUtils.getDirLength(new File(context.getCacheDir() + "/" + "image_catch"))));
        } catch (Exception e) {
            e.printStackTrace();
            return "获取失败";
        }
    }

    // 清除图片磁盘缓存，调用Glide自带方法
    public boolean clearCacheDiskSelf(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 清除Glide内存缓存
    public boolean clearCacheMemory(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void loadImg(Activity at, String path, int reeorRes, ImageView iv) {
        Glide.with(at).load(path).error(reeorRes).into(iv);
    }

    public static void loadImg(Context at, String path, int reeorRes, ImageView iv) {
        Glide.with(at).load(path).error(reeorRes).into(iv);
    }


    public static void loadResImg(Activity at, int reeorRes, ImageView iv) {
        Glide.with(at).load(reeorRes).into(iv);
    }

    public static void loadResImg(Context at, int reeorRes, ImageView iv) {
        Glide.with(at).load(reeorRes).into(iv);
    }

    public static void loadRoundmg(final Activity at, String path, int reeorRes, final ImageView iv) {
        Glide.with(at).load(path).asBitmap().centerCrop().error(reeorRes).into(new BitmapImageViewTarget(iv) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(at.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                iv.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    //    public static void setImageSrc(final SimpleDraweeView draweeView, Uri uri, int width, int height) {
    //        ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(uri);
    //        if (width > 0 && height > 0) {
    //            builder.setResizeOptions(new ResizeOptions(width, height));
    //        }
    //        ImageRequest request = builder.build();
    //        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
    //                .setImageRequest(request)
    //                .setTapToRetryEnabled(true)
    //                .setOldController(draweeView.getController())
    //                .build();
    //
    //        draweeView.setController(controller);
    //    }
    //
    //    public static GenericDraweeHierarchy setImagRound(Context ct) {
    //        //初始化圆角圆形参数对象
    //        RoundingParams rp = new RoundingParams();
    //        //设置图像是否为圆形
    //        //rp.setRoundAsCircle(true);
    //
    //        //获取GenericDraweeHierarchy对象
    //        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(ct.getResources())
    //                //设置圆形圆角参数
    //                //.setRoundingParams(rp)
    //                //设置圆角半径
    //                //.setRoundingParams(RoundingParams.fromCornersRadius(20))
    //                //分别设置左上角、右上角、左下角、右下角的圆角半径
    //                .setRoundingParams(RoundingParams.fromCornersRadii(ScreenUtils.dp2px(ct, 5), ScreenUtils.dp2px(ct, 5), ScreenUtils.dp2px(ct, 5), ScreenUtils.dp2px(ct, 5)))
    //                //设置圆形圆角参数；RoundingParams.asCircle()是将图像设置成圆形
    //                //.setRoundingParams(RoundingParams.asCircle())
    //                //设置淡入淡出动画持续时间(单位：毫秒ms)
    //                //                .setFadeDuration(5000)
    //                //构建
    //                .build();
    //
    //        //设置Hierarchy
    //        return hierarchy;
    //
    //    }
    /* 格式化单位
    *
            * @param size size
    * @return size
    */
    private static String getFormatSize(double size) {

        double kiloByte = size / 1000;
        if (kiloByte == 0) {
            return "0B";
        }

        if (kiloByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(size));
            return result1.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "B";
        }

        double megaByte = kiloByte / 1000;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1000;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1000;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}
