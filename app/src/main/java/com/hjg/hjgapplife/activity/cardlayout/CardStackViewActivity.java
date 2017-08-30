package com.hjg.hjgapplife.activity.cardlayout;

import android.os.Handler;
import android.util.Log;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.UpDownStackAnimatorAdapter;

import java.util.Arrays;

import butterknife.BindView;

/**
 * 卡片式布局（仿苹果提醒事项）
 */
public class CardStackViewActivity extends BaseOthreRenderSwipActivity {
    @BindView(R.id.card_view)
    CardStackView cardView;
    private TestStackAdapter mTestStackAdapter;
    public static Integer[] TEST_DATAS = new Integer[]{
            R.color.colorPrimary,
            R.color.colorAccent,
            R.color.yellow,
            R.color.notrans,
            R.color.halftransp,
            R.color.gold,
            R.color.lightpink,
            R.color.darkorange,
            R.color.hotpink,
            R.color.fuchsia,
            R.color.red,
            R.color.salmon,
            R.color.lightcoral,
            R.color.goldenrod,
            R.color.silver,
            R.color.darkgoldenrod,
            R.color.firebrick,
            R.color.paleturquoise,
            R.color.greenyellow,
            R.color.darkgrey,
            R.color.darkorchid,
            R.color.turquoise,
            R.color.limegreen,
            R.color.dodgerblue,
            R.color.black,
            R.color.colorAccent
    };

    @Override
    protected int getContentLayout() {
        return R.layout.activity_card_stack_view;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "卡片式布局（仿苹果提醒事项）");
    }

    @Override
    protected void initData() {
        mTestStackAdapter = new TestStackAdapter(activity);
        cardView.setAdapter(mTestStackAdapter);
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mTestStackAdapter.updateData(Arrays.asList(TEST_DATAS));
                    }
                }
                , 200
        );
        //是否展开卡片
        cardView.setItemExpendListener(new CardStackView.ItemExpendListener() {
            @Override
            public void onItemExpend(boolean expend) {
                Log.d("ThirdFragment", "expend:" + expend);
            }
        });
        //设置动画
        cardView.setAnimatorAdapter(new UpDownStackAnimatorAdapter(cardView));
//        cardView.setAnimatorAdapter(new UpDownAnimatorAdapter(cardView));
//        cardView.setAnimatorAdapter(new AllMoveDownAnimatorAdapter(cardView));
    }
}
