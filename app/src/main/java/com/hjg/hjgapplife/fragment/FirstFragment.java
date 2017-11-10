package com.hjg.hjgapplife.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Transformers.AccordionTransformer;
import com.daimajia.slider.library.Transformers.BackgroundToForegroundTransformer;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.daimajia.slider.library.Transformers.CubeInTransformer;
import com.daimajia.slider.library.Transformers.DefaultTransformer;
import com.daimajia.slider.library.Transformers.DepthPageTransformer;
import com.daimajia.slider.library.Transformers.FadeTransformer;
import com.daimajia.slider.library.Transformers.FlipHorizontalTransformer;
import com.daimajia.slider.library.Transformers.FlipPageViewTransformer;
import com.daimajia.slider.library.Transformers.ForegroundToBackgroundTransformer;
import com.daimajia.slider.library.Transformers.RotateDownTransformer;
import com.daimajia.slider.library.Transformers.RotateUpTransformer;
import com.daimajia.slider.library.Transformers.StackTransformer;
import com.daimajia.slider.library.Transformers.TabletTransformer;
import com.daimajia.slider.library.Transformers.ZoomInTransformer;
import com.daimajia.slider.library.Transformers.ZoomOutSlideTransformer;
import com.daimajia.slider.library.Transformers.ZoomOutTransformer;
import com.hjg.baseapp.adapter.RvCommonAdapter;
import com.hjg.baseapp.adapter.ViewHolder;
import com.hjg.baseapp.widget.VerticalTextview;
import com.hjg.hjgapplife.DataProvider;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.Rx.RxJavaMainActivity;
import com.hjg.hjgapplife.activity.ScreenFit.ScreenFitMainActivity;
import com.hjg.hjgapplife.activity.base.BaseFragment;
import com.hjg.hjgapplife.activity.takephoto.PhotoViewActivity;
import com.hjg.hjgapplife.activity.transitionhelper.PhotoShowActivity;
import com.hjg.hjgapplife.adpter.viewPagerAdapter.VPCirclationAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import immortalz.me.library.TransitionsHeleper;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class FirstFragment extends BaseFragment implements View.OnClickListener {

    private static FirstFragment firstFragment;
    @BindView(R.id.cv_rxjava)
    CardView cvRxjava;
    @BindView(R.id.cv_screen_fit)
    CardView cvScreenFit;
    private VerticalTextview vtvShowNotes;
    private ViewPager banner;
    private Button btn_switch;
    //设置展示图片的列表
    private List<Integer> mDatas = new ArrayList<Integer>(Arrays.asList(
            R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d,
            R.mipmap.e, R.mipmap.f, R.mipmap.g, R.mipmap.h,
            R.mipmap.i));
    private RecyclerView recycleViewHorizatal;


    public static FirstFragment getInstance() {
        if (firstFragment == null)
            firstFragment = new FirstFragment();
        return firstFragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_first;
    }

    @Override
    protected void initView() {
        vtvShowNotes = (VerticalTextview) findViewById(R.id.vtv_show_notes);
        banner = (ViewPager) findViewById(R.id.slider);
        btn_switch = (Button) findViewById(R.id.btn_switch);

        recycleViewHorizatal = (RecyclerView) findViewById(R.id.recycleViewHorizatal);
        //设置横向
        recycleViewHorizatal.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void initListenAndSetAndAdes() {
        btn_switch.setOnClickListener(this);
//        循环滚动的条目
        vtvShowNotes.setTextList(DataProvider.getTitleList());
        vtvShowNotes.setTextStillTime(3000);//设置停留时长间隔
        vtvShowNotes.setAnimTime(200);//设置进入和退出的时间间隔
    }


    @Override
    public void initData() {
//        for (SliderLayout.Transformer e : SliderLayout.Transformer.values()) {
//            transList.add(e);
//        }
        ArrayList<View> bannerViewList = new ArrayList();
        for (String o : DataProvider.getBannerList()) {
            ImageView imageView = new ImageView(activity);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(activity).load(o).into(imageView);
            bannerViewList.add(imageView);
        }
        VPCirclationAdapter bannerAdapter = new VPCirclationAdapter(bannerViewList);
        banner.setAdapter(bannerAdapter);
        banner.setOnTouchListener((view, motionEvent) -> {
            handler.removeMessages(msgWhat);
            return false;
        });
        banner.setPageTransformer(true, new AccordionTransformer());
        initHorizatalScrollView();
    }


    String imgUrl = "http://imga.mumayi.com/android/wallpaper/2012/01/02/sl_600_2012010206150877826134.jpg";

    private void initHorizatalScrollView() {
        RvCommonAdapter<Integer> hot_adapter = new RvCommonAdapter<Integer>(activity, R.layout.recyleview_item, mDatas) {
            @Override
            public void convert(ViewHolder holder, final Integer resid, final int position) {
                final ImageView iv = holder.getView(R.id.iv);
//                final ViewGroup.LayoutParams lp = iv.getLayoutParams();
//                lp.width = (ScreenUtils.getScreenWidth(mContext) - ScreenUtils.dp2px(mContext, 25)) / 2;
//                lp.height = lp.width * 4 / 7;
//                iv.setLayoutParams(lp);
                iv.setImageResource(resid);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LinearLayoutManager linearManager = (LinearLayoutManager)
                                recycleViewHorizatal.getLayoutManager();
                        int lastposition = linearManager.findLastVisibleItemPosition();
                        Log.d("FirstFragment", "最后的position===:" + lastposition);
                        //两种不同的图片展示方式
                        if (position % 2 == 0) {
                            TransitionsHeleper.startActivity(activity, PhotoShowActivity.class, iv, resid, position == lastposition);
                        } else {
                            TransitionsHeleper.startActivity(activity, PhotoViewActivity.class, iv, resid, position == lastposition);
                        }
                    }
                });
            }
        };
        recycleViewHorizatal.setAdapter(hot_adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        handler.sendEmptyMessageDelayed(msgWhat, 2000);
        vtvShowNotes.startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeMessages(msgWhat);
        vtvShowNotes.stopAutoScroll();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_switch://banner切换动画
                setPresetTransforme(new Random().nextInt(15));
//                banner.setPresetTransformer(transList.get(new Random().nextInt(transList.size())));
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.cv_rxjava, R.id.cv_screen_fit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cv_rxjava:
                startActivity(new Intent(activity, RxJavaMainActivity.class));
                break;
            case R.id.cv_screen_fit://屏幕适配
                startActivity(new Intent(activity, ScreenFitMainActivity.class));
                break;
        }
    }


    //    无限循环
    int msgWhat = 0;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == msgWhat) {
                banner.setCurrentItem(banner.getCurrentItem() + 1);//收到消息，指向下一个页面
                handler.sendEmptyMessageDelayed(msgWhat, 2000);//2S后在发送一条消息，由于在handleMessage()方法中，造成死循环。
            }
        }
    };


    public void setPresetTransforme(int ts) {
        //
        // special thanks to https://github.com/ToxicBakery/ViewPagerTransforms
        //
        BaseTransformer t = null;
        switch (ts) {
            case 0:
                t = new DefaultTransformer();
                break;
            case 1:
                t = new AccordionTransformer();
                break;
            case 2:
                t = new BackgroundToForegroundTransformer();
                break;
            case 3:
                t = new CubeInTransformer();
                break;
            case 4:
                t = new DepthPageTransformer();
                break;
            case 5:
                t = new FadeTransformer();
                break;
            case 6:
                t = new FlipHorizontalTransformer();
                break;
            case 7:
                t = new FlipPageViewTransformer();
                break;
            case 8:
                t = new ForegroundToBackgroundTransformer();
                break;
            case 9:
                t = new RotateDownTransformer();
                break;
            case 10:
                t = new RotateUpTransformer();
                break;
            case 11:
                t = new StackTransformer();
                break;
            case 12:
                t = new TabletTransformer();
                break;
            case 13:
                t = new ZoomInTransformer();
                break;
            case 14:
                t = new ZoomOutSlideTransformer();
                break;
            case 15:
                t = new ZoomOutTransformer();
                break;
        }
        banner.setPageTransformer(true, t);
    }


}
