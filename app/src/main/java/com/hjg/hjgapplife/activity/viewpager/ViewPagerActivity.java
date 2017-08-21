package com.hjg.hjgapplife.activity.viewpager;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hjg.baseapp.util.ScreenUtils;
import com.hjg.baseapp.widget.viewPager.ScalePageTransformer;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.base.BaseActivity;
import com.hjg.hjgapplife.adpter.viewPagerAdapter.TubatuAdapter;
import com.hjg.hjgapplife.adpter.viewPagerAdapter.VPCirclationAdapter;
import com.hjg.hjgapplife.adpter.viewPagerAdapter.VpAdapter1;
import com.hjg.hjgapplife.adpter.viewPagerAdapter.VpAdapter2;
import com.hjg.hjgapplife.listener.ViewPagerItemListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页展示的各种ViewPager的样式
 */
public class ViewPagerActivity extends BaseActivity {


    @BindView(R.id.vp_1)
    ViewPager vp1;
    @BindView(R.id.vp_2)
    ViewPager vp2;
    @BindView(R.id.vp_3)
    ViewPager vp3;
    @BindView(R.id.vp_4)
    ViewPager vp4;
    @BindView(R.id.cvp)
    ViewPager cvp;
    @BindView(R.id.rl_clp_container)
    RelativeLayout rl_clp_container;


    //设置展示图片的列表
    private List<Integer> mDatas = new ArrayList<Integer>(Arrays.asList(
            R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d,
            R.mipmap.e, R.mipmap.f, R.mipmap.g, R.mipmap.h,
            R.mipmap.i));
    ArrayList<View> views = new ArrayList<>();
    ArrayList<View> view2s = new ArrayList<>();
    ArrayList<View> view3s = new ArrayList<>();
    ArrayList<View> view4s = new ArrayList<>();
    ArrayList<View> view5s = new ArrayList<>();

