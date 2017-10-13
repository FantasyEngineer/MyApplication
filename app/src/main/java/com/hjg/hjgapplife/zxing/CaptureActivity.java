package com.hjg.hjgapplife.zxing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.hjg.baseapp.util.Logs;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.CreateTwoBarActivity;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;
import com.hjg.hjgapplife.zxing.camera.CameraManager;
import com.hjg.hjgapplife.zxing.constants.Constants;
import com.hjg.hjgapplife.zxing.encode.QRCodeDecoder;
import com.hjg.hjgapplife.zxing.utils.AmbientLightManager;
import com.hjg.hjgapplife.zxing.utils.BeepManager;
import com.hjg.hjgapplife.zxing.utils.BitmapDecodeUtil;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;


/**
 * 扫一扫
 * 修改扫描框大小和距离上左的位置见CameraManager类，修改扫描框的颜色ViewfinderView类
 */
public final class CaptureActivity extends BaseOthreRenderSwipActivity implements
        SurfaceHolder.Callback, View.OnClickListener {

    private static final String TAG = CaptureActivity.class.getSimpleName();

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private Result savedResultToShow;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private AmbientLightManager ambientLightManager;

    //闪光灯是否打开的状态
    private boolean flast_lamp_status = false;
    private BeepManager beepManager;
    private SurfaceView surfaceView;
    private TextView creatBar;


    ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    CameraManager getCameraManager() {
        return cameraManager;
    }


    @Override
    protected int getContentLayout() {
        return R.layout.capture;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "二维码扫描");
        topBarManage.setRightButtonImgAndTxt(true, getResources().getDrawable(R.mipmap.ablum), "", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开相册
//                openAlbum();
                //打开闪光灯
                openFlashLamp();
            }
        });
    }

    @Override
    protected void initData() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        //扫码之后发出的声音
        beepManager = new BeepManager(this);
        ambientLightManager = new AmbientLightManager(this);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        creatBar = (TextView) findViewById(R.id.creatBar);
        creatBar.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        cameraManager = new CameraManager(getApplication());
        viewfinderView.setCameraManager(cameraManager);
        handler = null;
        resetStatusView();
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
//        beepManager.updatePrefs();
        ambientLightManager.start(cameraManager);
        inactivityTimer.onResume();
        decodeFormats = null;
        characterSet = null;
    }


    /**
     * 打开或者关闭闪光灯
     */
    public void openFlashLamp() {
        if (!flast_lamp_status) { // 打开闪光灯
            flast_lamp_status = true;
//            mFlashLamp.setText(R.string.flash_lamp_close);
            cameraManager.setTorch(true);
        } else { // 关闭闪光灯
            flast_lamp_status = false;
//            mFlashLamp.setText(R.string.flash_lamp_open);
            cameraManager.setTorch(false);
        }
    }

    /**
     * 打开相册
     */
    public void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "选择图片"),
                Constants.REQUEST_READ_ALBUM);
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        ambientLightManager.stop();
//        beepManager.close();
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finish();
            case KeyEvent.KEYCODE_FOCUS:
            case KeyEvent.KEYCODE_CAMERA:
                // Handle these events so they don't launch the Camera app
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result) {
        // Bitmap isn't used yet -- will be used soon
        if (handler == null) {
            savedResultToShow = result;
        } else {
            if (result != null) {
                savedResultToShow = result;
            }
            if (savedResultToShow != null) {
                Message message = Message.obtain(handler,
                        R.id.decode_succeeded, savedResultToShow);
                handler.sendMessage(message);
            }
            savedResultToShow = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult   The contents of the barcode.
     * @param scaleFactor amount by which thumbnail was scaled
     * @param barcode     A greyscale bitmap of the camera data which was decoded.
     */
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        inactivityTimer.onActivity();

        boolean fromLiveScan = barcode != null;
        if (fromLiveScan) {
            drawResultPoints(barcode, scaleFactor, rawResult);
        }

        handleDecodeInternally(rawResult, barcode);
    }

    /**
     * Superimpose a line for 1D or dots for 2D to highlight the key features of
     * the barcode.
     *
     * @param barcode     A bitmap of the captured image.
     * @param scaleFactor amount by which thumbnail was scaled
     * @param rawResult   The decoded results which contains the points to draw.
     */
    private void drawResultPoints(Bitmap barcode, float scaleFactor,
                                  Result rawResult) {
        ResultPoint[] points = rawResult.getResultPoints();
        if (points != null && points.length > 0) {
            Canvas canvas = new Canvas(barcode);
            Paint paint = new Paint();
            paint.setColor(getResources().getColor(R.color.orange));
            if (points.length == 2) {
                paint.setStrokeWidth(4.0f);
                drawLine(canvas, paint, points[0], points[1], scaleFactor);
            } else if (points.length == 4
                    && (rawResult.getBarcodeFormat() == BarcodeFormat.UPC_A || rawResult
                    .getBarcodeFormat() == BarcodeFormat.EAN_13)) {
                // Hacky special case -- draw two lines, for the barcode and
                // metadata
                drawLine(canvas, paint, points[0], points[1], scaleFactor);
                drawLine(canvas, paint, points[2], points[3], scaleFactor);
            } else {
                paint.setStrokeWidth(10.0f);
                for (ResultPoint point : points) {
                    if (point != null) {
                        canvas.drawPoint(scaleFactor * point.getX(),
                                scaleFactor * point.getY(), paint);
                    }
                }
            }
        }
    }

    private static void drawLine(Canvas canvas, Paint paint, ResultPoint a,
                                 ResultPoint b, float scaleFactor) {
        if (a != null && b != null) {
            canvas.drawLine(scaleFactor * a.getX(), scaleFactor * a.getY(),
                    scaleFactor * b.getX(), scaleFactor * b.getY(), paint);
        }
    }

    private void handleDecodeInternally(Result rawResult, Bitmap barcode) {
        feedbackScanResult(rawResult);
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG,
                    "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a
            // RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats,
                        decodeHints, characterSet, cameraManager);
            }
            decodeOrStoreSavedBitmap(null, null);
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
        }
    }


    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
        resetStatusView();
    }

    private void resetStatusView() {
        viewfinderView.setVisibility(View.VISIBLE);
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {
            if (requestCode == Constants.REQUEST_READ_ALBUM) { // 选择相册
                Uri originalUri = data.getData();
                if (originalUri == null) {
                    return;
                }

                Bitmap cameraBitmap = BitmapDecodeUtil.decodeSampledBitmapFromUri(this, originalUri, Constants.MAX_BITMAP_SIZE);
                if (cameraBitmap == null) {
                    return;
                }

                new QRCodeDecoder(CaptureActivity.this).decodeImageResource(
                        cameraBitmap, new QRCodeDecoder.QRCodeDecoderListener() {
                            @Override
                            public void parseResult(Bitmap bitmap, Result result) {
                                if (bitmap != null && !bitmap.isRecycled()) {
                                    bitmap.recycle();
                                    bitmap = null;
                                }
                                if (result != null) {
                                    feedbackScanResult(result);
                                } else {
                                }
                            }
                        });
            }
        } else {
        }
    }

    /**
     * 返回扫描结果
     */
    private void feedbackScanResult(Result result) {
        //发出声音
        beepManager.playBeepSoundAndVibrate();
        Logs.d("TAG", "twocode---->" + result.getText());
        Toast.makeText(this, result.getText(), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (handler != null) {
                    handler.restartPreviewAndDecode();
                }
            }
        }, 2000);
    }


    @Override
    public void onClick(View view) {
        startActivity(new Intent(activity, CreateTwoBarActivity.class));
        overridePendingTransition(R.anim.startactivity_up_enter, 0);
    }
}
