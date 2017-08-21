package com.hjg.hjgapplife.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hjg.baseapp.util.StringUtils;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.hjg.hjgapplife.R.id.btn_1;

/**
 * 字符串处理的activity
 */
public class StringDealActivity extends BaseActivity {
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(btn_1)
    Button btn1;
    @BindView(R.id.btn_2)
    Button btn2;
    @BindView(R.id.btn_3)
    Button btn3;
    @BindView(R.id.btn_4)
    Button btn4;
    @BindView(R.id.btn_5)
    Button btn5;
    @BindView(R.id.btn_6)
    Button btn6;
    @BindView(R.id.btn_7)
    Button btn7;
    @BindView(R.id.btn_8)
    Button btn8;
    @BindView(R.id.btn_9)
    Button btn9;
    @BindView(R.id.btn_10)
    Button btn10;

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "字符串处理");
        topBarManage.setLeftBtnBack(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_string_deal;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        btn1.setText("随机生成长度为5-10位的数字+字母字符串");
        btn2.setText("随机生成固定长度的数字+字母字符串");
        btn3.setText("获取长度（双字节字符长度计2，ASCII字符计1）");
        btn4.setText("aaaaa字符串3倍加长");
        btn5.setText("金额去,");
        btn6.setText("字符串转数组");
        btn7.setText("数组转字符串（穿插符为，）");
        btn8.setText("随机生成长度为6位的数字");
        btn9.setText("随机生成长度为5位的区域内16进制数字字符串");
        btn10.setText("随机生成长度为10位的字母字符串");
    }


    @OnClick({btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_10})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case btn_1:
                tvShow.setText(StringUtils.buildRandomString(5, 10));
                break;
            case R.id.btn_2:
                tvShow.setText(StringUtils.buildRandomString(8));
                break;
            case R.id.btn_3:
                tvShow.setText(StringUtils.getLength(tvShow.getText().toString()) + "");

                break;
            case R.id.btn_4:
                tvShow.setText(StringUtils.repeatString("aaaaa", 3));

                break;
            case R.id.btn_5:
                tvShow.setText(StringUtils.decimal("5623,2121"));

                break;
            case R.id.btn_6:
                String[] str = StringUtils.toArr("niaho");
                tvShow.setText(str[0]);

                break;
            case R.id.btn_7:
                String[] str1 = new String[]{"a", "f", "f", "t", "3"};
                String s = "";
                tvShow.setText(StringUtils.toStr(str1, s));

                break;
            case R.id.btn_8:
                tvShow.setText(StringUtils.randomInt(6));

                break;
            case R.id.btn_9:
                tvShow.setText(StringUtils.randomInt(5, 56, 99));
//                tvShow.setText(StringUtils.randomString16(6));
                break;
            case R.id.btn_10:
                tvShow.setText(StringUtils.randomString(10));
                break;
        }
    }
}
