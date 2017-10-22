package com.hjg.hjgapplife.activity.takephoto;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderActivity;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;

public class PhotoViewActivity extends BaseOthreRenderActivity {


    @BindView(R.id.photoView)
    PhotoView photoView;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_photo_view;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData() {

    }


//    public static void startActivity(Activity activity, String url) {
//        activity.startActivity(new Intent(activity, PhotoViewActivity.class));
//    }
}
