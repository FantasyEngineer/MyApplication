package com.hjg.hjgapplife.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hjg.baseapp.util.glide.GlideCircleTransform;
import com.hjg.baseapp.widget.zoomview.OnScrollValueListener;
import com.hjg.baseapp.widget.zoomview.PullToZoomBase;
import com.hjg.baseapp.widget.zoomview.PullToZoomRecyclerViewEx;
import com.hjg.baseapp.widget.zoomview.RecyclerViewHeaderAdapter;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.base.BaseFragment;

/**
 * Created by Administrator on 2017/8/15 0015.
 * 首页--我的
 */

public class FourFragment extends BaseFragment {
    private static FourFragment fourFragment;
    private PullToZoomRecyclerViewEx listView;
    private RelativeLayout topBar;
    private TextView tvTopTextTitle;
    private View headerView;//头部布局
    private ImageView iv_user_head;

    public static FourFragment getInstance() {
        if (fourFragment == null)
            fourFragment = new FourFragment();
        return fourFragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_four;
    }

    @Override
    protected void initView() {
        listView = (PullToZoomRecyclerViewEx) findViewById(R.id.recyclerview);
        topBar = (RelativeLayout) findViewById(R.id.topBar);
        tvTopTextTitle = (TextView) findViewById(R.id.tvTopTextTitle);
        tvTopTextTitle.setVisibility(View.VISIBLE);
        tvTopTextTitle.setText("个人中心");
        topBar.setPadding(0, 30, 0, 0);
    }

    @Override
    public void initListenAndSetAndAdes() {
    }

    @Override
    public void initData() {
        final RecyclerViewHeaderAdapter mAdapter = new RecyclerAdapterCustom(activity);
        final GridLayoutManager manager = new GridLayoutManager(activity, 2);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.getItemViewType(position) == RecyclerViewHeaderAdapter.INT_TYPE_HEADER ? 2 : 1;
            }
        });
        listView.setAdapterAndLayoutManager(mAdapter, manager);


        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenWidth = localDisplayMetrics.widthPixels;
        AbsListView.LayoutParams localObject = new AbsListView.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 12.0F)));
        listView.setHeaderLayoutParams(localObject);
        headerView = listView.getHeaderView();
        listView.setOnPullZoomListener(new PullToZoomBase.OnPullZoomListener() {
            @Override
            public void onPullZooming(int newScrollValue) {
                Log.d("PullToZoomRecyclerActiv", "newScrollValue:" + newScrollValue);
            }

            @Override
            public void onPullZoomEnd() {
                Log.d("PullToZoomRecyclerActiv", "onPullZoomEnd");
            }
        });
        listView.setOnScrollValueListener(new OnScrollValueListener() {
            @Override
            public void onValue(double y) {
                Log.d("PullToZoomRecyclerActiv", "value" + y);
                if (y <= 0) {
                    topBar.setBackgroundColor(Color.argb((int) 0, 255, 140, 0));//AGB由相关工具获得，或者美工提供
                } else if (y > 0 && y <= 400) {
                    float scale = (float) y / 400;
                    float alpha = (255 * scale);
                    // 只是layout背景透明(仿知乎滑动效果)
                    topBar.setBackgroundColor(Color.argb((int) alpha, 255, 140, 0));
                } else {
                    topBar.setBackgroundColor(getResources().getColor(R.color.darkorange));
                }
            }
        });
        iv_user_head = (ImageView) headerView.findViewById(R.id.iv_user_head);
        String url = "http://img.zcool.cn/community/016fa1587b3965a8012060c89283f6.jpg";
        Glide.with(this).load(url).transform(new GlideCircleTransform(activity)).placeholder(R.mipmap.circle_bg).error(R.mipmap.circle_bg).into(iv_user_head);

    }

//    @Override
//    public void onClick(View view) {
//        MyDialog dialog = new MyDialog(activity);
//        dialog.setLayoutView(LayoutInflater.from(activity)
//                .inflate(R.layout.dialog_layout, null));
//        dialog.setDialogGravity(MyDialog.DialogGravity.CENTERBOTTOM);
//        dialog.show();
//    }


    public class RecyclerAdapterCustom extends RecyclerViewHeaderAdapter<ViewHolderRecyclerPullToZoom> {
        final String[] adapterData = new String[]{"Activity", "Service", "Content Provider", "Intent", "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient",
                "DDMS", "Android Studio", "Fragment", "Loader", "Activity", "Service", "Content Provider", "Intent",
                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient", "Activity", "Service", "Content Provider", "Intent",
                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient", "Activity", "Service", "Content Provider", "Intent", "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient",
                "DDMS", "Android Studio", "Fragment", "Loader", "Activity", "Service", "Content Provider", "Intent",
                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient", "Activity", "Service", "Content Provider", "Intent",
                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient", "Activity", "Service", "Content Provider", "Intent", "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient",
                "DDMS", "Android Studio", "Fragment", "Loader", "Activity", "Service", "Content Provider", "Intent",
                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient", "Activity", "Service", "Content Provider", "Intent",
                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient", "Activity", "Service", "Content Provider", "Intent", "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient",
                "DDMS", "Android Studio", "Fragment", "Loader", "Activity", "Service", "Content Provider", "Intent",
                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient", "Activity", "Service", "Content Provider", "Intent",
                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient", "Activity", "Service", "Content Provider", "Intent", "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient",
                "DDMS", "Android Studio", "Fragment", "Loader", "Activity", "Service", "Content Provider", "Intent",
                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient", "Activity", "Service", "Content Provider", "Intent",
                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient"};

        public RecyclerAdapterCustom(Context context) {
            super(context);
        }

        @Override
        public int getCount() {
            return adapterData.length;
        }

        @Override
        public ViewHolderRecyclerPullToZoom onCreateContentView(ViewGroup parent, int viewType) {
            return new ViewHolderRecyclerPullToZoom(new TextView(getContext()));
        }

        @Override
        public void onBindView(ViewHolderRecyclerPullToZoom view, int position) {

            view.mtextview.setText(adapterData[position]);

//            final StaggeredGridLayoutManager.LayoutParams lp =
//                    (StaggeredGridLayoutManager.LayoutParams) view.mtextview.getLayoutParams();
////
//            lp.span = span;
//            lp.height = size;
//            itemView.setLayoutParams(lp);


        }
    }

    public static class ViewHolderRecyclerPullToZoom extends RecyclerView.ViewHolder {

        TextView mtextview;

        public ViewHolderRecyclerPullToZoom(View itemView) {
            super(itemView);

            mtextview = (TextView) itemView;
        }
    }
}
