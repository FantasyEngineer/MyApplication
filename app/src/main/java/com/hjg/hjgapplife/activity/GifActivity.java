package com.hjg.hjgapplife.activity;

import android.view.View;
import android.widget.Toast;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;

import java.io.IOException;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageButton;
import pl.droidsonroids.gif.GifImageView;
import pl.droidsonroids.gif.GifTextView;
import pl.droidsonroids.gif.MultiCallback;

/*//asset file
GifDrawable gifFromAssets = new GifDrawable( getAssets(), "anim.gif" );

//resource (drawable or raw)
        GifDrawable gifFromResource = new GifDrawable( getResources(), R.drawable.anim );

//Uri
        ContentResolver contentResolver = ... //can be null for file:// Uris
        GifDrawable gifFromUri = new GifDrawable( contentResolver, gifUri );

//byte array
        byte[] rawGifBytes = ...
        GifDrawable gifFromBytes = new GifDrawable( rawGifBytes );

//FileDescriptor
        FileDescriptor fd = new RandomAccessFile( "/path/anim.gif", "r" ).getFD();
        GifDrawable gifFromFd = new GifDrawable( fd );

//file path
        GifDrawable gifFromPath = new GifDrawable( "/path/anim.gif" );

//file
        File gifFile = new File(getFilesDir(),"anim.gif");
        GifDrawable gifFromFile = new GifDrawable(gifFile);

//AssetFileDescriptor
        AssetFileDescriptor afd = getAssets().openFd( "anim.gif" );
        GifDrawable gifFromAfd = new GifDrawable( afd );

//InputStream (it must support marking)
        InputStream sourceIs = ...
        BufferedInputStream bis = new BufferedInputStream( sourceIs, GIF_LENGTH );
        GifDrawable gifFromStream = new GifDrawable( bis );

//direct ByteBuffer
        ByteBuffer rawGifBytes = ...
        GifDrawable gifFromBytes = new GifDrawable( rawGifBytes );*/
public class GifActivity extends BaseOthreRenderSwipActivity {

    private GifImageView gifImageView;
    private GifImageView gifImageView2;
    private GifTextView gifTextView;
    private GifImageButton gifImageButton;
    private GifImageView gifImageViewMul;
    private GifImageView gifImageViewMul1;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_gif;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "GIF展示");
    }

    @Override
    protected void initData() {
        gifImageViewMul = (GifImageView) findViewById(R.id.gifImageViewMul);
        gifImageViewMul1 = (GifImageView) findViewById(R.id.gifImageViewMul1);

        //循环播放GifImageView,使用MediaController
        gifImageView = (GifImageView) findViewById(R.id.gifImageView);
        gifImageView.setImageResource(R.mipmap.scenery);
        final android.widget.MediaController mediaController = new android.widget.MediaController(this);
        mediaController.setMediaPlayer((GifDrawable) gifImageView.getDrawable());
        gifImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaController.show();
            }
        });

        //循环播放GifImageView,暂停和开始
        gifImageView2 = (GifImageView) findViewById(R.id.gifImageView2);
        try {
            final GifDrawable gifDrawable = new GifDrawable(getAssets(), "meinv.gif");
            gifImageView2.setBackground(gifDrawable);
            android.widget.MediaController mediaController2 = new android.widget.MediaController(this);
            mediaController2.setMediaPlayer(gifDrawable);
            mediaController2.show();
            gifDrawable.addAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationCompleted(int loopNumber) {
                    //暂停
                    gifDrawable.pause();
                }
            });
            gifImageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!gifDrawable.isRunning()) {
                        gifDrawable.start();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        //循环播放GifTextView
        gifTextView = (GifTextView) findViewById(R.id.gifTextView);
        final GifDrawable gifDrawableTextView;
        try {
            gifDrawableTextView = new GifDrawable(getAssets(), "meinv.gif");
            gifTextView.setBackground(gifDrawableTextView);
            android.widget.MediaController mediaController2 = new android.widget.MediaController(this);
            mediaController2.setMediaPlayer(gifDrawableTextView);
            mediaController2.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


        //循环播放GifButton
        gifImageButton = (GifImageButton) findViewById(R.id.gifImageButton);
        final GifDrawable gifDrawableBtn;
        try {
            gifDrawableBtn = new GifDrawable(getAssets(), "meinv.gif");
            gifImageButton.setBackground(gifDrawableBtn);
//            android.widget.MediaController mediaController2 = new android.widget.MediaController(this);
//            mediaController2.setMediaPlayer(gifDrawableBtn);
//            mediaController2.show();
            gifDrawableBtn.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        gifImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "点击了gifImageButton", Toast.LENGTH_SHORT).show();
            }
        });


        //同时应用多个，不推荐， 卡顿。
        MultiCallback multiCallback = new MultiCallback();
        GifDrawable gifDrawablemul = null;
        try {
            gifDrawablemul = new GifDrawable(getAssets(), "meinv.gif");
        } catch (Exception e) {
        }
        gifImageViewMul1.setBackground(gifDrawablemul);
        multiCallback.addView(gifImageButton);

        gifImageViewMul.setBackground(gifDrawablemul);
        multiCallback.addView(gifImageViewMul);

        gifDrawablemul.setCallback(multiCallback);
        gifDrawablemul.start();
    }


}
