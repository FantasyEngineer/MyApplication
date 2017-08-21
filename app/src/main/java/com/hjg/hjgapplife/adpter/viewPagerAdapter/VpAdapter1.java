package com.hjg.hjgapplife.adpter.viewPagerAdapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author houjiguo
 * @title
 * @date 2017/08/20  00:22
 * @Description
 */

public class VpAdapter1 extends PagerAdapter {
    private List<View> list;

    public VpAdapter1(List<View> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    // 判断是否由对象生成界面
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    //销毁页面
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    //初始化界面
    @Override
    public View instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));//添加页卡
        return list.get(position);
    }
}
