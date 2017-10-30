package com.hjg.hjgapplife.activity.recycleViewAbout.BaseRecycleAdapter;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipFinalActivity;

import java.util.ArrayList;
import java.util.List;

public class SingleSelectActivity extends BaseOthreRenderSwipActivity {
    RecyclerView mRecyclerView;

    BaseQuickAdapter<String, BaseViewHolder> mAdapter;

    private SparseBooleanArray mBooleanArray;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_single_select;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "recycleView单选");
    }

    @Override
    protected void initData() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setAdapter(mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.rv_item, getDatas()) {
            @Override
            protected void convert(final BaseViewHolder helper, String item) {

                if (mBooleanArray.get(helper.getAdapterPosition())) {
                    helper.setBackgroundColor(R.id.tv_item_text, Color.parseColor("#c0ffbd21"));
                } else {
                    helper.setBackgroundColor(R.id.tv_item_text, Color.parseColor("#ffffff"));
                }

                helper.setOnClickListener(R.id.tv_item_text, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setItemChecked(helper.getAdapterPosition());
                    }
                });

            }
        });
    }

    public List<String> getDatas() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add("" + i);
        }

        mBooleanArray = new SparseBooleanArray(datas.size());

        return datas;
    }


    private int mLastCheckedPosition = -1;

    /**
     * @param position
     */
    public void setItemChecked(int position) {
        if (mLastCheckedPosition == position)
            return;

        mBooleanArray.put(position, true);

        if (mLastCheckedPosition > -1) {
            mBooleanArray.put(mLastCheckedPosition, false);
            mAdapter.notifyItemChanged(mLastCheckedPosition);
        }

        mAdapter.notifyDataSetChanged();

        mLastCheckedPosition = position;
    }


}
