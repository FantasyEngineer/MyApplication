package com.hjg.hjgapplife.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RadioGroup;

import com.hjg.baseapp.util.SharedPreferenceUtil;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.base.BaseSwipeActivity;

import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * 侧滑退出当前页面
 */
public class SwipeBackActivity extends BaseSwipeActivity implements View.OnClickListener {

    private RadioGroup mTrackingModeGroup;
    private SwipeBackLayout mSwipeBackLayout;


    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "侧滑退出");
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_swipe_back;
    }


    @Override
    protected void initData() {
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_finish).setOnClickListener(this);
        mTrackingModeGroup = (RadioGroup) findViewById(R.id.tracking_mode);

        mSwipeBackLayout = getSwipeBackLayout();

        mTrackingModeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int edgeFlag;
                switch (checkedId) {
                    case R.id.mode_left:
                        edgeFlag = SwipeBackLayout.EDGE_LEFT;
                        break;
                    case R.id.mode_right:
                        edgeFlag = SwipeBackLayout.EDGE_RIGHT;
                        break;
                    case R.id.mode_bottom:
                        edgeFlag = SwipeBackLayout.EDGE_BOTTOM;
                        break;
                    default:
                        edgeFlag = SwipeBackLayout.EDGE_ALL;
                }
                mSwipeBackLayout.setEdgeTrackingEnabled(edgeFlag);
                saveTrackingMode(edgeFlag);
            }
        });
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {

            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
                vibrate(40);
            }

            @Override
            public void onScrollOverThreshold() {
                vibrate(40);
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                startActivity(new Intent(this, SwipeBackActivity.class));
                break;
            case R.id.btn_finish:
                scrollToFinishActivity();
                break;
        }
    }

    /**
     * 记录退出的方式
     *
     * @param flag
     */
    private void saveTrackingMode(int flag) {
        SharedPreferenceUtil.setInfoToShared("退出方式", flag);
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreTrackingMode();
    }

    /**
     * 取出记录退出的方式，将页面对应还原
     */
    private void restoreTrackingMode() {
        int flag = SharedPreferenceUtil.getInfoFromShared("退出方式",
                SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.setEdgeTrackingEnabled(flag);
        switch (flag) {
            case SwipeBackLayout.EDGE_LEFT:
                mTrackingModeGroup.check(R.id.mode_left);
                break;
            case SwipeBackLayout.EDGE_RIGHT:
                mTrackingModeGroup.check(R.id.mode_right);
                break;
            case SwipeBackLayout.EDGE_BOTTOM:
                mTrackingModeGroup.check(R.id.mode_bottom);
                break;
            case SwipeBackLayout.EDGE_ALL:
                mTrackingModeGroup.check(R.id.mode_all);
                break;
        }
    }

}
