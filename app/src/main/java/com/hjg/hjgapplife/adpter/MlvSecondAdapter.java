package com.hjg.hjgapplife.adpter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjg.baseapp.adapter.listViewAdapter.mBaseAdapter;
import com.hjg.baseapp.adapter.listViewAdapter.mViewHolder;
import com.hjg.hjgapplife.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18 0018.
 */

public class MlvSecondAdapter extends mBaseAdapter<String> {
    public MlvSecondAdapter(Context context, List lst) {
        super(context, lst);
    }

    @Override
    public int[] getFindViewByIDs(int position) {
        return new int[]{
                R.id.iv_set, R.id.tv_set_name
        };
    }

    @Override
    public int getLayout(int position) {
        return R.layout.layout_set_item;
    }

    @Override
    public void renderData(View convertView, int position, mViewHolder vh) {
        ImageView imageView = vh.getView(ImageView.class, R.id.iv_set);
        TextView tv_set_name = vh.getView(TextView.class, R.id.tv_set_name);
        tv_set_name.setText(getList().get(position) + "");
    }
}
