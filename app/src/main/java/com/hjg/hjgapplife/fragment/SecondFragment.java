package com.hjg.hjgapplife.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.hjg.baseapp.widget.MyListView;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.Butterknife.ButterknifeActivity;
import com.hjg.hjgapplife.activity.base.BaseFragment;
import com.hjg.hjgapplife.activity.greenDao.GreenDaoActivity;
import com.hjg.hjgapplife.activity.viewpager.ViewPagerActivity;
import com.hjg.hjgapplife.adpter.MlvSecondAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class SecondFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private MyListView mlv;
    private List<String> setlist = new ArrayList(Arrays.asList("GreenDao的使用", "Butterknife插件", "ViewPager各种展示效果", "未知", "未知", "未知"
            , "未知", "未知", "未知", "未知", "未知", "未知"));

    public static SecondFragment getInstance() {
        SecondFragment sf = new SecondFragment();
        return sf;
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
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
        }
    }
}
