package com.hjg.hjgapplife.activity.dragRecycleView;

import android.view.View;

import com.hjg.hjgapplife.activity.dragRecycleView.adapter.DragListRecyclerAdapter;

/**
 * Created by Administrator on 2017/8/29 0029.
 */

public interface OnLongClickListener {
    void onLongClick(DragListRecyclerAdapter.MyViewHolder holder, View v);
}
