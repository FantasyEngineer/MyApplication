package com.hjg.hjgapplife.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.hjg.baseapp.widget.CircleImageView;
import com.hjg.baseapp.widget.MyDialog;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.base.BaseFragment;

/**
 * Created by Administrator on 2017/8/15 0015.
 * 首页--我的
 */

public class FourFragment extends BaseFragment implements View.OnClickListener {
    private static FourFragment fourFragment;
    private CircleImageView civ_official;

    public static FourFragment getInstance() {
        if (fourFragment == null)
            fourFragment = new FourFragment();
        return fourFragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_four;
    }

    @Override
    protected void initView() {
        civ_official = (CircleImageView) findViewById(R.id.civ_official);
    }

    @Override
    public void initListenAndSetAndAdes() {
        civ_official.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        MyDialog dialog = new MyDialog(activity);
        dialog.setLayoutView(LayoutInflater.from(activity)
                .inflate(R.layout.dialog_layout, null));
        dialog.setDialogGravity(MyDialog.DialogGravity.CENTERBOTTOM);
        dialog.show();
    }
}
