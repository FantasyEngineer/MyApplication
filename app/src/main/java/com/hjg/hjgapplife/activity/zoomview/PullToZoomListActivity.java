package com.hjg.hjgapplife.activity.zoomview;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.hjg.baseapp.widget.zoomview.PullToZoomListViewEx;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;

import java.util.ArrayList;

import razerdp.listener.OnItemListener;
import razerdp.popup.AsDropDownPopup;

public class PullToZoomListActivity extends BaseOthreRenderSwipActivity implements OnItemListener {
    private PullToZoomListViewEx listView;
    private ArrayList list = new ArrayList();
    private AsDropDownPopup asDropDownPopup;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_pull_to_zoom_list;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "PullToZoomListActivity");
        topBarManage.setRightButtonImgAndTxt(true, null, "功能", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asDropDownPopup.showPopupWindow(topBarManage.getRightBtn());
            }
        });
    }

    @Override
    protected void initData() {
        list.add("正常的滑动消失");
        list.add("覆盖式的消失");
        list.add("隐藏头部布局");
        list.add("展示头部布局");
        list.add("禁止下拉放大");
        list.add("支持下拉放大");
        asDropDownPopup = new AsDropDownPopup(activity, list);
        asDropDownPopup.setOnItemListener(this);

        listView = (PullToZoomListViewEx) findViewById(R.id.listview);
        String[] adapterData = new String[]{"Activity", "Service", "Content Provider", "Intent", "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient",
                "DDMS", "Android Studio", "Fragment", "Loader", "Activity", "Service", "Content Provider", "Intent",
                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient", "Activity", "Service", "Content Provider", "Intent",
                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient"};

        listView.setAdapter(new ArrayAdapter<String>(PullToZoomListActivity.this, android.R.layout.simple_list_item_1, adapterData));
        listView.getPullRootView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("zhuwenwu", "position = " + position);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("zhuwenwu", "position = " + position);
            }
        });

        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        AbsListView.LayoutParams localObject = new AbsListView.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
        listView.setHeaderLayoutParams(localObject);
    }

    @Override
    public void onItem(AdapterView<?> adapterView, View view, int i, long l) {
        asDropDownPopup.dismiss();
        switch (i) {
            case 0:
                listView.setParallax(false);
                break;
            case 1:
                listView.setParallax(true);
                break;
            case 2:
                listView.setHideHeader(false);
                break;
            case 3:
                listView.setHideHeader(true);
                break;
            case 4:
                listView.setZoomEnabled(false);
                break;
            case 5:
                listView.setZoomEnabled(true);
                break;
        }
    }
}
