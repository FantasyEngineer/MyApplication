package com.hjg.hjgapplife.fragment;

import com.hjg.baseapp.fragment.BaseFragment;
import com.hjg.hjgapplife.R;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class ThirdFragment extends BaseFragment {
    public static ThirdFragment getInstance() {
        ThirdFragment sf = new ThirdFragment();
        return sf;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_third;
    }
}
