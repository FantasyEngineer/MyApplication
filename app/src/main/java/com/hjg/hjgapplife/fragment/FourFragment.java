package com.hjg.hjgapplife.fragment;

import com.hjg.baseapp.fragment.BaseFragment;
import com.hjg.hjgapplife.R;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class FourFragment extends BaseFragment {
    public static FourFragment getInstance() {
        FourFragment sf = new FourFragment();
        return sf;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_four;
    }
}
