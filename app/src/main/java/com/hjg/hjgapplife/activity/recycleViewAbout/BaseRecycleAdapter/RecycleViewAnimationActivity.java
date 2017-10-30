package com.hjg.hjgapplife.activity.recycleViewAbout.BaseRecycleAdapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipFinalActivity;
import com.hjg.hjgapplife.activity.baseRender.LayoutConstans;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAnimationActivity extends BaseOthreRenderSwipFinalActivity {

    private TextView tvAnimationType;
    private TextView tvIsFirstOnly;
    private RecyclerView mRecyclerView;

    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_recycle_view_animation;
    }

    @Override
    protected int getIncludeLayoutType() {
        return LayoutConstans.LL;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "RecycleView滑动动画");
    }

    @Override
    protected void initData() {
        tvAnimationType = (TextView) findViewById(R.id.tv_animation_type);
        tvIsFirstOnly = (TextView) findViewById(R.id.tv_first_only);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mRecyclerView.setAdapter(mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.rv_item, getItemDatas()) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_item_text, item);
            }
        });

        mAdapter.openLoadAnimation();

        init();
    }

    private void init() {

        tvAnimationType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAnimationTypeDialog();
            }
        });

        tvIsFirstOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showIsFirstOnlyDialog();
            }
        });

    }

    public static List<String> getItemDatas() {
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < 99; i++) {
            mList.add("recycleView子项");
        }
        return mList;
    }


    private void showAnimationTypeDialog() {
        final String[] stringItems = {"ALPHA", "SCALE", "SLIDE_BOTTOM",
                "SLIDE_LEFT", "SLIDE_RIGHT"/*, "SLIDE_LEFT_RIGHT", "SLIDE_BOTTOM_TOP"*/};
        final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, null);
//        title("choose animation type")//
//                .titleTextSize_SP(14.5f)//
        dialog.isTitleShow(false)
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    default:
//                    case 0:
////                        mAdapter.openLoadAnimation(BaseQuickAdapter.CUSTOMIN);
//                        break;
                    case 0:
                        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                        break;
                    case 1:
                        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                        break;
                    case 2:
                        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
                        break;
                    case 3:
                        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
                        break;
                    case 4:
                        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
                        break;
//                    case 5:
////                        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT_RIGHT);
//                        break;
//                    case 6:
////                        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM_TOP);
//                    break;
                }
                tvAnimationType.setText(stringItems[position]);
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

    private void showIsFirstOnlyDialog() {
        final String[] stringItems = {"isFirstOnly(true)", "isFirstOnly(false)"};
        final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, null);
        dialog.isTitleShow(false)
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    default:
                    case 0:
                        mAdapter.isFirstOnly(true);
                        break;
                    case 1:
                        mAdapter.isFirstOnly(false);
                        break;
                }
                tvIsFirstOnly.setText(stringItems[position]);
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }
}