    private LayoutInflater layoutInflater;

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "展示效果");
        topBarManage.setLeftBtnBack(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_viewpager;
    }

    @Override
    protected void initView() {
        //正常的viewpager
        initPager1();
        //显示多个item的viewpager，重点是复写pageradapter中的getPageWidth(int position) 方法
        // @Override
        //  public float getPageWidth(int position) {
        //      return 0.4f;//0-1范围。 0.5的时候一屏显示2个，0.4的时候显示2.5个。
        //  }
        initPager2();
        initPager3();
        initPager4();
        initPagerCVP5();
    }

    private void initPager1() {
        for (int i = 0; i < mDatas.size(); i++) {
            ImageView imageView = new ImageView(activity);
            imageView.setBackgroundResource(mDatas.get(i));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setPadding(ScreenUtils.dp2px(activity, 20), 0, ScreenUtils.dp2px(activity, 20), 0);
            views.add(imageView);
        }
        VpAdapter1 vpAdapter1 = new VpAdapter1(views);
        vp1.setAdapter(vpAdapter1);
        vp1.setOffscreenPageLimit(4);//缓存数目
    }


    private void initPager2() {
        layoutInflater = LayoutInflater.from(activity);
        LinearLayout item1 = (LinearLayout) layoutInflater.inflate(R.layout.viewpager_item, null);
        ImageView imageView1 = ButterKnife.findById(item1, R.id.iv);
        imageView1.setBackgroundResource(R.mipmap.b);

        LinearLayout item2 = (LinearLayout) layoutInflater.inflate(R.layout.viewpager_item, null);
        ImageView imageView2 = ButterKnife.findById(item2, R.id.iv);
        imageView2.setBackgroundResource(R.mipmap.c);


        LinearLayout item3 = (LinearLayout) layoutInflater.inflate(R.layout.viewpager_item, null);
        ImageView imageView3 = ButterKnife.findById(item3, R.id.iv);
        imageView3.setBackgroundResource(R.mipmap.d);

        LinearLayout item4 = (LinearLayout) layoutInflater.inflate(R.layout.viewpager_item, null);
        ImageView imageView4 = ButterKnife.findById(item4, R.id.iv);
        imageView4.setBackgroundResource(R.mipmap.f);

        LinearLayout item5 = (LinearLayout) layoutInflater.inflate(R.layout.viewpager_item, null);
        ImageView imageView5 = ButterKnife.findById(item5, R.id.iv);
        imageView5.setBackgroundResource(R.mipmap.e);

        view2s.add(item1);
        view2s.add(item2);
        view2s.add(item3);
        view2s.add(item4);
        view2s.add(item5);
        VpAdapter2 vpAdapter2 = new VpAdapter2(view2s);
        vp2.setAdapter(vpAdapter2);
        vp2.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.d("ViewPagerActivity", "position:" + position);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("ViewPagerActivity", "onPageSelected:" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //    无限循环
    int msgWhat = 0;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == msgWhat) {
                vp3.setCurrentItem(vp3.getCurrentItem() + 1);//收到消息，指向下一个页面
                handler.sendEmptyMessageDelayed(msgWhat, 2000);//2S后在发送一条消息，由于在handleMessage()方法中，造成死循环。
            }
        }
    };

    private void initPager3() {
        for (int i = 0; i < mDatas.size(); i++) {
            ImageView imageView = new ImageView(activity);
            imageView.setBackgroundResource(mDatas.get(i));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setPadding(ScreenUtils.dp2px(activity, 20), 0, ScreenUtils.dp2px(activity, 20), 0);
            view3s.add(imageView);
        }

        VPCirclationAdapter vpCirclationAdapter = new VPCirclationAdapter(view3s);
        vpCirclationAdapter.setListener(new ViewPagerItemListener() {
            @Override
            public void callBack(int position) {
                Toast.makeText(activity, "点击的位置position:" + position, Toast.LENGTH_SHORT).show();
            }
        });
        vp3.setAdapter(vpCirclationAdapter);
//        当按下的时候停止循环
//        vp3.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.d("ViewPagerActivity", "ACTION_DOWN");
//                        handler.sendEmptyMessageDelayed(msgBreak, 2000);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        handler.sendEmptyMessageDelayed(msgWhat, 2000);
//                        Log.d("ViewPagerActivity", "ACTION_UP");
//                        break;
//                }
//                return false;
//            }
//        });
    }

    private void initPager4() {
        for (int i = 0; i < mDatas.size(); i++) {
            ImageView imageView = new ImageView(activity);
            imageView.setBackgroundResource(mDatas.get(i));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setPadding(ScreenUtils.dp2px(activity, 20), 0, ScreenUtils.dp2px(activity, 20), 0);
            view4s.add(imageView);
        }
        VPCirclationAdapter vpCirclationAdapter4 = new VPCirclationAdapter(view4s);
        vp4.setAdapter(vpCirclationAdapter4);
        vp4.setCurrentItem(1000);
        // 设置2张图之前的间距。
        vp4.setPageMargin(ScreenUtils.dp2px(activity, 10));
        vp4.setOffscreenPageLimit(2);//因为是漏边的，必须要有缓存数量
    }


    private void initPagerCVP5() {
        for (int i = 0; i < mDatas.size(); i++) {
            ImageView imageView = new ImageView(activity);
            imageView.setBackgroundResource(mDatas.get(i));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setPadding(ScreenUtils.dp2px(activity, 20), 0, ScreenUtils.dp2px(activity, 20), 0);
            view5s.add(imageView);
        }
        TubatuAdapter vpAdapter5 = new TubatuAdapter(view5s);
        cvp.setAdapter(vpAdapter5);
        cvp.setPageTransformer(true, new ScalePageTransformer());
//        这里需要将setOffscreenPageLimit的值设置成数据源的总个数，如果不加这句话，会导致左右切换异常
        cvp.setOffscreenPageLimit(mDatas.size());
        //将外部容器的触点分发给Viewpager，让viewpager来处理
        rl_clp_container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return cvp.dispatchTouchEvent(motionEvent);
            }
        });
    }


    @Override
    protected void initData() {

    }

    /**
     * 当MainActivity不可见的时候让handler停止发送消息
     * 防止内存泄露
     */
    @Override
    protected void onStop() {
        super.onStop();
        handler.removeMessages(msgWhat);
    }

    /**
     * activity可见可交互的时候就开始发送消息，开启循环
     */
    @Override
    protected void onResume() {
        super.onResume();
        handler.sendEmptyMessageDelayed(msgWhat, 1000);
    }
}
