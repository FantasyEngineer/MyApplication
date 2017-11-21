package com.hjg.hjgapplife.activity.okhttp;

import android.content.Intent;

import com.hjg.hjgapplife.activity.baseRender.BaseOtherRenderListActivity;

public class OkhttpMainActivity extends BaseOtherRenderListActivity {


    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "OKHTTP的使用");
    }

    @Override
    public void initDataList() {
        dataList.add("okhttp的基本案例");
    }

    @Override
    public void OnItemClick(int i) {
        switch (i) {
            case 0:
                startActivity(new Intent(activity, DemoOKActivity.class));
                break;

        }

    }
}
