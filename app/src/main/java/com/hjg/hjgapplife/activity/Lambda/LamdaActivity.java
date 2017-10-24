package com.hjg.hjgapplife.activity.Lambda;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hjg.baseapp.util.ToastUtil;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;
import com.hjg.hjgapplife.entity.TestBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import butterknife.BindView;
import butterknife.OnClick;

public class LamdaActivity extends BaseOthreRenderSwipActivity {


    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.btn_foreach)
    Button btnForeach;
    @BindView(R.id.btn_runnable)
    Button btnRunnable;
    @BindView(R.id.btn_sort)
    Button btnSort;
    @BindView(R.id.btn_modify)
    Button btn_stream;
    @BindView(R.id.btn_filter)
    Button btnFilter;
    @BindView(R.id.btn_limit)
    Button btnLimit;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_lamda;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "Lambda表达式的使用范例");
    }

    @Override
    protected void initData() {
        //匿名内部类使用Lambda
        btn1.setOnClickListener(view -> {
            Toast.makeText(activity, "使用lambda表达式", Toast.LENGTH_SHORT).show();
        });

        testBeenList = new ArrayList<>();
        testBeenList.add(new TestBean("2345-1-45", "消息1", 1000));
        testBeenList.add(new TestBean("2007-4-07", "消息2", 2000));
        testBeenList.add(new TestBean("2007-4-07", "消息3", 3000));
        testBeenList.add(new TestBean("2007-4-07", "消息4", 4000));
    }


    String[] atp = {"Nadal", "Djokovic",
            "daStanislas Wawrinka", "mdaDavid Ferrer", "oger Federer",
            "mdaAndy Murray", "Lamas Berdych",
            "aJuan Martin Del Potro"};
    List<String> players = new ArrayList<>(Arrays.asList(atp));
    ArrayList<TestBean> testBeenList;

    @OnClick({R.id.btn_foreach, R.id.btn_runnable, R.id.btn_sort, R.id.btn_modify, R.id.btn_filter, R.id.btn_limit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_foreach://打印循环体
                Log.d("LamdaActivity", "打印循环体");
                //以前的循环方式
//                for (String str : players) {System.out.print(str);}
                //使用lambda表达式
                players.forEach((player) -> Log.d("LamdaActivity", player + "; "));
                //                players.forEach(System.out::print);//双冒号符

                //循环打印对象
                testBeenList.forEach(testBean -> Log.d("LamdaActivity", "消息内容" + testBean.getMessageContent()));
                break;


            case R.id.btn_runnable://实现接口内容
                //老方法实现
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                ToastUtil.show(activity, "Runnable中弹出的toast");
//                            }
//                        });
//                    }
//                }).start();
                new Thread(() -> runOnUiThread(() -> ToastUtil.show(activity, "Runnable中弹出的toast"))).start();
                break;


            case R.id.btn_sort://排序操作
                for (String s : atp) {
                    Log.d("LamdaActivity", "排序前" + s);
                }


//                // 使用匿名内部类根据 name 排序 数组atp
//                Arrays.sort(atp, new Comparator<String>() {
//                    @Override
//                    public int compare(String s1, String s2) {
//                        return (s1.compareTo(s2));
//                    }
//                });

//                // 使用 lambda expression 排序 atp
//                Comparator<String> sortByName = (String s1, String s2) -> (s1.compareTo(s2));
//                Arrays.sort(atp, sortByName);

                //也可以采用如下形式:
                Arrays.sort(atp, (String s1, String s2) -> (s1.compareTo(s2)));

                for (String s : atp) {
                    Log.d("LamdaActivity", "排序后" + s);
                }
                break;


            case R.id.btn_modify:
                //全体员工工资涨500  合着写
//                testBeenList.forEach(testBean -> {
//                    testBean.setPrice(testBean.getPrice() + 500);
//                });
                //拆开写
                Consumer<TestBean> testBeanConsumer = testBean -> testBean.setPrice(testBean.getPrice() + 500);
                testBeenList.forEach(testBeanConsumer);

                //输出修改后的价格
                testBeenList.forEach(a -> Log.d("LamdaActivity", a.getPrice() + ""));
                break;


            case R.id.btn_filter:


                break;
            case R.id.btn_limit:
                break;
        }
    }


}
