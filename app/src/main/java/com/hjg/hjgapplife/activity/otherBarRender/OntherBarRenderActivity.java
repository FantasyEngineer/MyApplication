package com.hjg.hjgapplife.activity.otherBarRender;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.hjg.baseapp.widget.JustifyTextView;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 另外一种状态栏渗透的变色的方法：
 */
public class OntherBarRenderActivity extends BaseOthreRenderActivity {
    @BindView(R.id.tv)
    JustifyTextView tv;
    String s = "这是一种不同与StatusBarUtil的改变状态栏颜色的渗透方式，这种方式先是将状态栏隐藏，方式如上图所示，" +
            "再将自己的topBar的高度+原来的状态栏高度，作为填充。避免了侧滑时，顶部状态栏高亮，打开drawlayout时，状态栏颜色不统一的情况" +
            "";
    @BindView(R.id.jump)
    Button jump;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_onther_bar_render;
    }


    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "另一种状态栏沉浸的方式");
        topBarManage.setLeftBtnBack(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    protected void initData() {
        tv.setText(s);
    }

    @OnClick(R.id.jump)
    public void onViewClicked() {
        startActivity(new Intent(activity, LeftbackActivity.class));
    }
}
