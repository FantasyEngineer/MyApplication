package com.hjg.hjgapplife.activity;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjg.baseapp.widget.AmountView;
import com.hjg.baseapp.widget.goodsview.GoodView;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ButtonStyleActivity extends BaseActivity {

    @BindView(R.id.amountView)
    AmountView amountView;
    @BindView(R.id.good)
    ImageView good;
    @BindView(R.id.good2)
    ImageView good2;
    @BindView(R.id.collection)
    ImageView collection;
    @BindView(R.id.bookmark)
    ImageView bookmark;
    @BindView(R.id.reset)
    TextView reset;
    @BindView(R.id.btn_count_time)
    Button btnCountTime;
    private GoodView mGoodView;
    private TimeCount timeCount;

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "Button的各种样式");
        topBarManage.setLeftBtnBack(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_button_style;
    }

    @Override
    protected void initView() {
//        购物车商品数量加减
        amountView.setFocus(false);
        amountView.setDefaultNum(5);
        amountView.setGoods_storage(100);
        amountView.setMinNum(2);

    }

    @Override
    protected void initData() {
        mGoodView = new GoodView(this);
        timeCount = new TimeCount(60 * 1000, 1000);
    }


    @OnClick({R.id.good, R.id.good2, R.id.collection, R.id.bookmark, R.id.reset, R.id.btn_count_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.good:
                ((ImageView) view).setImageResource(R.mipmap.good_checked);
                mGoodView.setText("+1");
                mGoodView.show(view);
                break;
            case R.id.good2:
                ((ImageView) view).setImageResource(R.mipmap.good_checked);
                mGoodView.setImage(getResources().getDrawable(R.mipmap.good_checked));
                mGoodView.show(view);
                break;
            case R.id.collection:
                ((ImageView) view).setImageResource(R.mipmap.collection_checked);
                mGoodView.setTextInfo("收藏成功", Color.parseColor("#f66467"), 12);
                mGoodView.show(view);
                break;
            case R.id.bookmark:
                ((ImageView) view).setImageResource(R.mipmap.bookmark_checked);
                mGoodView.setTextInfo("收藏成功", Color.parseColor("#ff941A"), 12);
                mGoodView.show(view);
                break;
            case R.id.reset:
                ((ImageView) findViewById(R.id.good)).setImageResource(R.mipmap.good);
                ((ImageView) findViewById(R.id.good2)).setImageResource(R.mipmap.good);
                ((ImageView) findViewById(R.id.collection)).setImageResource(R.mipmap.collection);
                ((ImageView) findViewById(R.id.bookmark)).setImageResource(R.mipmap.bookmark);
                mGoodView.reset();
                break;

            case R.id.btn_count_time:
                timeCount.start();
                break;
        }
    }

    //计时器
    private class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {//返回的每一秒的时间
            btnCountTime.setClickable(false);
            btnCountTime.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {//倒计时结束的时候
            btnCountTime.setClickable(true);
            btnCountTime.setText("重新获取");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeCount!=null){
            timeCount.cancel();
        }
    }
}
