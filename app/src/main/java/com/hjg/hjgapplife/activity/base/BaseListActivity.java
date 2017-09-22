package com.hjg.hjgapplife.activity.base;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.adpter.MlvSecondAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 不可以侧滑关闭的展示List的基类
 */

public abstract class BaseListActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    protected ArrayList<String> dataList = new ArrayList();
    @BindView(R.id.list)
    ListView list;

    @Override
    protected int getContentLayout() {
        return R.layout.list;
    }

    @Override
    protected void initView() {
        initDataList();
        MlvSecondAdapter mlvSecondAdapter = new MlvSecondAdapter(activity, dataList);
        list.setAdapter(mlvSecondAdapter);
        list.setOnItemClickListener(this);
    }

    //需要重写这个方法 将dataList填充数据
    public abstract void initDataList();


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        OnItemClick(i);
    }

    public abstract void OnItemClick(int i);
}
