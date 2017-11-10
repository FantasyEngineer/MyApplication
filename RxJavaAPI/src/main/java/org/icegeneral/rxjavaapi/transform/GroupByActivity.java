package org.icegeneral.rxjavaapi.transform;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.icegeneral.rxjavaapi.R;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.observables.GroupedObservable;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class GroupByActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<GroupedObservable<Boolean, Integer>> observable;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);
        tv_top.setText("将一个Observable<T>进行分组加工成为一个Observable<GroupedObservable<K, T>>，观察者onNext()收到的是GroupedObservable<K, T>\n\n" +
                "Observable<GroupedObservable<Boolean, Integer>> observable = Observable.just(0, 1, 2, 3, 4, 5).groupBy(new Func1<Integer, Boolean>() {\n" +
                "   @Override\n" +
                "   public Boolean call(Integer integer) {\n" +
                "       return integer % 2 == 0;//按奇数偶数分组\n" +
                "   }\n" +
                "}, new Func1<Integer, Integer>() {\n" +
                "   @Override\n" +
                "   public Integer call(Integer integer) {\n" +
                "       return integer % 2 == 0 ? integer * 2 : integer;//偶数就乘2，第二个Func1可以不写，就是返回原来的值\n" +
                "   }\n" +
                "});\n\n" +
                "观察者\n" +
                "public void onNext(GroupedObservable<Boolean, Integer> groupedObservable) {\n" +
                "   if (groupedObservable.getKey()) {//例子只处理偶数\n" +
                "       ...\n" +
                "   }\n" +
                "}");
        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_subscribe = (Button) findViewById(R.id.btn_subscribe);

        observable = Observable.just(0, 1, 2, 3, 4, 5).groupBy(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer % 2 == 0;//按奇数偶数分组
            }
        }, new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer % 2 == 0 ? integer * 2 : integer;//偶数就乘2，第二个Func1可以不写，就是返回原来的值
            }
        });

        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                observable.subscribe(new Subscriber<GroupedObservable<Boolean, Integer>>() {
                    @Override
                    public void onCompleted() {
                        sb.append("onCompleted()");
                        sb.append("\n");
                        tv_bottom.setText(sb);
                    }

                    @Override
                    public void onError(Throwable e) {
                        sb.append("onError() ");
                        sb.append(e.getMessage());
                        sb.append("\n");
                        tv_bottom.setText(sb);
                    }

                    @Override
                    public void onNext(GroupedObservable<Boolean, Integer> groupedObservable) {
                        sb.append("onNext() ");
                        sb.append(groupedObservable);
                        sb.append("\n");
                        tv_bottom.setText(sb);
                        if (groupedObservable.getKey()) {//例子只处理偶数
                            groupedObservable.subscribe(new Subscriber<Integer>() {
                                @Override
                                public void onCompleted() {
                                    sb.append("---sub onCompleted()");
                                    sb.append("\n");
                                    tv_bottom.setText(sb);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    sb.append("---sub onError() ");
                                    sb.append(e.getMessage());
                                    sb.append("\n");
                                    tv_bottom.setText(sb);
                                }

                                @Override
                                public void onNext(Integer integer) {
                                    sb.append("---sub onNext() ");
                                    sb.append(integer);
                                    sb.append("\n");
                                    tv_bottom.setText(sb);
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
