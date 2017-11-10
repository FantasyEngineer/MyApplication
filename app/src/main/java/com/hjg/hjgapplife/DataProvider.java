package com.hjg.hjgapplife;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/8 0008.
 */

public class DataProvider {
    //提供首页banner的地址
    public static ArrayList<String> getBannerList() {
        ArrayList<String> bannerList = new ArrayList<>();
        bannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502871861358&di=efef08f0e9f2f7457b002d5d060a426e&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0173765821b703a84a0e282b8a197c.jpg%40900w_1l_2o_100sh.jpg");
        bannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502871861359&di=c243a73a01770d7c9f92efbb9b70f734&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fback_pic%2Fqk%2Fback_origin_pic%2F00%2F03%2F11%2F3e0210f7a00859a4ae0a9991fcbbe8b2.jpg");
        bannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502876203382&di=63654525f49723acdf61ff2e68f2ad07&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01cc9c57bc1c7d0000012e7e53d8b8.jpg%40900w_1l_2o_100sh.jpg");
        bannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502871861364&di=9d46cece0494f0490407356698c642b3&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01859757e372de0000012e7e38b66b.jpg%40900w_1l_2o_100sh.jpg");
        return bannerList;
    }

    //提供首页循环上下滚动的条目
    public static ArrayList<String> getTitleList() {
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("我和你男和女都逃不过爱情");
        titleList.add("谁愿意有勇气 不顾一切付出真心");
        titleList.add("你说的不只你 还包括我自己");
        titleList.add("该不该再继续 该不该有回应");
        titleList.add("让爱一步一步靠近");
        titleList.add("我对你有一点动心");
        titleList.add("却如此害怕看你的眼睛");
        titleList.add("有那么一点点动心 一点点迟疑");
        titleList.add("害怕爱过以后还要失去");
        titleList.add("人最怕就是动了情");
        return titleList;
    }

}
