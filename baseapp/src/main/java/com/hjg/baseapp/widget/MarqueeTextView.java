package com.hjg.baseapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 侯继国 on 2017/8/17 0017.
 * 跑马灯效果的textview
 */

/**
 * <com.hjg.baseapp.widget.MarqueeTextView
 * android:layout_width="match_parent"
 * android:layout_height="wrap_content"
 * android:layout_centerInParent="true"
 * android:ellipsize="marquee"
 * android:marqueeRepeatLimit="marquee_forever"
 * android:padding="20dp"
 * android:singleLine="true"
 * android:text="收集开源的lib，将自己写的东西，项目中用的东西都集成起来，以便以后很好的找到           " />
 */
public class MarqueeTextView extends TextView {

    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }

}