package com.hjg.hjgapplife.adpter.viewPagerAdapter;

import android.view.View;
import android.view.ViewGroup;

import com.hjg.baseapp.widget.viewPager.RecyclingPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class TubatuAdapter extends RecyclingPagerAdapter {

    private List<View> list;

    public TubatuAdapter(List<View> list) {
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
