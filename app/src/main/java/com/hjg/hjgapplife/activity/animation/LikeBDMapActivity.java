package com.hjg.hjgapplife.activity.animation;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hjg.baseapp.util.ScreenUtils;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;
import com.yinglan.scrolllayout.ScrollLayout;

public class LikeBDMapActivity extends BaseOthreRenderSwipActivity {

    @Override
    protected int getContentLayout() {
        return R.layout.activity_like_bdmap;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "仿百度地图上拉滑动效果");
    }

    @Override
    protected void initData() {
        initView();
    }

    private void initView() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.root);
        mScrollLayout = (ScrollLayout) findViewById(R.id.scroll_down_layout);
        text_foot = (TextView) findViewById(R.id.text_foot);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new ListviewAdapter(this));
        Button button = (Button) findViewById(R.id.btn_go_second);

        /**设置 setting*/
        mScrollLayout.setMinOffset(0);
        mScrollLayout.setMaxOffset((int) (ScreenUtils.getScreenHeight(this) * 0.5));
        mScrollLayout.setExitOffset(ScreenUtils.dp2px(this, 100));
        mScrollLayout.setIsSupportExit(true);
        mScrollLayout.setAllowHorizontalScroll(true);
        mScrollLayout.setOnScrollChangedListener(mOnScrollChangedListener);
        mScrollLayout.setToExit();

        mScrollLayout.getBackground().setAlpha(0);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollLayout.scrollToExit();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, LikeBDMap2Activity.class));
            }
        });
        findViewById(R.id.btn_go_third).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, LikeBDMapRecyActivity.class));
            }
        });
        text_foot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollLayout.setToOpen();
            }
        });
    }


    private ScrollLayout mScrollLayout;
    private TextView text_foot;

    private ScrollLayout.OnScrollChangedListener mOnScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            if (currentProgress >= 0) {
                float precent = 255 * currentProgress;
                if (precent > 255) {
                    precent = 255;
                } else if (precent < 0) {
                    precent = 0;
                }
                mScrollLayout.getBackground().setAlpha(255 - (int) precent);
            }
            if (text_foot.getVisibility() == View.VISIBLE)
                text_foot.setVisibility(View.GONE);
        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
            if (currentStatus.equals(ScrollLayout.Status.EXIT)) {
                text_foot.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onChildScroll(int top) {
        }
    };


    public class ListviewAdapter extends BaseAdapter {

        private Context mContext;

        public ListviewAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return 50;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewholder;

            if (null == convertView) {
                convertView = View.inflate(mContext, R.layout.item_list_drag, null);
                viewholder = new ViewHolder(convertView);
                convertView.setTag(viewholder);
            } else {
                viewholder = (ViewHolder) convertView.getTag();
            }

            viewholder.textView.setText("测试" + position);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "点击了" + position, Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }

        class ViewHolder {
            TextView textView;

            public ViewHolder(View view) {
                textView = (TextView) view.findViewById(R.id.item_text);
            }
        }
    }
}
