package com.hjg.hjgapplife.activity.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjg.baseapp.adapter.OnItemClickListener;
import com.hjg.baseapp.adapter.RvCommonAdapter;
import com.hjg.baseapp.adapter.ViewHolder;
import com.hjg.baseapp.widget.CircleImageView;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 仿淘宝点击商品加入到购物车特效
 */
public class ScaleActivity extends BaseOthreRenderSwipActivity {

    @BindView(R.id.iv_shop_car)
    ImageView ivShopCar;
    @BindView(R.id.tv_all_mark)
    TextView tvAllMark;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    private CircleImageView animImageView;
    private ViewGroup anim_mask_layout;
    private ArrayList<Integer> datas = new ArrayList<>();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_scale;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "仿淘宝点击商品加入到购物车特效");
    }

    @Override
    protected void initData() {
        datas.add(R.mipmap.a);
        datas.add(R.mipmap.b);
        datas.add(R.mipmap.c);
        datas.add(R.mipmap.d);
        datas.add(R.mipmap.e);
        datas.add(R.mipmap.f);
        datas.add(R.mipmap.g);
        initRecycleView();
        startAnimator(ivShopCar);
        startAnimator(tvAllMark);
    }


    private void initRecycleView() {
        recycleView.setHasFixedSize(false);
        recycleView.setLayoutManager(new LinearLayoutManager(activity));
        RvCommonAdapter adapter = new RvCommonAdapter<Integer>(activity, R.layout.item_recycle, datas) {

            @Override
            public void convert(final ViewHolder holder, Integer s, int position) {
                LinearLayoutManager linearManager = (LinearLayoutManager)
                        recycleView.getLayoutManager();
                //获取到recyleview的可见的position
                int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                int lastposition = linearManager.findLastVisibleItemPosition();
                Log.d("ScaleActivity", "lastItemPosition:" + lastposition);
                Log.d("ScaleActivity", "firstItemPosition:" + firstItemPosition);

                //添加图片
                holder.setImageResource(R.id.iv_bigshow, s);

                holder.getView(R.id.rl).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startAnim((ImageView) holder.getView(R.id.iv_bigshow));
                    }
                });

            }
        };
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
        recycleView.setAdapter(adapter);
    }

    /**
     * 放大缩小动画
     *
     * @param view 需要执行动画的view
     */
    private void startAnimator(View view) {
        AnimatorSet animatorSet = new AnimatorSet();//组合动画
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", 1, 1.5f, 1);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", 1, 1.5f, 1);
        animatorSet.setDuration(1000);
        animatorSet.play(animatorX).with(animatorY);//两个动画同时开始
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }


    /**
     * 添加到购物车中的动画
     *
     * @param view
     */
    public void startAnim(ImageView view) {
        // 记录开始的位置
        int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
        view.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）

        // 创建要做动画的ImageView
        animImageView = new CircleImageView(this);

        // 设置animImageView的背景
        Drawable background = view.getDrawable();
        Drawable zoomDrawable = zoomDrawable(background, dip2Px(activity, view.getWidth()),
                dip2Px(activity, view.getHeight()));
        animImageView.setBackgroundDrawable(zoomDrawable);

        // 开始执行动画
        setAnim(animImageView, startLocation, view);
    }

    /**
     * 设置动画
     *
     * @param v
     * @param startLocation
     * @param view
     */
    private void setAnim(final View v, int[] startLocation, final View view) {

        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);// 把动画小球添加到动画层
        final View viewa = addViewToAnimLayout(anim_mask_layout, v,
                startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        ivShopCar.getLocationInWindow(endLocation);// shopCart是那个购物车

        // 计算位移
        int endX = endLocation[0] - startLocation[0];// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标
        TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
                0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0.6f, 0.1f, 0.6f, 0.1f);
        scaleAnimation.setInterpolator(new AccelerateInterpolator());
        scaleAnimation.setRepeatCount(0);// 动画重复执行的次数
        scaleAnimation.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(scaleAnimation);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(1000);// 动画的执行时间
        viewa.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }
        });
    }

    /**
     * @param
     * @return void
     * @throws
     * @Description: 创建动画层
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) ((Activity) this).getWindow()
                .getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    /**
     * 将drawable对象进行指定大小的缩放
     *
     * @param drawable
     * @param w
     * @param h
     * @return
     */
    public Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable); // drawable 转换成 bitmap
        Matrix matrix = new Matrix(); // 创建操作图片用的 Matrix 对象
        float scaleWidth = ((float) w / width); // 计算缩放比例
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight); // 设置缩放比例
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true); // 建立新的 bitmap ，其内容是对原 bitmap 的缩放后的图
        return new BitmapDrawable(newbmp); // 把 bitmap 转换成 drawable 并返回
    }

    /**
     * 将drawable 转换成 bitmap
     *
     * @param drawable
     * @return
     */
    public Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth(); // 取 drawable 的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565; // 取 drawable 的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config); // 建立对应
        // bitmap
        Canvas canvas = new Canvas(bitmap); // 建立对应 bitmap 的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas); // 把 drawable 内容画到画布中
        return bitmap;
    }

    // dp转换为像素px
    public static int dip2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    @OnClick(R.id.iv_shop_car)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_shop_car://放大和缩小
                startAnimator(ivShopCar);
                startAnimator(tvAllMark);
                break;
        }

    }
}
