package org.icegeneral.rxjavaapi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public abstract class RXAdapter extends RecyclerView.Adapter<RXAdapter.MyViewHolder> implements View.OnClickListener {

    public RXAdapter(Context context) {
        this.context = context;
    }

    private Context context;
    private String[] data;

    public void setData(String[] data) {
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rxjava_rv_item, parent, false);
        v.setOnClickListener(this);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(data[position] );
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.length;
    }

    @Override
    public void onClick(View v) {
        onItemClick((int) v.getTag());
    }

    public abstract void onItemClick(int position);

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}