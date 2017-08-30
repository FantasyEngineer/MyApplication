package com.hjg.hjgapplife.activity.dragRecycleView.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.dragRecycleView.Item;
import com.hjg.hjgapplife.activity.dragRecycleView.MyItemTouchCallback;
import com.hjg.hjgapplife.activity.dragRecycleView.OnLongClickListener;

import java.util.Collections;
import java.util.List;

/**
 * 可拖拽的ListRecycler的适配器
 */
public class DragListRecyclerAdapter extends RecyclerView.Adapter<DragListRecyclerAdapter.MyViewHolder> implements MyItemTouchCallback.ItemTouchAdapter {

    private Context context;
    private int src;
    private List<Item> results;
    private OnLongClickListener listener;

    public DragListRecyclerAdapter(int src, List<Item> results) {
        this.results = results;
        this.src = src;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(src, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.imageView.setImageResource(results.get(position).getImg());
        holder.textView.setText(results.get(position).getName());
        holder.imageDrag.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (listener != null) {
                    listener.onLongClick(holder, view);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
//        如果是最后一个则不能移动
//        if (fromPosition==results.size()-1 || toPosition==results.size()-1){
//            return;
//        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(results, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(results, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {
        results.remove(position);
        notifyItemRemoved(position);
    }

    public void setListener(OnLongClickListener listener) {
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView imageView;
        public ImageView imageDrag;

        public MyViewHolder(View itemView) {
            super(itemView);
//            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//            int width = wm.getDefaultDisplay().getWidth();
//            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
//            layoutParams.height = width / 4;
//            itemView.setLayoutParams(layoutParams);
            textView = (TextView) itemView.findViewById(R.id.item_text);
            imageView = (ImageView) itemView.findViewById(R.id.item_img);
            imageDrag = (ImageView) itemView.findViewById(R.id.item_drag);
        }
    }
}
