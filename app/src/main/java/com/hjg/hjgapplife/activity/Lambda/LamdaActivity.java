package com.hjg.hjgapplife.activity.Lambda;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hjg.baseapp.util.ToastUtil;
import com.hjg.baseapp.widget.dialog.BottomDialog;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipActivity;
import com.hjg.hjgapplife.entity.TestBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

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

    BottomDialog bottomDialog;
    StringBuilder stringBuilder;

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
            bottomDialog.showDialog(" btn1.setOnClickListener(view -> {点击事件触发效果} ");
        });
        //底部弹出的dialog
        bottomDialog = new BottomDialog(activity);

        testBeenList = new ArrayList<>();
        testBeenList.add(new TestBean("2345-1-45", "职员1", 1000, 12));
        testBeenList.add(new TestBean("2007-4-07", "职员2", 2000, 56));
        testBeenList.add(new TestBean("2007-4-07", "职员3", 3000, 20));
        testBeenList.add(new TestBean("2007-4-07", "职员4", 4000, 30));
    }


    String[] atp = {"Nadal", "Djokovic", "daStanislas Wawrinka", "mdaDavid Ferrer", "oger Federer", "mdaAndy Murray", "Lamas Berdych", "aJuan Martin Del Potro"};
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
                bottomDialog.showDialog("   players.forEach((player) -> Log.d(\"LamdaActivity\", player + \"; \"));");
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
                bottomDialog.showDialog("  new Thread(() -> runOnUiThread(() -> ToastUtil.show(activity, \"Runnable中弹出的toast\"))).start();");

                break;


            case R.id.btn_sort://排序操作
                stringBuilder = new StringBuilder();
                stringBuilder.append("排序前数据：\n\n");
                for (String s : atp) {
                    stringBuilder.append("\t" + s + "\n");
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

                stringBuilder.append("\n排序后数据：\n\n");
                //也可以采用如下形式:
                Arrays.sort(atp, (String s1, String s2) -> (s1.compareTo(s2)));

                for (String s : atp) {
                    stringBuilder.append("\t").append(s).append("\n");
                }
                bottomDialog.showDialog(stringBuilder.toString());
                break;


            case R.id.btn_modify:
                stringBuilder = new StringBuilder();
                stringBuilder.append("修改前数据：\n\n");
                testBeenList.forEach(testBean -> stringBuilder.append(testBean.getAge()).append("岁；").append(testBean.getMessageContent()).append(";  工资：").append(testBean.getPrice()).append("\n"));
                //全体员工工资涨500  合着写
//                testBeenList.forEach(testBean -> {
//                    testBean.setPrice(testBean.getPrice() + 500);
//                });

                stringBuilder.append("\n全体员工工资涨500之后的数据：\n\n");
                //拆开写
                Consumer<TestBean> testBeanConsumer = testBean -> testBean.setPrice(testBean.getPrice() + 500);
                testBeenList.forEach(testBeanConsumer);

                testBeenList.forEach(testBean -> stringBuilder.append(testBean.getAge()).append("岁；").append(testBean.getMessageContent()).append(";  工资：").append(testBean.getPrice()).append("\n"));
                bottomDialog.showDialog(stringBuilder.toString());
                break;

            case R.id.btn_filter:
                stringBuilder = new StringBuilder();
                stringBuilder.append("筛选前数据：\n\n");
                testBeenList.forEach(testBean -> stringBuilder.append(testBean.getAge()).append("岁；").append(testBean.getMessageContent()).append(";  工资：").append(testBean.getPrice()).append("\n"));
                //筛选工资大于2000的人
//                testBeenList.stream()
//                        .filter(bean -> bean.getPrice() > 2000)
//                        .forEach(testBean -> {
//                            Log.d("LamdaActivity", "筛选后" + testBean.getPrice() + "");
//                        });
                //多重筛选，筛选出工资大于2000，且岁数小于35岁的。
                Predicate<TestBean> pricePredicate = testBean -> testBean.getPrice() > 2000;
                Predicate<TestBean> agePredicate = testBean -> testBean.getAge() < 35;

                stringBuilder.append("\n筛选出工资大于2000，且岁数小于35岁的数据：\n\n");
                testBeenList.stream()
                        .filter(pricePredicate)
                        .filter(agePredicate)
                        .forEach(testBean -> {
                            stringBuilder.append(testBean.getMessageContent()).append("\n");
                        });
                bottomDialog.showDialog(stringBuilder.toString());

                break;
            case R.id.btn_limit:
                stringBuilder = new StringBuilder();
                stringBuilder.append("未限制前长度是：").append(testBeenList.size()).append("\n\n");
                stringBuilder.append("限制前长度后数据如下：");
                testBeenList.stream()
                        .limit(2)
                        .forEach(testBean ->
                                stringBuilder.append(testBean.getMessageContent()).append("\n"));

                bottomDialog.showDialog(stringBuilder.toString());
                break;
        }
    }


}
