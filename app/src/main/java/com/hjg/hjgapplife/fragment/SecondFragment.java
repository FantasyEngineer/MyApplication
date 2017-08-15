package com.hjg.hjgapplife.fragment;

import com.hjg.baseapp.fragment.BaseFragment;
import com.hjg.hjgapplife.R;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class SecondFragment extends BaseFragment {
    public static SecondFragment getInstance() {
        SecondFragment sf = new SecondFragment();
        return sf;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_second;
    }
}
