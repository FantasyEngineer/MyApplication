package com.hjg.hjgapplife.activity.takephoto.glide;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;

import java.util.ArrayList;
import java.util.List;

public class GlideUseActivity extends BaseOthreRenderSwipActivity {


    @Override
    protected int getContentLayout() {
        return R.layout.activity_glide_use;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "图片处理");
    }

    @Override
    protected void initData() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<GlideAdapter.Type> dataSet = new ArrayList<>();
        dataSet.add(GlideAdapter.Type.Mask);
        dataSet.add(GlideAdapter.Type.NinePatchMask);
        dataSet.add(GlideAdapter.Type.CropTop);
        dataSet.add(GlideAdapter.Type.CropCenter);
        dataSet.add(GlideAdapter.Type.CropBottom);
        dataSet.add(GlideAdapter.Type.CropSquare);
        dataSet.add(GlideAdapter.Type.CropCircle);
        dataSet.add(GlideAdapter.Type.ColorFilter);
        dataSet.add(GlideAdapter.Type.Grayscale);
        dataSet.add(GlideAdapter.Type.RoundedCorners);
        dataSet.add(GlideAdapter.Type.Blur);
        dataSet.add(GlideAdapter.Type.Toon);
        dataSet.add(GlideAdapter.Type.Sepia);
        dataSet.add(GlideAdapter.Type.Contrast);
        dataSet.add(GlideAdapter.Type.Invert);
        dataSet.add(GlideAdapter.Type.Pixel);
        dataSet.add(GlideAdapter.Type.Sketch);
        dataSet.add(GlideAdapter.Type.Swirl);
        dataSet.add(GlideAdapter.Type.Brightness);
        dataSet.add(GlideAdapter.Type.Kuawahara);
        dataSet.add(GlideAdapter.Type.Vignette);

        recyclerView.setAdapter(new GlideAdapter(this, dataSet));
    }
}
