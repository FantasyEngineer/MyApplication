package com.hjg.hjgapplife.activity.viewpager;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import java.util.List;

public class YLeiPageAdapter extends PagerAdapter {
	public  static final String TAG = "YLeiPageAdapter";

	private Activity mContext = null;
	private List<View> mListViews;

	public YLeiPageAdapter(Activity context, List<View> list) {
		mContext = context;
		mListViews = list;

	}

	@Override
	public Object instantiateItem(View collection, int position) {
		Log.d(TAG, "[YLeiPageAdapter]----->instantiateItem");
		((ViewPager) collection).addView(mListViews.get(position), 0);
		return mListViews.get(position);

	}

	@Override
	public int getCount() {
		//Log.d(TAG, "[YLeiPageAdapter]----->getCount");
		return mListViews.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		Log.d(TAG, "[YLeiPageAdapter]----->isViewFromObject");
		return (view == object);
	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		Log.d(TAG, "[YLeiPageAdapter]----->destroyItem");
		((ViewPager) collection).removeView(mListViews.get(position));
	}

}
