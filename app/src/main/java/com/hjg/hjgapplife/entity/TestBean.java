package com.hjg.hjgapplife.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/24 0024.
 */

public class TestBean implements Serializable {

    private static final long serialVersionUID = -4686644884538215075L;

    private String createTime;
    private String messageContent;
    private int price;
    private int age;

    public TestBean(String createTime, String messageContent) {
        this.createTime = createTime;
        this.messageContent = messageContent;
    }

    public TestBean(String createTime, String messageContent, int price, int age) {
        this.createTime = createTime;
        this.messageContent = messageContent;
        this.price = price;
        this.age = age;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
