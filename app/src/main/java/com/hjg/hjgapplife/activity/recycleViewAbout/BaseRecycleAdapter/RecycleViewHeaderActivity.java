package com.hjg.hjgapplife.activity.recycleViewAbout.BaseRecycleAdapter;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipFinalActivity;
import com.hjg.hjgapplife.activity.baseRender.LayoutConstans;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewHeaderActivity extends BaseOthreRenderSwipFinalActivity {

    private RecyclerView mRecyclerView;
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_recycle_view_header;
    }

    @Override
    protected int getIncludeLayoutType() {
        return LayoutConstans.LL;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "recycleView的头布局和尾布局");
    }

    @Override
    protected void initData() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setAdapter(mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.rxjava_rv_item, getItemDatas()) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_item_text, item);
                helper.setOnClickListener(R.id.tv_item_text, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "your click item", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });

        addHeaderView();

        addFooterView();
    }


    private void addFooterView() {
        View footerView = getLayoutInflater().inflate(R.layout.rv_footer, null);
        footerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mAdapter.addFooterView(footerView);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "your click footerView", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    int headderIndex = 0;

    private void addHeaderView() {
        View headerView = getLayoutInflater().inflate(R.layout.rv_header, null);
        TextView tv = headerView.findViewById(R.id.tv);
        tv.setText("点击--增加一个头部布局");
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mAdapter.addHeaderView(headerView);
        //点击之后增加头部布局
        headerView.setOnClickListener(view -> {
                    Snackbar.make(view, "your click headerView", Snackbar.LENGTH_SHORT).show();
                    View headerView1 = getLayoutInflater().inflate(R.layout.rv_header, null);
                    mAdapter.addHeaderView(headerView1, headderIndex++);
                }
        );
    }

    public static List<String> getItemDatas() {
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mList.add("recycleView的子项");
        }
        return mList;
    }
}
