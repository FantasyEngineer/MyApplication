package com.hjg.hjgapplife.activity.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hjg.baseapp.util.ScreenUtils;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class BezierLineActivity extends BaseOthreRenderSwipActivity {


    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.start_add)
    Button startAdd;
    @BindView(R.id.iv_shop_car)
    ImageView ivShopCar;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    private ImageView imageView;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_bezier_line;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "贝塞尔曲线的使用");


    }

    @Override
    protected void initData() {

    }

    private float[] mCurrentPosition = new float[2];//贝塞尔曲线执行过程中，点的坐标保存的数组

    @OnClick(R.id.start_add)
    public void onViewClicked() {
        startAnimation(iv);
    }

    public void startAnimation(View view) {

        //创建执行动画掉落的imageview
        imageView = new ImageView(this);
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.c));

        //执行动画掉落的生成的imageview的宽高。
        int newWidth = ScreenUtils.dp2px(this, 20);
        int newHeight = ScreenUtils.dp2px(this, 20);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(newWidth, newWidth);
//        params.setMargins((ivWidth - newWidth) / 2, (ivHeight - newHeight) / 2, 0, 0);

        rlContent.addView(imageView, params);


        //动画开始的位置为
        int[] startLocation = new int[2];
        view.getLocationOnScreen(startLocation);

        //得到购物车图片的坐标(用于计算动画结束后的坐标)
        int endLocation[] = new int[2];
        ivShopCar.getLocationOnScreen(endLocation);


//        正式开始计算动画开始/结束的坐标
        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        float startX = startLocation[0] + iv.getWidth() / 2;
        float startY = startLocation[1] + iv.getHeight() / 2;

        //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
        float toX = endLocation[0];
        float toY = endLocation[1];

        //开始绘制贝塞尔曲线
        Path path = new Path();
        //移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY);
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo((startX + toX), startY, toX, toY);
        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
        // 如果是true，path会形成一个闭环
        final PathMeasure mPathMeasure = new PathMeasure(path, false);

        //属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(1000);
        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                mPathMeasure.getPosTan(value, mCurrentPosition, null);//mCurrentPosition此时就是中间距离点的坐标值
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                imageView.setTranslationX(mCurrentPosition[0]);
                imageView.setTranslationY(mCurrentPosition[1]);

            }
        });
        valueAnimator.start();

//        动画结束后的处理
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            //当动画结束后：
            @Override
            public void onAnimationEnd(Animator animation) {
//                rlContent.removeView(imageView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
