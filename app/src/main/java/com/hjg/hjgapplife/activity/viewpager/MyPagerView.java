package com.hjg.hjgapplife.activity.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.hjg.hjgapplife.R;

public class MyPagerView extends RelativeLayout {
    private Context mContext;
    private int mPagerID = 0;
    private RelativeLayout mLayout = null;
    private Button mBtnView = null;

    public MyPagerView(Context context) {
        super(context);
        mContext = context;
        initLayout();
    }

    public MyPagerView(Context context, int pagerID) {
        super(context);
        mPagerID = pagerID;
        mContext = context;
        initLayout();
    }

    private void initLayout() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayout = (RelativeLayout) inflater.inflate(R.layout.viewpager_layout, null);
        addView(mLayout);
        mBtnView = (Button) mLayout.findViewById(R.id.view);

        if (mPagerID != 0) {
            mBtnView.setText("---Pager" + mPagerID + "---");
        }
    }

}
