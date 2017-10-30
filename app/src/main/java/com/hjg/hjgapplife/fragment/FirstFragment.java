package com.hjg.hjgapplife.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.hjg.baseapp.adapter.RvCommonAdapter;
import com.hjg.baseapp.adapter.ViewHolder;
import com.hjg.baseapp.widget.VerticalTextview;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.TestUPviewActivity;
import com.hjg.hjgapplife.activity.base.BaseFragment;
import com.hjg.hjgapplife.activity.takephoto.PhotoViewActivity;
import com.hjg.hjgapplife.activity.transitionhelper.PhotoShowActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    //    滚动广告的集合
    private ArrayList<String> titleList = new ArrayList<String>();
    //banner切换动画特效集合
    ArrayList<SliderLayout.Transformer> transList = new ArrayList();
    private VerticalTextview vtvShowNotes;
    private SliderLayout banner;
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
        banner = (SliderLayout) findViewById(R.id.slider);
        btn_switch = (Button) findViewById(R.id.btn_switch);

        recycleViewHorizatal = (RecyclerView) findViewById(R.id.recycleViewHorizatal);
        //设置横向
        recycleViewHorizatal.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void initListenAndSetAndAdes() {
        btn_switch.setOnClickListener(this);
//        循环滚动的条目
        titleList.add("我和你男和女都逃不过爱情");
        titleList.add("谁愿意有勇气 不顾一切付出真心");
        titleList.add("你说的不只你 还包括我自己");
        titleList.add("该不该再继续 该不该有回应");
        titleList.add("让爱一步一步靠近");
        titleList.add("我对你有一点动心");
        titleList.add("却如此害怕看你的眼睛");
        titleList.add("有那么一点点动心 一点点迟疑");
        titleList.add("害怕爱过以后还要失去");
        titleList.add("人最怕就是动了情");
        vtvShowNotes.setTextList(titleList);
        vtvShowNotes.setTextStillTime(3000);//设置停留时长间隔
        vtvShowNotes.setAnimTime(300);//设置进入和退出的时间间隔
    }


    @Override
    public void initData() {
        for (SliderLayout.Transformer e : SliderLayout.Transformer.values()) {
            transList.add(e);
        }

//通过网络
        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("网络1", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502871861358&di=efef08f0e9f2f7457b002d5d060a426e&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0173765821b703a84a0e282b8a197c.jpg%40900w_1l_2o_100sh.jpg");
        url_maps.put("网络2", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502871861359&di=c243a73a01770d7c9f92efbb9b70f734&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fback_pic%2Fqk%2Fback_origin_pic%2F00%2F03%2F11%2F3e0210f7a00859a4ae0a9991fcbbe8b2.jpg");
        url_maps.put("网络3", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502876203382&di=63654525f49723acdf61ff2e68f2ad07&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01cc9c57bc1c7d0000012e7e53d8b8.jpg%40900w_1l_2o_100sh.jpg");
        url_maps.put("网络4", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502871861364&di=9d46cece0494f0490407356698c642b3&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01859757e372de0000012e7e38b66b.jpg%40900w_1l_2o_100sh.jpg");
//通过res
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("默认1", R.mipmap.default_banner);
        file_maps.put("默认2", R.mipmap.default_banner);
        file_maps.put("默认3", R.mipmap.default_banner);
        file_maps.put("默认4", R.mipmap.default_banner);

        for (final String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(activity);
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            Toast.makeText(activity, name, Toast.LENGTH_SHORT).show();
                        }
                    });

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            banner.addSlider(textSliderView);
        }
        //设置切换动画
        banner.setPresetTransformer(SliderLayout.Transformer.Accordion);
        //设置指示器位置
        banner.setPresetIndicator(SliderLayout.PresetIndicators.Right_Top);
        //设置自定义指示器
//        banner.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator2));
        //隐藏指示器
//        banner.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        banner.setCustomAnimation(new DescriptionAnimation());//设置图片名称出现的动画
        //设置持续时间
        banner.setDuration(2000);
        //设置页面切换监听
        banner.addOnPageChangeListener(new PageChangeListener());


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
        vtvShowNotes.startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        vtvShowNotes.stopAutoScroll();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_switch://banner切换动画
                banner.setPresetTransformer(transList.get(new Random().nextInt(transList.size())));
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public class PageChangeListener implements ViewPagerEx.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


}
