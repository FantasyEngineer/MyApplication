package com.hjg.hjgapplife.activity.animation;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;
import com.yinglan.scrolllayout.ScrollLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LikeBDMap2Activity extends BaseOthreRenderSwipActivity {
    private ScrollLayout mScrollLayout;
    private ArrayList<Address> mAllAddressList;
    private TextView mGirlDesText;
    private RelativeLayout topBar;
    private TextView tvTopTextTitle;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_like_bdmap2;
    }

    @Override
    protected void initTitle() {
//        topBarManage.iniTop(true, "嵌套ScrollView效果");
        topBarManage.setVisibleTopbar(false);
        topBar = (RelativeLayout) findViewById(R.id.topBar);
        tvTopTextTitle = (TextView) topBar.findViewById(R.id.tvTopTextTitle);
        tvTopTextTitle.setVisibility(View.VISIBLE);
        tvTopTextTitle.setText("嵌套ScrollView效果");
    }

    @Override
    protected void initData() {
        initGirlUrl();
        initView();
    }

    private void initView() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        mGirlDesText = (TextView) findViewById(R.id.text_view);
        mScrollLayout = (ScrollLayout) findViewById(R.id.scroll_down_layout);
        mScrollLayout.setOnScrollChangedListener(mOnScrollChangedListener);
        mScrollLayout.getBackground().setAlpha(0);

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(this);
        mainPagerAdapter.setOnClickItemListener(mOnClickItemListener);
        viewPager.setAdapter(mainPagerAdapter);
        viewPager.setOnPageChangeListener(mOnPageChangeListener);
        mainPagerAdapter.initViewUrl(mAllAddressList);
        mGirlDesText.setText(mAllAddressList.get(0).getDesContent());
    }

    private void initGirlUrl() {
        mAllAddressList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Address address = new Address();
            address.setImageUrl(ImageUrl[i]);
            address.setDesContent(DesContent[i]);
            mAllAddressList.add(address);
        }
    }

    private MainPagerAdapter.OnClickItemListenerImpl mOnClickItemListener = new MainPagerAdapter.OnClickItemListenerImpl() {
        @Override
        public void onClickItem(View item, int position) {
            if (mScrollLayout.getCurrentStatus() == ScrollLayout.Status.OPENED) {
                mScrollLayout.scrollToClose();
            } else {
//                startActivity(new Intent(SecondActivity.this, ThreeActivity.class));
            }
        }
    };

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
                topBar.getBackground().setAlpha(255 - (int) precent);
            }
        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
            if (currentStatus.equals(ScrollLayout.Status.EXIT)) {
//                finish();
            }
        }

        @Override
        public void onChildScroll(int top) {
        }
    };

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mGirlDesText.setText(mAllAddressList.get(position).getDesContent());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    public static class MainPagerAdapter extends PagerAdapter implements View.OnClickListener {

        private ArrayList<Address> mAllAddressList;
        private Map<Integer, View> mAllImageMap;
        private Context mContext;
        private OnClickItemListenerImpl mOnClickItemListener;

        @Override
        public void onClick(View v) {
            if (null != v && v instanceof ImageView) {
                if (mOnClickItemListener != null) {
                    int position = -1;
                    Address address = (Address) v.getTag();
                    if (mAllAddressList != null && address != null) {
                        position = mAllAddressList.indexOf(address);
                    }
                    mOnClickItemListener.onClickItem(v, position);
                }
            }
        }

        public MainPagerAdapter(Context context) {
            mContext = context;
            mAllImageMap = new HashMap<>();
            mAllAddressList = new ArrayList<>();
        }

        public void initViewUrl(ArrayList<Address> addresses) {
            if (null == addresses) return;
            mAllAddressList.clear();
            mAllAddressList.addAll(addresses);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mAllAddressList.size();
        }

        @Override
        public float getPageWidth(int position) {
            return super.getPageWidth(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            if (mAllImageMap.containsKey(position)) {
                container.removeView(mAllImageMap.get(position));
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView;
            Address address = mAllAddressList.get(position);
            if (mAllImageMap.containsKey(position)) {
                View oldView = mAllImageMap.get(position);
                Object tag = oldView.getTag();
                if (null != tag && tag instanceof Address) {
                    if (tag.equals(address)) {
                        itemView = oldView;
                        container.addView(itemView);
                        return itemView;
                    }
                }
                container.removeView(oldView);
                mAllImageMap.remove(position);
            }

            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(mContext).load(address.getImageUrl()).into(imageView);
            imageView.setTag(address);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
            mAllImageMap.put(position, imageView);
            itemView = imageView;
            itemView.setOnClickListener(this);
            container.addView(itemView);
            return itemView;
        }

        public void setOnClickItemListener(OnClickItemListenerImpl onClickItemListener) {
            mOnClickItemListener = onClickItemListener;
        }

        public interface OnClickItemListenerImpl {
            void onClickItem(View item, int position);
        }
    }


    public static String[] ImageUrl = {
            "http://f.hiphotos.baidu.com/baike/w%3D790/sign=f3eda0139652982205333bcae7c87b3b/11385343fbf2b21126dd199acc8065380dd78e77.jpg",
            "http://d.hiphotos.baidu.com/baike/c0%3Dbaike272%2C5%2C5%2C272%2C90/sign=20affa6640a98226accc2375ebebd264/060828381f30e92473f2e4224f086e061d95f74b.jpg",
            "http://d.hiphotos.baidu.com/baike/c0%3Dbaike180%2C5%2C5%2C180%2C60/sign=973a0a701a950a7b613846966bb809bc/0b46f21fbe096b632d160f160b338744ebf8ac6d.jpg",
            "http://e.hiphotos.baidu.com/baike/c0%3Dbaike150%2C5%2C5%2C150%2C50/sign=fdadcbfa8913632701e0ca61f0e6cb89/d8f9d72a6059252d38584a22379b033b5bb5b94d.jpg",
            "http://d.hiphotos.baidu.com/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=f8f2b61b8401a18be4e61a1dff466c6d/a2cc7cd98d1001e94a272438bb0e7bec54e797e9.jpg"
    };

    public static String[] DesContent = {
            "点击图片进入RecyclerView展示页面\n\n" +
                    "南极分东南极洲和西南极洲两部分。东南极洲从西经30°向东延伸到东经170°，包括科茨地、毛德皇后地、恩德比地、威尔克斯地、乔治五世海岸、维多利亚地、南极高原和极点。面积1018万平方千米。西南极洲位于西经50°-160°之间，包括南极半岛、亚历山大岛、埃尔斯沃思地以及伯德地（玛丽．伯德地）等，面积229万平方千米。南极洲仅有一些来自其它大陆的科学考查人员以及捕鲸队。南极是已知世界上唯一有陆地的极地。",
            "点击图片进入RecyclerView展示页面\n\n" + "北极（英文：North Pole；法文：Arctique）。（英文：North Pole；法文：Arctique）（英文：North Pole；法文：Arctique）（英文：North Pole；法文：Arctique）（英文：North Pole；法文：Arctique）北极地区的气候终年寒冷。北冰洋是一片浩瀚的冰封海洋，周围是众多的岛屿以及北美洲和亚洲北部的沿海地区。北极是指地球自转轴的北端，也就是北纬90°的那一点。北极地区是指北极附近北纬66°34′北极圈以内的地区。冬季，太阳始终在地平线以下，大海完全封冻结冰。夏季，气温上升到冰点以上，北冰洋的边缘地带融化，太阳连续几个星期都挂在天空。北冰洋中有丰富的鱼类和浮游生物，这为夏View展示页面\n\n" + "撒哈拉沙漠约形成于250万年前，是世界第1大荒漠（仅次于南极洲），也是世界最大的沙质荒漠。它位于非洲北部，该地区气候条件非常恶劣，是地球上最不适合生物生存的地方之一。其总面积约容得下整个美国本土。“撒哈拉”国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局季在这里筑巢的数百万只海鸟提供了丰富的食物来源。同时，也是海豹、鲸和其他海洋动物的食物。北冰洋周围的大部分地区都比较平坦，没有树木生长。冬季大地封冻，地面上覆盖着厚厚的积雪。夏天积雪融化，表层土解冻，植物生长开花，为驯鹿和麝牛等动物提供了食物。同时，狼和北极熊等食肉动物也依靠捕食其他动物得以存活。北极地区是世界上人口最稀少的地区之一。千百年以来，因纽特人（旧称爱斯基摩人）在这里世代繁衍。在这里发现了石油，因而许多人从南部来到这里工作。北极（英文：North Pole；法文：Arctique）。北极地区的气候终年寒冷。北冰洋是一片浩瀚的冰封海洋，周围是众多的岛屿以及北美洲和亚洲北部的沿海地区。北极是指地球自转轴的北端，也就是北纬90°的那一点。北极地区是指北极附近北纬66°34′北极圈以内的地区。冬季，太阳始终在地平线以下，大海完全封冻结冰。夏季，气温上升到冰点以上，北冰洋的边缘地带融化，太阳连续几个星期都挂在天空。北冰洋中有丰富的鱼类和浮游生物，这为夏季在这里筑巢的数百万只海鸟提供了丰富的食物来源。同时，也是海豹、鲸和其他海洋动物的食物。北冰洋周围的大部分地区都比较平坦，没有树木生长。冬季大地封冻，地面上覆盖着厚厚的积雪。夏天积雪融化，表层土解冻，植物生长开花，为驯鹿和麝牛等动物提供了食物。同时，狼和北极熊等食肉动物也依靠捕食其他动物得以存活。北极地区是世界上人口最稀少的地区之一。千百年以来，因纽特人（旧称爱斯基摩人）在这里世代繁衍。在这里发现了石油，因而许多人从南部来到这里工作。北极（英文：North Pole；法文：Arctique）。北极地区的气候终年寒冷。北冰洋是一片浩瀚的冰封海洋，周围是众多的岛屿以及北美洲和亚洲北部的沿海地区。北极是指地球自转轴的北端，也就是北纬90°的那一点。北极地区是指北极附近北纬66°34′北极圈以内的地区。冬季，太阳始终在地平线以下，大海完全封冻结冰。夏季，气温上升到冰点以上，北冰洋的边缘地带融化，太阳连续几个星期都挂在天空。北冰洋中有丰富的鱼类和浮游生物，这为夏季在这里筑巢的数百万只海鸟提供了丰富的食物来源。同时，也是海豹、鲸和其他海洋动物的食物。北冰洋周围的大部分地区都比较平坦，没有树木生长。冬季大地封冻，地面上覆盖着厚厚的积雪。夏天积雪融化，表层土解冻，植物生长开花，为驯鹿和麝牛等动物提供了食物。同时，狼和北极熊等食肉动物也依靠捕食其他动物得以存活。北极地区是世界上人口最稀少的地区之一。千百年以来，因纽特人（旧称爱斯基摩人）在这里世代繁衍。在这里发现了石油，因而许多人从南部来到这里工作。",
            "点击图片进入RecyclerView展示页面\n\n" + "珠穆朗玛峰是喜马拉雅山脉的主峰，为世界最高峰。位于中华人民共和国与尼泊尔边界上，它的北坡在中国青藏高原境内，南坡在尼泊尔境内，而顶峰位于中国境内。藏语中“珠穆”是女神的意思，“朗玛”是第三的意思。因为在珠穆朗玛峰的附近还有四座山峰，珠峰位居第三，所以称为珠穆朗玛峰。\n" +
                    "珠穆朗玛峰是世View展示页面\n\n" + "撒哈拉沙漠约形成于250万年前，是世界第1大荒漠（仅次于南极洲），也是世界最大的沙质荒漠。它位于非洲北部，该地区气候条件非常恶劣，是地球上最不适合生物生存的地方之一。其总面积约容得下整个美国本土。“撒哈拉”国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局界海拔最高的山峰，按2005年国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊中国国家测绘局测量的岩面高为8844.43米，尼泊尔则使用传统的雪盖高8848米（29029英尺），2010年起两国官方互相承认对方的测量数据。除了是海拔最高的山峰之外，它也是距离地心第五远的高峰。它是喜马拉雅山脉中的主峰",
            "点击图片进入RecyclerView展示页面\n\n" + "乞力马扎罗山（Kilimanjaro）位于坦桑尼亚东北部及东非大裂谷以南约160公里，奈洛比以南约225公里，赤道与南纬3°之间，是坦桑尼亚和肯尼亚的分水岭，非洲最高的山脉，也是同时是火山和雪山。该山的主体沿东西向延伸将近80公里，主要由基博、马温西和希拉三个死火山构成，面积756平方公里，其中央火山锥呼鲁峰，海拔5,895米，是非洲最高点。\n" +
                    "乞力马扎罗山素有“非洲屋脊”之称，而许多地理学家称它为“非洲之王”。该山的主体以典型火国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊00米，山顶终年满View展示页面\n\n" + "撒哈拉沙漠约形成于250万年前，是世界第1大荒漠（仅次于南极洲），也是世界最大的沙质荒漠。它位于非洲北部，该地区气候条件非常恶劣，是地球上最不适合生物生存的地方之一。其总面积约容得下整个美国本土。“撒哈拉”国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局布冰雪，但冰川消融现象非常严重。该山四周都是山林，生活着众多哺乳动物，其中一些是濒危物种。\n" +
                    "乞力马扎罗山地区已经于1968年辟为国家公园，生长着热、温、寒三带野生植物和栖息着热、温、寒三带野生动物。联合国教育、科学及文化组织已于1981年将它列入《世界文化与自然遗国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊产保护名录》。",
            "点击图片进入RecyclerView展View展示页面\n\n" + "撒哈拉沙漠约形成于250万年前，是世界第1大荒漠（仅次于南极洲），也是世界最大的沙质荒漠。它位于非洲北部，该地区气候条件非常恶劣，是地球上最不适合生物生存的地方之一。其总面积约容得下整个美国本土。“撒哈拉”国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局示页面\n\n" + "撒哈拉沙漠约形成于250万年前，是世界第1大荒漠（仅次于南极洲），也是世界最大的沙质荒漠。它位于非洲北部，该地区气候条件非常恶劣，是地球上最不适合生物生存的地方之一。其总面积约容得下整个美国本土。“撒哈拉”国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊国家测绘局测量的岩面高为8844.43米，尼泊是阿拉伯语的音译，在阿拉伯语中“撒哈拉”为大沙漠，源自当地游牧民族图阿雷格人的语言，原意即为“大荒漠”。"
    };

    public class Address {
        private String imageUrl;
        private String desContent;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getDesContent() {
            return desContent;
        }

        public void setDesContent(String desContent) {
            this.desContent = desContent;
        }
    }
}
