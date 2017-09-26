package com.hjg.hjgapplife.activity.BLE.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Administrator on 2017/9/25 0025.
 */

public class MultipleItem implements MultiItemEntity {
    public static final int ME = 1;
    public static final int OTHER = 2;

    public String content;

    public int itemType;

    @Override
    public int getItemType() {
        return itemType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
