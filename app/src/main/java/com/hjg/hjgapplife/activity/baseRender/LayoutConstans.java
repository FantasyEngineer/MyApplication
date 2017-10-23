package com.hjg.hjgapplife.activity.baseRender;

/**
 * Created by Administrator on 2017/10/23 0023.
 */

public class LayoutConstans {
    public static final int LL = 1;//容器布局是线性布局（bar在布局之上，布局如何滑动，bar都在最上面）
    public static final int RL = 2;//容器布局是相对布局（bar覆盖在布局之上）
    public static final int BAR_IN_SV = 3;//容器布局是滑动scrollview，bar在滑动布局内，随着scroll的向上滑动，bar会消失
    public static final int BAR_OUT_SV = 4;//容器布局是滑动scrollview，bar在滑动布局外，随着scroll的向上滑动，bar永远在最上面，效果与LL布局内嵌套scrollview的效果一致
}
