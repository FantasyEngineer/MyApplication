package com.hjg.hjgapplife.activity.greenDao.entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Administrator on 2017/8/4 0004.
 */
@Entity
public class Person {
    @Id//    (autoincrement = true)主键自增
    private Long id;

    private String name;
    private int age;
    private boolean isBoy;

    @Generated(hash = 159949159)
    public Person(Long id, String name, int age, boolean isBoy) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.isBoy = isBoy;
    }

    @Generated(hash = 1024547259)
    public Person() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getIsBoy() {
        return this.isBoy;
    }

    public void setIsBoy(boolean isBoy) {
        this.isBoy = isBoy;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", isBoy=" + isBoy +
                '}';
    }
}
