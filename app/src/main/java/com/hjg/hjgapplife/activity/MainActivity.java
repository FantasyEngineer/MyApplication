package com.hjg.hjgapplife.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hjg.baseapp.entity.TabEntity;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.base.BaseActivity;
import com.hjg.hjgapplife.activity.seclectCity.SelectCityActivity;
import com.hjg.hjgapplife.fragment.FirstFragment;
import com.hjg.hjgapplife.fragment.FourFragment;
import com.hjg.hjgapplife.fragment.SecondFragment;
import com.hjg.hjgapplife.fragment.ThirdFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
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

    @Override
    protected void initView() {
        mTabLayout = (CommonTabLayout) findViewById(R.id.tl);
    }

    @Override
    public void initTitle() {
        topBarManage.iniTop(true, R.mipmap.icon_home);
        topBarManage.setLeftButtonImgAndTxt(true, getResources().getDrawable(R.mipmap.icon_location), "定位", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, SelectCityActivity.class));
            }
        });
    }


//    @Override
//    protected int setBarColor() {
//        return R.color.darkorange;
//    }


    @Override
    protected void initData() {
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

    }

    @Override
    public void initAction() {
        super.initAction();
        mTabLayout.setOnTabSelectListener(new TabSelectListener());
    }


    public class TabSelectListener implements OnTabSelectListener {

        @Override
        public void onTabSelect(int position) {
            Log.d("TabSelectListener", "position:" + position);
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
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
