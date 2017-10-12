package com.hjg.hjgapplife.fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.hjg.baseapp.ItemDecoration.DividerGridItemDecoration;
import com.hjg.baseapp.util.ACache;
import com.hjg.baseapp.util.VibratorUtil;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.BLE.BleMainActivity;
import com.hjg.hjgapplife.activity.GifActivity;
import com.hjg.hjgapplife.activity.animation.AnimationListActivity;
import com.hjg.hjgapplife.activity.base.BaseFragment;
import com.hjg.hjgapplife.activity.dragRecycleView.DragRecycleListActivity;
import com.hjg.hjgapplife.activity.dragRecycleView.Item;
import com.hjg.hjgapplife.activity.dragRecycleView.MyItemTouchCallback;
import com.hjg.hjgapplife.activity.dragRecycleView.OnRecyclerItemClickListener;
import com.hjg.hjgapplife.activity.dragRecycleView.adapter.DragGridRecyclerAdapter;
import com.hjg.hjgapplife.activity.md.MDMainActivity;
import com.hjg.hjgapplife.activity.pupwindow.PupWindowActivity;
import com.hjg.hjgapplife.activity.stickyHeadRecycleview.StickyMainActivity;
import com.hjg.hjgapplife.activity.takephoto.LikeWeChatSelectPhotoActivity;
import com.hjg.hjgapplife.activity.takephoto.PhotoDealMainActivity;
import com.hjg.hjgapplife.activity.webview.WebViewActivity;
import com.hjg.hjgapplife.activity.zoomview.PullToZoomMainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import razerdp.popup.DialogPopup;

/**
 * 可拖拽的gridview
 */

public class ThirdFragment extends BaseFragment {
    private static ThirdFragment thirdFragment;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerViewContainer;
    private List<Item> results = new ArrayList<Item>();
    private ItemTouchHelper itemTouchHelper;


    public static ThirdFragment getInstance() {
        if (thirdFragment == null)
            thirdFragment = new ThirdFragment();
        return thirdFragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_third;
    }

    @Override
    protected void initView() {
    }

    @Override
    public void onResume() {
        super.onResume();
        //高亮的开启
        shimmerViewContainer.setDuration(1000);
        shimmerViewContainer.setRepeatMode(ObjectAnimator.REVERSE);
        shimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void initListenAndSetAndAdes() {

        /////////初始化数据，如果缓存中有就使用缓存中的
        ArrayList<Item> items = (ArrayList<Item>) ACache.get(getActivity()).getAsObject("items");
        if (items != null) {
            //防止多次加载
            results.removeAll(results);
            results.addAll(items);
        } else {
            //防止多次加载
            results.removeAll(results);
            results.add(new Item(0, "可拖拽的 ListView", R.mipmap.icon_grid));
            results.add(new Item(1, "动画操作", R.mipmap.icon_grid));
            results.add(new Item(2, "PupWindow样式展示", R.mipmap.icon_grid));
            results.add(new Item(3, "GIF相关操作", R.mipmap.icon_grid));
            results.add(new Item(4, "文件管理器", R.mipmap.icon_grid));
            results.add(new Item(5, "下拉后背景变大", R.mipmap.icon_grid));
            results.add(new Item(6, "仿微信多选图片", R.mipmap.icon_grid));
            results.add(new Item(7, "蓝牙", R.mipmap.icon_grid));
            results.add(new Item(8, "图片处理", R.mipmap.icon_grid));
            results.add(new Item(9, "粘性标签", R.mipmap.icon_grid));
            results.add(new Item(10, "Material Design", R.mipmap.icon_grid));
            results.add(new Item(11, "app换肤", R.mipmap.icon_grid));
        }
//        results.remove(results.size() - 1);
//        results.add(new Item(results.size(), "更多", R.drawable.takeout_ic_more));
    }

    @Override
    public void initData() {
        DragGridRecyclerAdapter adapter = new DragGridRecyclerAdapter(R.layout.item_grid, results);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 4));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));

        itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(adapter).setOnDragListener(new MyItemTouchCallback.OnDragListener() {
            @Override
            public void onFinishDrag() {
                //将移动之后的位置list 存入缓存
                ACache.get(getActivity()).put("items", (ArrayList<Item>) results);
            }
        }));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                //最后一个不震动，不准移动
//                if (vh.getLayoutPosition() != results.size() - 1) {
//                    itemTouchHelper.startDrag(vh);
//                    VibratorUtil.Vibrate(getActivity(), 70);
//                }
                itemTouchHelper.startDrag(vh);
                VibratorUtil.Vibrate(getActivity(), 20);   //震动20ms
            }

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                Item item = results.get(vh.getLayoutPosition());
                switch (item.getId()) {
                    case 0:
                        startActivity(new Intent(activity, DragRecycleListActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(activity, AnimationListActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(activity, PupWindowActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(activity, GifActivity.class));
                        break;
                    case 4:
                        break;
                    case 5:
                        startActivity(new Intent(activity, PullToZoomMainActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(activity, LikeWeChatSelectPhotoActivity.class));
                        break;
                    case 7:
                        final DialogPopup dialogPopup = new DialogPopup(activity);
                        dialogPopup.setSingleBtn("知道了", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(activity, BleMainActivity.class));
                                dialogPopup.dismiss();
                            }
                        });
                        dialogPopup.setTitleAndContent("友情提醒", "  一部手机先要开启服务端，待服务端开启完毕后，另一部手机" +
                                "点击客户端，客户端点击连接，待两端连接成功之后，可以进行通信。");
                        dialogPopup.showPopupWindow();
                        break;
                    case 8:
                        startActivity(new Intent(activity, PhotoDealMainActivity.class));
                        break;
                    case 9:
                        startActivity(new Intent(activity, StickyMainActivity.class));
                        break;
                    case 10:
                        startActivity(new Intent(activity, MDMainActivity.class));
                        break;
                    case 11:
                        WebViewActivity.startActivityToWebView(activity, "https://github.com/burgessjp/ThemeSkinning", "app换肤");
                        break;
                }
            }
        });
    }
}
