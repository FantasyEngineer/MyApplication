package com.hjg.hjgapplife.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class EventBusBean implements Serializable {
    public static final int CitySelect = 1; //城市选择标志


    private static final long serialVersionUID = -3145269997610640878L;
    private int flag;
    private String content;

    public EventBusBean(int flag, String content) {
        this.flag = flag;
        this.content = content;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
