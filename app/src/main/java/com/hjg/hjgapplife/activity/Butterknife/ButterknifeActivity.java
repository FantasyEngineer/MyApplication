package com.hjg.hjgapplife.activity.Butterknife;

import android.view.View;

import com.hjg.baseapp.widget.JustifyTextView;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.base.BaseActivity;

import butterknife.BindView;

public class ButterknifeActivity extends BaseActivity {
    String explain = "在demo中使用ButterKnife插件是没有任何问题的，但是在本工程中使用就一直报控件空指针，" +
            "终于有时间彻底的查看了一下。\n首先配置是肯定没有问题的。因为在demo中也是同样的配置。" +
            "\n\n但是demo和本工程有个区别，demo是在app目录的build.gradle中进行引入ButterKnife插件，而这个项目是在BaseLib" +
            "中引入的，app再去引入BaseLib模块，达到间接使用到Knife的效果，因为baseActivity都是写在BaseLib基础库中的。" +
            "因为将引入搬到了app下面的gradle中，所以baseActivity和BaseFragment也要一同迁到了app下";

    @BindView(R.id.tv_explain)
    JustifyTextView tvExplain;

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "ButterKnife插件");
        topBarManage.setLeftBtnBack(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_butterknife;
    }


    @Override
    protected void initView() {
        tvExplain.setText(explain);
    }

    @Override
    protected void initData() {

    }

}
