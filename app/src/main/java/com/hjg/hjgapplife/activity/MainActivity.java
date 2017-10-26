package com.hjg.hjgapplife.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.blog.www.guideview.Guide;
import com.blog.www.guideview.GuideBuilder;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.utils.UnreadMsgUtils;
import com.flyco.tablayout.widget.MsgView;
import com.hjg.baseapp.util.ScreenUtils;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderActivity;
import com.hjg.hjgapplife.activity.guide.SimpleComponent;
import com.hjg.hjgapplife.activity.seclectCity.SelectCityActivity;
import com.hjg.hjgapplife.entity.EventBusBean;
import com.hjg.hjgapplife.entity.TabEntity;
import com.hjg.hjgapplife.fragment.FirstFragment;
import com.hjg.hjgapplife.fragment.FourFragment;
import com.hjg.hjgapplife.fragment.SecondFragment;
import com.hjg.hjgapplife.fragment.ThirdFragment;
import com.hjg.hjgapplife.zxing.CaptureActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

public class MainActivity extends BaseOthreRenderActivity {
    private String[] mTitles = {"首页", "功能", "消息", "我的"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private CommonTabLayout mTabLayout;
    private int[] mIconUnselectIds = {
            R.mipmap.icon_first, R.mipmap.icon_second,
            R.mipmap.icon_third, R.mipmap.icon_four};
    private int[] mIconSelectIds = {
            R.mipmap.icon_first_select, R.mipmap.icon_second_select,
            R.mipmap.icon_third_select, R.mipmap.icon_four_select};
    //tab的标题、选中图标、未选中图标
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    protected void initView() {
        mTabLayout = (CommonTabLayout) findViewById(R.id.tl);
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                if (mCache.getAsString("isShowed") == null) {
                    showGuideView();
                }
            }
        });
    }

    @Override
    public void initTitle() {
        topBarManage.iniTop(true, R.mipmap.icon_home);
        topBarManage.setLeftButtonImgAndTxt(true, getResources().getDrawable(R.mipmap.icon_location), "定位", view -> {
            startActivity(new Intent(activity, SelectCityActivity.class));
            MobclickAgent.onEvent(activity, "test1");
        });
        topBarManage.setRightButtonImgAndTxt(true,
                getResources().getDrawable(R.mipmap.saoyisao), "", view -> {
                    startActivity(new Intent(activity, CaptureActivity.class));
                    MobclickAgent.onEvent(activity, "test2");
                });
    }


//    @Override
//    protected int setBarColor() {
//        return R.color.darkorange;
//    }


    @Override
    protected void initData() {
        mFragments.removeAll(mFragments);
        initView();
        initAction();
        //如果有存在就不进行添加了。
        mFragments.add(FirstFragment.getInstance());
        mFragments.add(SecondFragment.getInstance());
        mFragments.add(ThirdFragment.getInstance());
        mFragments.add(FourFragment.getInstance());
        //设置tab的标题、选中图标、未选中图标
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        //给tab设置数据和关联的fragment
        mTabLayout.setTabData(mTabEntities, MainActivity.this, R.id.fl_change, mFragments);


        //两位数
        mTabLayout.showMsg(0, 55);
        mTabLayout.setMsgMargin(0, -5, 5);

        //三位数
        mTabLayout.showMsg(1, 100);
        mTabLayout.setMsgMargin(1, -5, 5);

        //设置未读消息红点
        mTabLayout.showDot(2);
        MsgView rtv_2_2 = mTabLayout.getMsgView(2);
        if (rtv_2_2 != null) {
            UnreadMsgUtils.setSize(rtv_2_2, ScreenUtils.dp2px(activity, 7.5f));
        }
    }

    public void initAction() {
        mTabLayout.setOnTabSelectListener(new TabSelectListener());
    }

    private int currentPosition;

    public class TabSelectListener implements OnTabSelectListener {

        @Override
        public void onTabSelect(int position) {
            currentPosition = position;
            if (position == 3) {
                hideTopBar();
            } else {
                showTopBar();
            }
        }

        @Override
        public void onTabReselect(int position) {
//            showTopBar();
        }

    }


    long firstTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;//更新firstTime
                return true;
            } else {    //两次按键小于2秒时，退出应用
                MobclickAgent.onKillProcess(activity);
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    Guide guide;

    public void showGuideView() {
        //标记是否已经展示过
        mCache.put("isShowed", "isShowed");
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(topBarManage.getLeftBtn())
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {
            }

            @Override
            public void onDismiss() {
            }
        });

        builder.addComponent(new SimpleComponent());
        guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(true);
        guide.show(this);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        currentPosition = savedInstanceState.getInt("position");
        //旋转屏幕的时候进行
        mTabLayout.setCurrentTab(currentPosition);
        if (currentPosition == 3) {
            hideTopBar();
        } else {
            showTopBar();
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("position", currentPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onEventMainThread(Object object) {
        //从地址选择界面选中后，重置title
        if (object instanceof EventBusBean) {
            EventBusBean bean = (EventBusBean) object;
            if (bean.getFlag() == EventBusBean.CitySelect) {
                topBarManage.getLeftBtn().setText(bean.getContent());
            }
        }

    }
}
