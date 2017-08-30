package com.hjg.hjgapplife.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.hjg.baseapp.ItemDecoration.DividerGridItemDecoration;
import com.hjg.baseapp.util.ACache;
import com.hjg.baseapp.util.ToastUtil;
import com.hjg.baseapp.util.VibratorUtil;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.base.BaseFragment;
import com.hjg.hjgapplife.activity.dragRecycleView.DragRecycleListActivity;
import com.hjg.hjgapplife.activity.dragRecycleView.Item;
import com.hjg.hjgapplife.activity.dragRecycleView.MyItemTouchCallback;
import com.hjg.hjgapplife.activity.dragRecycleView.OnRecyclerItemClickListener;
import com.hjg.hjgapplife.activity.dragRecycleView.adapter.DragGridRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 可拖拽的gridview
 */

public class ThirdFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<Item> results = new ArrayList<Item>();
    private ItemTouchHelper itemTouchHelper;


    public static ThirdFragment getInstance() {
        ThirdFragment sf = new ThirdFragment();
        return sf;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_third;
    }

    @Override
    protected void initView() {


    }

    @Override
    public void initListenAndSetAndAdes() {

        /////////初始化数据，如果缓存中有就使用缓存中的
        ArrayList<Item> items = (ArrayList<Item>) ACache.get(getActivity()).getAsObject("items");
        if (items != null)
            results.addAll(items);
        else {
            results.add(new Item(0, "可拖拽的ListView", R.mipmap.icon_grid));
            results.add(new Item(1, "转账", R.mipmap.icon_grid));
            results.add(new Item(2, "余额宝", R.mipmap.icon_grid));
            results.add(new Item(3, "手机充值", R.mipmap.icon_grid));
            results.add(new Item(4, "医疗", R.mipmap.icon_grid));
            results.add(new Item(5, "彩票", R.mipmap.icon_grid));
            results.add(new Item(6, "电影", R.mipmap.icon_grid));
            results.add(new Item(7, "游戏", R.mipmap.icon_grid));
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
                //存入缓存
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
                //弹出确认按下的是哪一个
                ToastUtil.show(activity, item.getName());
                switch (item.getId()) {
                    case 0:
                        startActivity(new Intent(activity, DragRecycleListActivity.class));
                        break;
                    case 1:
                        break;
                }
            }
        });
    }


}
