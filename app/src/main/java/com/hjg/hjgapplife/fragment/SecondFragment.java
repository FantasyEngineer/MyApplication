package com.hjg.hjgapplife.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.hjg.baseapp.widget.MyListView;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.Butterknife.ButterknifeActivity;
import com.hjg.hjgapplife.activity.ButtonStyleActivity;
import com.hjg.hjgapplife.activity.StringDealActivity;
import com.hjg.hjgapplife.activity.SwipeBackActivity;
import com.hjg.hjgapplife.activity.WindowManager.WindowAlertActivity;
import com.hjg.hjgapplife.activity.base.BaseFragment;
import com.hjg.hjgapplife.activity.cardlayout.CardStackViewActivity;
import com.hjg.hjgapplife.activity.greenDao.GreenDaoActivity;
import com.hjg.hjgapplife.activity.notification.NoticaficationActivity;
import com.hjg.hjgapplife.activity.otherBarRender.OntherBarRenderActivity;
import com.hjg.hjgapplife.activity.titlepage.PagerMainActivity;
import com.hjg.hjgapplife.activity.viewpager.ViewPagerActivity;
import com.hjg.hjgapplife.adpter.MlvSecondAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class SecondFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private static SecondFragment secondFragment;
    private MyListView mlv;
    private List<String> setlist = new ArrayList(Arrays.asList("GreenDao的使用", "Butterknife插件", "ViewPager各种展示效果", "Button的各种样式",
            "字符串操作", "头部pager展示", "通知栏相关", "另一种方式的状态栏渗透", "需要申请权限实现全局悬浮", "recycleView吸顶", "仿外卖双RecycleView联动",
            "recyleView子项侧滑删除", "右滑退出页面", "卡片式布局"));

    public static SecondFragment getInstance() {
        if (secondFragment == null) {
            secondFragment = new SecondFragment();
        }
        return secondFragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_second;
    }

    @Override
    protected void initView() {
        mlv = (MyListView) findViewById(R.id.mlv);

    }

    @Override
    public void initListenAndSetAndAdes() {
        MlvSecondAdapter mlvSecondAdapter = new MlvSecondAdapter(activity, setlist);
        mlv.setAdapter(mlvSecondAdapter);
        mlv.setOnItemClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position) {
            case 0:
                startActivity(new Intent(activity, GreenDaoActivity.class));
                break;
            case 1:
                startActivity(new Intent(activity, ButterknifeActivity.class));
                break;
            case 2:
                startActivity(new Intent(activity, ViewPagerActivity.class));
                break;
            case 3:
                startActivity(new Intent(activity, ButtonStyleActivity.class));
                break;
            case 4:
                startActivity(new Intent(activity, StringDealActivity.class));
                break;
            case 5:
                startActivity(new Intent(activity, PagerMainActivity.class));
                break;
            case 6://通知栏
                startActivity(new Intent(activity, NoticaficationActivity.class));
                break;
            case 7://另外一种状态栏渗透的方法
                startActivity(new Intent(activity, OntherBarRenderActivity.class));
                break;
            case 8://不需要权限的悬浮窗实现
                startActivity(new Intent(activity, WindowAlertActivity.class));
                break;
            case 9:
//                startActivity(new Intent(activity, NoticaficationActivity.class));
                break;
            case 10:
//                startActivity(new Intent(activity, NoticaficationActivity.class));
                break;
            case 11:
//                startActivity(new Intent(activity, NoticaficationActivity.class));
                break;
            case 12:
                startActivity(new Intent(activity, SwipeBackActivity.class));
                break;
            case 13:
                startActivity(new Intent(activity, CardStackViewActivity.class));
                break;
        }
    }
}
