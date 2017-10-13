package com.hjg.hjgapplife.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.hjg.baseapp.util.BitmapUtils;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;
import com.hjg.hjgapplife.zxing.utils.BarcodeCreateUtil;

import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class CreateTwoBarActivity extends BaseOthreRenderSwipActivity {


    @BindView(R.id.yibar)
    ImageView yibar;
    @BindView(R.id.erbar)
    ImageView erbar;
    private Bitmap bitmap, bitmapTwo;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_create_two_bar;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "生成二维码界面");
    }

    @Override
    protected void initData() {
        try {
            //条形码不允许包含汉字
            bitmap = BarcodeCreateUtil.creatBarcode(activity, "123456456", 700, 200, false);
            bitmapTwo = BarcodeCreateUtil.createBarcode1("这是二维码", 800, 800);
            yibar.setImageBitmap(bitmap);
            erbar.setImageBitmap(bitmapTwo);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void finish() {
        super.finish();
        //关闭窗体动画显示
        this.overridePendingTransition(0, R.anim.startactivity_down_exit);
    }


    @OnClick({R.id.yibar, R.id.erbar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.yibar:
                break;
            case R.id.erbar:
                break;
        }
    }

    @OnLongClick({R.id.yibar, R.id.erbar})
    public boolean onViewLongClick(View view) {
        switch (view.getId()) {
            case R.id.yibar:
                try {
                    BitmapUtils.saveBitmap("一维码.png", bitmap);
                    Toast.makeText(activity, "保存成功", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    Toast.makeText(activity, "保存失败", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case R.id.erbar:
                try {
                    BitmapUtils.saveBitmap("二维码.png", bitmapTwo);
                    Toast.makeText(activity, "保存成功", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    Toast.makeText(activity, "保存失败", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
        }
        return true;
    }
}
