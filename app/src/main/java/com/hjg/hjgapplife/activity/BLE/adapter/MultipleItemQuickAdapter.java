package com.hjg.hjgapplife.activity.BLE.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.BLE.entity.MultipleItem;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25 0025.
 */

public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    public MultipleItemQuickAdapter(List data) {
        super(data);
        addItemType(MultipleItem.ME, R.layout.itemchat1);
        addItemType(MultipleItem.OTHER, R.layout.itemchat2);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (item.itemType) {
            case MultipleItem.ME:
                helper.setText(R.id.content, item.getContent());
                break;
            case MultipleItem.OTHER:
                helper.setText(R.id.content, item.getContent());
                break;

        }
    }
}
