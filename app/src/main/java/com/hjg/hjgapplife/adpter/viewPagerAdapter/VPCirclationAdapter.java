package com.hjg.hjgapplife.adpter.viewPagerAdapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.hjg.hjgapplife.listener.ViewPagerItemListener;

import java.util.List;

/**
 * @author houjiguo
 * @title 无限循环的PagerAdapter
 * @date 2017/08/20  19:23
 * @Description
 */

public class VPCirclationAdapter extends PagerAdapter {
    private List<View> list;
    private ViewPagerItemListener listener;

    public VPCirclationAdapter(List<View> list) {
        this.list = list;
    }

    //表示viewpager共存放了多少个页面
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
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

    /**
     * position % imageList.size() 而不是position，是为了防止角标越界异常
     * 因为我们设置了viewpager子页面的数量有Integer.MAX_VALUE，而imageList的数量只是5。
     */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        list.get(position % list.size()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.callBack(position % list.size());
                }
            }
        });
        if (null != list.get(position % list.size()).getParent()) {
            container.removeView(list.get(position % list.size()));
            return list.get(position % list.size()).getParent();
        }
        container.addView(list.get(position % list.size()));
        return list.get(position % list.size());
    }

//    但是这样暴露一个缺点，就是可以向左滑动，但是当前页是第一页的时候，无法向右滑动，因为第一页的position是0。
//
//    解决方法：
//    给viewpager设置当前页是第1000页，这样当前页的左边还有999页，右边还有Integer.MAX_VALUE -1000页，
//    无论是999，还是（Integer.MAX_VALUE -1000），一般情况下是滑不到第一页或最后一页的。
//    当然1000是自己设置的，也可以设置多加几个0。

    public ViewPagerItemListener getListener() {
        return listener;
    }

    public void setListener(ViewPagerItemListener listener) {
        this.listener = listener;
    }
}
