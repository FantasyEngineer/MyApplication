package com.hjg.hjgapplife.activity.seclectCity;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hjg.baseapp.adapter.listViewAdapter.mBaseAdapter;
import com.hjg.baseapp.adapter.listViewAdapter.mViewHolder;
import com.hjg.baseapp.util.AbAppUtil;
import com.hjg.baseapp.util.pinyin.CharacterParser;
import com.hjg.baseapp.widget.ClearEditText;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.base.BaseActivity;
import com.hjg.hjgapplife.entity.EventBusBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import xiaofei.library.hermeseventbus.HermesEventBus;

public class SelectCityActivity extends BaseActivity implements com.hjg.baseapp.widget.SideBar.OnTouchingLetterChangedListener {

    ArrayList<String> CityList = new ArrayList<String>(Arrays.asList("北京", "安徽", "福建", "甘肃", "广东", "广西", "贵州", "海南", "河北", "河南", "黑龙江", "湖北", "湖南", "吉林", "江苏", "江西", "辽宁", "内蒙古", "宁夏", "青海", "山东", "山西", "陕西", "上海", "四川", "天津", "西藏", "新疆", "云南", "浙江", "香港", "澳门", "台湾", "安庆", "蚌埠", "巢湖", "池州", "滁州", "阜阳", "淮北", "淮南", "黄山", "六安", "马鞍山", "宿州", "铜陵", "芜湖", "宣城", "亳州", "北京", "福州", "龙岩", "南平", "宁德", "莆田", "泉州", "三明", "厦门", "漳州", "兰州", "白银", "定西", "甘南", "嘉峪关", "金昌", "酒泉", "临夏", "陇南", "平凉", "庆阳", "天水", "武威", "张掖", "广州", "深圳", "潮州", "东莞", "佛山", "河源", "惠州", "江门", "揭阳", "茂名", "梅州", "清远", "汕头", "汕尾", "韶关", "阳江", "云浮", "湛江", "肇庆", "中山", "珠海", "南宁", "桂林", "百色", "北海", "崇左", "防城港", "贵港", "河池", "贺州", "来宾", "柳州", "钦州", "梧州", "玉林", "贵阳", "安顺", "毕节", "六盘水", "黔东南", "黔南", "黔西南", "铜仁", "遵义", "海口", "三亚", "白沙", "保亭", "昌江", "澄迈县", "定安县", "东方", "乐东", "临高县", "陵水", "琼海", "琼中", "屯昌县", "万宁", "文昌", "五指山", "儋州", "石家庄", "保定", "沧州", "承德", "邯郸", "衡水", "廊坊", "秦皇岛", "唐山", "邢台", "张家口", "郑州", "洛阳", "开封", "安阳", "鹤壁", "济源", "焦作", "南阳", "平顶山", "三门峡", "商丘", "新乡", "信阳", "许昌", "周口", "驻马店", "漯河", "濮阳", "哈尔滨", "大庆", "大兴安岭", "鹤岗", "黑河", "鸡西", "佳木斯", "牡丹江", "七台河", "齐齐哈尔", "双鸭山", "绥化", "伊春", "武汉", "仙桃", "鄂州", "黄冈", "黄石", "荆门", "荆州", "潜江", "神农架林区", "十堰", "随州", "天门", "咸宁", "襄樊", "孝感", "宜昌", "恩施", "长沙", "张家界", "常德", "郴州", "衡阳", "怀化", "娄底", "邵阳", "湘潭", "湘西", "益阳", "永州", "岳阳", "株洲", "长春", "吉林", "白城", "白山", "辽源", "四平", "松原", "通化", "延边", "南京", "苏州", "无锡", "常州", "淮安", "连云港", "南通", "宿迁", "泰州", "徐州", "盐城", "扬州", "镇江", "南昌", "抚州", "赣州", "吉安", "景德镇", "九江", "萍乡", "上饶", "新余", "宜春", "鹰潭", "沈阳", "大连", "鞍山", "本溪", "朝阳", "丹东", "抚顺", "阜新", "葫芦岛", "锦州", "辽阳", "盘锦", "铁岭", "营口", "呼和浩特", "阿拉善盟", "巴彦淖尔盟", "包头", "赤峰", "鄂尔多斯", "呼伦贝尔", "通辽", "乌海", "乌兰察布市", "锡林郭勒盟", "兴安盟", "银川", "固原", "石嘴山", "吴忠", "中卫", "西宁", "果洛", "海北", "海东", "海南", "海西", "黄南", "玉树", "济南", "青岛", "滨州", "德州", "东营", "菏泽", "济宁", "莱芜", "聊城", "临沂", "日照", "泰安", "威海", "潍坊", "烟台", "枣庄", "淄博", "太原", "长治", "大同", "晋城", "晋中", "临汾", "吕梁", "朔州", "忻州", "阳泉", "运城", "西安", "安康", "宝鸡", "汉中", "商洛", "铜川", "渭南", "咸阳", "延安", "榆林", "上海", "成都", "绵阳", "阿坝", "巴中", "达州", "德阳", "甘孜", "广安", "广元", "乐山", "凉山", "眉山", "南充", "内江", "攀枝花", "遂宁", "雅安", "宜宾", "资阳", "自贡", "泸州", "天津", "拉萨", "阿里", "昌都", "林芝", "那曲", "日喀则", "山南", "乌鲁木齐", "阿克苏", "阿拉尔", "巴音郭楞", "博尔塔拉", "昌吉", "哈密", "和田", "喀什", "克拉玛依", "克孜勒苏", "石河子", "图木舒克", "吐鲁番", "五家渠", "伊犁", "昆明", "怒江", "普洱", "丽江", "保山", "楚雄", "大理", "德宏", "迪庆", "红河", "临沧", "曲靖", "文山", "西双版纳", "玉溪", "昭通", "杭州", "湖州", "嘉兴", "金华", "丽水", "宁波", "绍兴", "台州", "温州", "舟山", "衢州", "重庆", "香港", "澳门", "台湾", "合肥"));
    ArrayList remenCity = new ArrayList<String>(Arrays.asList("北京", "安徽", "福建", "甘肃", "广东", "广西"));
    @BindView(R.id.filter_edit)
    ClearEditText filterEdit;
    @BindView(R.id.lv_country)
    ListView lvCountry;
    @BindView(R.id.tv_show_press)
    TextView tvShowPress;
    @BindView(R.id.SideBar)
    com.hjg.baseapp.widget.SideBar SideBar;
    private GridView mGridView;
    private PinyinComparator comparator;
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;
    private SortAdapter adapter;

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "地址选择");
        topBarManage.setLeftBtnBack(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_select_city;
    }

    @Override
    protected void initView() {
        View view = View.inflate(this, R.layout.head_city_list, null);
        mGridView = (GridView) view.findViewById(R.id.id_gv_remen);
        MyGridViewAdapter myGridViewAdapter = new MyGridViewAdapter(activity, remenCity);
        mGridView.setAdapter(myGridViewAdapter);

        characterParser = CharacterParser.getInstance();
        comparator = new PinyinComparator();

        SourceDateList = filledData(CityList);

        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, comparator);
        adapter = new SortAdapter(this, SourceDateList);
        lvCountry.setAdapter(adapter);
        lvCountry.addHeaderView(view);

        //地址被点击之后，发送到首页，用于头部显示
        lvCountry.setOnItemClickListener(((adapterView, view1, positon, l) -> {
            HermesEventBus.getDefault().post(new EventBusBean(EventBusBean.CitySelect, SourceDateList.get(positon - 1).getName()));
            activity.finish();
        }));
        mGridView.setOnItemClickListener((adapterView, view1, i, l) -> {
                    HermesEventBus.getDefault().post(new EventBusBean(EventBusBean.CitySelect, remenCity.get(i) + ""));
                    activity.finish();
                }
        );
        //当listview滑动的时候，关闭键盘
        lvCountry.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // 触摸移动时的操作
                        AbAppUtil.closeSoftInput(activity);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    public void initAction() {
        super.initAction();
        SideBar.setOnTouchingLetterChangedListener(this);
        SideBar.setTextView(tvShowPress);
        filterEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @Override
    public void onTouchingLetterChanged(String s) {
        //该字母首次出现的位置
        if (s.equals("热门")) {
            lvCountry.setSelection(0);
        } else {
            int position = adapter.getPositionForSection(s.charAt(0));
            if (position != -1) {
                //因为add了一个head所以要加一
                lvCountry.setSelection(position + 1);
            }
        }
    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(List<String> date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date.get(i));
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date.get(i));
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }


    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, comparator);
        adapter.updateListView(filterDateList);
    }

    private class MyGridViewAdapter extends mBaseAdapter<String> {

        public MyGridViewAdapter(Context context, List<String> mReMenCitys) {
            super(context, mReMenCitys);
        }

        @Override
        public int[] getFindViewByIDs(int position) {
            return new int[]{R.id.id_tv_cityname};
        }

        @Override
        public int getLayout(int position) {
            return R.layout.item_remen_city;
        }

        @Override
        public void renderData(View convertView, int position, mViewHolder vh) {
            TextView id_tv_cityname = vh.getView(TextView.class, R.id.id_tv_cityname);
            id_tv_cityname.setText(getList().get(position) + "");
        }
    }
}
