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

package com.hjg.hjgapplife.zxing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.zxing.camera.CameraManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial
 * transparency outside it, as well as the laser scanner animation and result points.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ViewfinderView extends View {

    private static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192, 128, 64};
    private static final long ANIMATION_DELAY = 80L;
    private static final int CURRENT_POINT_OPACITY = 0xA0;
    private static final int MAX_RESULT_POINTS = 20;
    private static final int POINT_SIZE = 6;
    private static final int TEXT_SIZE = 16;
    private static final int TEXT_PADDING_TOP = 30;
    private static float density;

    private CameraManager cameraManager;
    private final Paint paint;
    private Bitmap resultBitmap;
    private final int maskColor;
    private final int resultColor;
    private final int laserColor;
    private final int resultPointColor;
    private final int cornerColor;
    private int scannerAlpha;
    private List<ResultPoint> possibleResultPoints;
    private List<ResultPoint> lastPossibleResultPoints;

    // This constructor is used when the class is built from an XML resource.
    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize these once for performance rather than calling them every time in onDraw().
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Resources resources = getResources();
        maskColor = resources.getColor(R.color.viewfinder_mask);
        resultColor = resources.getColor(R.color.result_view);
        laserColor = resources.getColor(R.color.viewfinder_laser);
        resultPointColor = resources.getColor(R.color.possible_result_points);
        cornerColor = resources.getColor(R.color.viewfinder_corner);
        scannerAlpha = 0;
        possibleResultPoints = new ArrayList<ResultPoint>(5);
        lastPossibleResultPoints = null;
        density = context.getResources().getDisplayMetrics().density;
    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        if (cameraManager == null) {
            return; // not ready yet, early draw before done configuring
        }
        Rect frame = cameraManager.getFramingRect();
        Rect previewFrame = cameraManager.getFramingRectInPreview();
        if (frame == null || previewFrame == null) {
            return;
        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        float cornerWidth = Math.min(frame.width(), frame.height()) / 10.0f;
        paint.setColor(cornerColor);

        canvas.drawRect(frame.left, frame.top, frame.left + cornerWidth, frame.top + 10, paint);
        canvas.drawRect(frame.right - cornerWidth, frame.top, frame.right, frame.top + 10, paint);

        canvas.drawRect(frame.left, frame.bottom - 10, frame.left + cornerWidth, frame.bottom, paint);
        canvas.drawRect(frame.right - cornerWidth, frame.bottom - 10, frame.right, frame.bottom, paint);

        canvas.drawRect(frame.left, frame.top, frame.left + 10, frame.top + cornerWidth, paint);
        canvas.drawRect(frame.left, frame.bottom - cornerWidth, frame.left + 10, frame.bottom, paint);

        canvas.drawRect(frame.right - 10, frame.top, frame.right, frame.top + cornerWidth, paint);
        canvas.drawRect(frame.right - 10, frame.bottom - cornerWidth, frame.right, frame.bottom, paint);

        //绘制扫描框下面的文字
        paint.setColor(getResources().getColor(R.color.status_text));
        paint.setTextSize(TEXT_SIZE * density);
        paint.setTypeface(Typeface.create("System", Typeface.BOLD));
        paint.setTextAlign(Align.CENTER);
        if (scanTxt != null && !scanTxt.equals("对准取景框，可扫描二维码")) {
            canvas.drawText(scanTxt,
                    width / 2, (float) (frame.bottom + (float) TEXT_PADDING_TOP * density), paint);
        } else if (scanTxt != null) {
            canvas.drawText("对准取景框，可扫描二维码",
                    width / 2, (float) (frame.bottom + (float) TEXT_PADDING_TOP * density), paint);
            canvas.drawText("",
                    width / 2, (float) (frame.bottom + 2 * ((float) TEXT_PADDING_TOP * density)), paint);
        }
//	canvas.drawText(getResources().getString(R.string.msg_default_status),
//			width / 2, (float) (frame.bottom + (float)TEXT_PADDING_TOP *density), paint);

        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(resultBitmap, null, frame, paint);
        } else {

            // Draw a red "laser scanner" line through the middle to show decoding is active
            paint.setColor(laserColor);
            paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
            scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
            int middle = frame.height() / 2 + frame.top;
            canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);

            float scaleX = frame.width() / (float) previewFrame.width();
            float scaleY = frame.height() / (float) previewFrame.height();

            List<ResultPoint> currentPossible = possibleResultPoints;
            List<ResultPoint> currentLast = lastPossibleResultPoints;
            int frameLeft = frame.left;
            int frameTop = frame.top;
            if (currentPossible.isEmpty()) {
                lastPossibleResultPoints = null;
            } else {
                possibleResultPoints = new ArrayList<ResultPoint>(5);
                lastPossibleResultPoints = currentPossible;
                paint.setAlpha(CURRENT_POINT_OPACITY);
                paint.setColor(resultPointColor);
                synchronized (currentPossible) {
                    for (ResultPoint point : currentPossible) {
                        canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                                frameTop + (int) (point.getY() * scaleY),
                                POINT_SIZE, paint);
                    }
                }
            }
            if (currentLast != null) {
                paint.setAlpha(CURRENT_POINT_OPACITY / 2);
                paint.setColor(resultPointColor);
                synchronized (currentLast) {
                    float radius = POINT_SIZE / 2.0f;
                    for (ResultPoint point : currentLast) {
                        canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                                frameTop + (int) (point.getY() * scaleY),
                                radius, paint);
                    }
                }
            }

            // Request another update at the animation interval, but only repaint the laser line,
            // not the entire viewfinder mask.
            postInvalidateDelayed(ANIMATION_DELAY,
                    frame.left - POINT_SIZE,
                    frame.top - POINT_SIZE,
                    frame.right + POINT_SIZE,
                    frame.bottom + POINT_SIZE);
        }
    }

    public void drawViewfinder() {
        Bitmap resultBitmap = this.resultBitmap;
        this.resultBitmap = null;
        if (resultBitmap != null) {
            resultBitmap.recycle();
        }
        invalidate();
    }

    /**
     * Draw a bitmap with the result points highlighted instead of the live scanning display.
     *
     * @param barcode An image of the decoded barcode.
     */
    public void drawResultBitmap(Bitmap barcode) {
        resultBitmap = barcode;
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
        List<ResultPoint> points = possibleResultPoints;
        synchronized (points) {
            points.add(point);
            int size = points.size();
            if (size > MAX_RESULT_POINTS) {
                // trim it
                points.subList(0, size - MAX_RESULT_POINTS / 2).clear();
            }
        }
    }

    private String scanTxt = "对准取景框，可扫描二维码";

    //动态修改扫码提示信息
    public void updateViewFinderText(String txt) {
        String result = "对准取景框，可扫描二维码";
//        if (txt.equals("SCANCODE")) {
//            scanTxt = result;
//        } else if (txt.equals("ATM")) {
//            scanTxt = "请将手机对准ATM屏幕二维码";//请将手机对准ATM二维码  轻松扫一扫即可取款
//        } else if (txt.equals("CARD")) {
//            scanTxt = "将二维码放入框内，即可自动扫描";
//        } else if (txt.equals("UPLOADCARD")) {
//            scanTxt = "将二维码放入框内，即可自动扫描";
//        }
        invalidate();
    }

}
