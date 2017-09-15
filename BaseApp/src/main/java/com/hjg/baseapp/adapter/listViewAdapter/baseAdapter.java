package com.hjg.baseapp.adapter.listViewAdapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 基础适配器
 *
 * @author tongxu_li
 *         Copyright (c) 2014 Shanghai P&C Information Technology Co., Ltd.
 */
@SuppressWarnings("rawtypes")
public abstract class baseAdapter extends BaseAdapter {
    protected Context context;
    protected List items;

    @Override
    public int getCount() {
        if (items == null || items.size() == 0) {
            return 0;
        }
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        if (items == null || items.size() == 0) {
            return null;
        }
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 获取数据源
     */
    public List getItems() {
        return items;
    }

    /**
     * 设置数据源
     */
    public void setItems(List items) {
        this.items = items;
    }

    /**
     * 更改数据源
     */
    public void changeItems(List items) {
        setItems(items);
        this.notifyDataSetChanged();
    }

    /**
     * 删除数据源元素
     */
    public void deleteItem(Object item) {
        if (items.remove(item)) {
            this.notifyDataSetChanged();
        }
    }

    /**
     * 添加数据源元素
     */
    @SuppressWarnings("unchecked")
    public void addItem(Object item) {
        if (items.add(item)) {
            this.notifyDataSetChanged();
        }
    }
}
