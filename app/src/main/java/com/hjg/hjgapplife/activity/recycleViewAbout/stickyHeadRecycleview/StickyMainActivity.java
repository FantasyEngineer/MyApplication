package com.hjg.hjgapplife.activity.recycleViewAbout.stickyHeadRecycleview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;
import com.hjg.hjgapplife.activity.recycleViewAbout.stickyHeadRecycleview.adapter.BaseHeaderAdapter;
import com.hjg.hjgapplife.activity.recycleViewAbout.stickyHeadRecycleview.entitiy.PinnedHeaderEntity;
import com.oushangfeng.pinnedsectionitemdecoration.PinnedHeaderItemDecoration;
import com.oushangfeng.pinnedsectionitemdecoration.callback.OnHeaderClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import razerdp.listener.OnItemListener;
import razerdp.popup.AsDropDownPopup;

import static com.hjg.hjgapplife.R.mipmap.i;
import static com.hjg.hjgapplife.activity.animation.AutoUPChangeActivity.dip2px;

public class StickyMainActivity extends BaseOthreRenderSwipActivity {
    ArrayList list = new ArrayList<String>(Arrays.asList("线性", "网格", "瀑布流"));
    @BindView(R.id.stickyRecycleView)
    RecyclerView stickyRecycleView;
    private BaseHeaderAdapter<PinnedHeaderEntity<String>> mAdapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_sticky_main;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "recycleview粘性头部");
        topBarManage.setRightButtonImgAndTxt(true, null, "选择", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AsDropDownPopup menuPopup = new AsDropDownPopup(activity, list);
                menuPopup.showPopupWindow(topBarManage.getRightBtn());
                menuPopup.setOnItemListener(new OnItemListener() {
                    @Override
                    public void onItem(AdapterView<?> adapterView, View view, int i, long l) {
                        menuPopup.dismiss();
                        switch (i) {
                            case 0://linearlayout
                                stickyRecycleView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                                mAdapter.notifyDataSetChanged();
                                break;
                            case 1://gridlayout
                                stickyRecycleView.setLayoutManager(new GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false));
                                mAdapter.onAttachedToRecyclerView(stickyRecycleView);
                                mAdapter.notifyDataSetChanged();
                                break;
                            case 2:
                                stickyRecycleView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                                mAdapter.notifyDataSetChanged();
                                break;

                        }
                    }
                });
            }

        });
    }

    private String[] mDogs = {"哈士奇", "藏獒", "松狮", "贵宾犬", "牧羊犬", "秋田犬", "博美", "柴犬", "吉娃娃"};
    private String[] mCats = {"暹罗猫", "布偶猫", "苏格兰折耳猫", "英国短毛猫", "波斯猫", "埃及猫", "挪威森林猫",};
    private String[] mRabbits = {"荷兰侏儒兔", "迷你垂耳兔", " 荷兰垂耳兔", " 荷兰兔", " 巨型安哥拉兔", " 法国垂耳兔", " 海棠兔", " 巨型花明兔", " 英国垂耳兔", " 侏儒海棠兔", " 波兰兔", " 多瓦夫兔"};
    private String[] mPandas = {"花猫", "花熊", "华熊", "竹熊", "花头熊", "银狗", "大浣熊", "峨曲", "杜洞尕", "执夷", "貊", "猛豹", "猛氏兽", "食铁兽", "大猫熊", "熊猫", "貔貅", "白熊"};


    @Override
    protected void initData() {
        //填充数据
        List<PinnedHeaderEntity<String>> data = new ArrayList<>();
        data.add(new PinnedHeaderEntity<>("", BaseHeaderAdapter.TYPE_HEADER, "狗狗种类"));
        for (String dog : mDogs) {
            data.add(new PinnedHeaderEntity<>(dog, BaseHeaderAdapter.TYPE_DATA, "狗狗种类"));
        }
        data.add(new PinnedHeaderEntity<>("", BaseHeaderAdapter.TYPE_HEADER, "猫咪种类"));
        for (String cat : mCats) {
            data.add(new PinnedHeaderEntity<>(cat, BaseHeaderAdapter.TYPE_DATA, "猫咪种类"));
        }
        data.add(new PinnedHeaderEntity<>("", BaseHeaderAdapter.TYPE_HEADER, "兔子种类"));
        for (String rabbit : mRabbits) {
            data.add(new PinnedHeaderEntity<>(rabbit, BaseHeaderAdapter.TYPE_DATA, "兔子种类"));
        }
        data.add(new PinnedHeaderEntity<>("", BaseHeaderAdapter.TYPE_HEADER, "熊猫别称"));
        for (String panda : mPandas) {
            data.add(new PinnedHeaderEntity<>(panda, BaseHeaderAdapter.TYPE_DATA, "熊猫别称"));
        }
        mAdapter = new BaseHeaderAdapter<PinnedHeaderEntity<String>>(data) {

            private SparseIntArray mRandomHeights;

            @Override
            protected void addItemTypes() {
                addItemType(BaseHeaderAdapter.TYPE_HEADER, R.layout.item_pinned_header);
                addItemType(BaseHeaderAdapter.TYPE_DATA, R.layout.item_data);
            }

            @Override
            protected void convert(BaseViewHolder holder, PinnedHeaderEntity<String> item) {

                switch (holder.getItemViewType()) {
                    case BaseHeaderAdapter.TYPE_HEADER:
                        holder.setText(R.id.tv_animal, item.getPinnedHeaderName());
                        break;
                    case BaseHeaderAdapter.TYPE_DATA:
                        int position = holder.getLayoutPosition();
                        if (stickyRecycleView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                            // 瀑布流布局记录随机高度，就不会导致Item由于高度变化乱跑，导致画分隔线出现问题
                            // 随机高度, 模拟瀑布效果.
                            if (mRandomHeights == null) {
                                mRandomHeights = new SparseIntArray(getItemCount());
                            }
                            if (mRandomHeights.get(position) == 0) {
                                mRandomHeights.put(position, dip2px(activity, (int) (100 + Math.random() * 100)));
                            }
                            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
                            lp.height = mRandomHeights.get(position);
                            holder.itemView.setLayoutParams(lp);

                        }

                        holder.setText(R.id.tv_pos, position + "");
                        holder.setText(R.id.tv_animalName, item.getData());
//                        Glide.with(activity).load(item.getData()).into((ImageView) holder.getView(R.id.iv_animal));
                        break;
                }


            }
        };
        stickyRecycleView.setHasFixedSize(false);
        stickyRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //recycleView的列表点击相应
        stickyRecycleView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (mAdapter.getItemViewType(position)) {
                    case BaseHeaderAdapter.TYPE_DATA:
                        PinnedHeaderEntity<String> entity = mAdapter.getData().get(position);
                        Toast.makeText(activity, entity.getPinnedHeaderName() + ", position " + i + ", id " + entity.getData(), Toast.LENGTH_SHORT).show();
                        break;
                    case BaseHeaderAdapter.TYPE_HEADER:
                        entity = mAdapter.getData().get(position);
                        Toast.makeText(activity, "click, tag: " + entity.getPinnedHeaderName(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        //粘性头部点击事件（单击，双击，长按事件）
        OnHeaderClickListener headerClickListener = new OnHeaderClickListener() {
            @Override
            public void onHeaderClick(View view, int id, int position) {
                Toast.makeText(activity, "单击：" + mAdapter.getData().get(position).getPinnedHeaderName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onHeaderLongClick(View view, int id, int position) {
                Toast.makeText(activity, "长按点击: " + mAdapter.getData().get(position).getPinnedHeaderName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onHeaderDoubleClick(View view, int id, int position) {
                Toast.makeText(activity, "双击: " + mAdapter.getData().get(position).getPinnedHeaderName(), Toast.LENGTH_SHORT).show();
            }
        };
        //添加分割线
        stickyRecycleView.addItemDecoration(new PinnedHeaderItemDecoration.Builder(BaseHeaderAdapter.TYPE_HEADER).setDividerId(R.drawable.divider).enableDivider(true)
                .setHeaderClickListener(headerClickListener).create());
        stickyRecycleView.setAdapter(mAdapter);
    }

}
