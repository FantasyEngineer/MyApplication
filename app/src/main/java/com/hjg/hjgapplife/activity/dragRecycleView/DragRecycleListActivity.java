package com.hjg.hjgapplife.activity.dragRecycleView;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.hjg.baseapp.ItemDecoration.RecyclerViewDivider;
import com.hjg.baseapp.util.ACache;
import com.hjg.baseapp.util.ToastUtil;
import com.hjg.baseapp.util.VibratorUtil;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;
import com.hjg.hjgapplife.activity.dragRecycleView.adapter.DragListRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 可以拖拽的listview
 */
public class DragRecycleListActivity extends BaseOthreRenderSwipActivity {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    private List<Item> results = new ArrayList<Item>();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_drag_recycle_list;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "可以拖拽的ListView");
        topBarManage.setLeftBtnBack(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void initData() {
        /////////初始化数据，如果缓存中有就使用缓存中的
        ArrayList<Item> items = (ArrayList<Item>) ACache.get(activity).getAsObject("listItems");
        if (items != null)
            results.addAll(items);
        else {
            results.add(new Item(0, "收款", R.mipmap.icon_grid));
            results.add(new Item(1, "转账", R.mipmap.icon_grid));
            results.add(new Item(2, "余额宝", R.mipmap.icon_grid));
            results.add(new Item(3, "手机充值", R.mipmap.icon_grid));
            results.add(new Item(4, "医疗", R.mipmap.icon_grid));
            results.add(new Item(5, "彩票", R.mipmap.icon_grid));
            results.add(new Item(6, "电影", R.mipmap.icon_grid));
            results.add(new Item(7, "游戏", R.mipmap.icon_grid));
        }
        DragListRecyclerAdapter adapter = new DragListRecyclerAdapter(R.layout.item_list_drag, results);
        recycleView.setHasFixedSize(true);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(activity));
        //添加默认的item动画
        recycleView.setItemAnimator(new DefaultItemAnimator());
        //添加横线分割线
        recycleView.addItemDecoration(new RecyclerViewDivider(activity, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.darkorange)));
        //拖拽位置改变计入缓存
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(adapter).setOnDragListener(new MyItemTouchCallback.OnDragListener() {
            @Override
            public void onFinishDrag() {
                //将位置存入缓存
                ACache.get(activity).put("listItems", (ArrayList<Item>) results);
            }
        }));
        //拖拽工具与recycleview绑定
        itemTouchHelper.attachToRecyclerView(recycleView);
        //recycleview的移动事件监听
        adapter.setListener(new OnLongClickListener() {
            @Override
            public void onLongClick(DragListRecyclerAdapter.MyViewHolder vh, View v) {
                ToastUtil.show(activity, "可以拖动吗");
                itemTouchHelper.startDrag(vh);
                VibratorUtil.Vibrate(activity, 30);
            }
        });
//        recycleView.addOnItemTouchListener(new OnRecyclerItemClickListener(recycleView) {
//            @Override
//            public void onLongClick(RecyclerView.ViewHolder vh) {
//                //最后一个不准移动
////                if (vh.getLayoutPosition() != results.size() - 1) {
////                    itemTouchHelper.startDrag(vh);
////                    VibratorUtil.Vibrate(activity, 30);
////                }
//                itemTouchHelper.startDrag(vh);
//                VibratorUtil.Vibrate(activity, 30);
//            }
//        });
    }

}
