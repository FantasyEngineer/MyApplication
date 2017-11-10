package org.icegeneral.rxjavaapi.filter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.icegeneral.rxjavaapi.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class FirstActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<Integer> firstObservable;
    private Observable<Integer> singleObservable;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);

        tv_top.setText("如果你只对Observable发射的第一项数据，或者满足某个条件的第一项数据感兴趣，你可以使用First操作符。还有操作符firstOrDefault、takeFirst、first(Func1)、firstOrDefault(Func1)、takeFirst(Func1)，Func1的方法就是只返回第一个满足条件的数据项。\n" +
                "takeFirst与first类似，除了这一点：如果原始Observable没有发射任何满足条件的数据，first会抛出一个NoSuchElementException，takeFist会返回一个空的Observable（不调用onNext()但是会调用onCompleted）。\n" +
                "在我看来，first是不是也可以用filter+take(1)或者elementAt(0)来代替\n" +
                "single操作符也与first类似，但是原始Observable不止发射1次，会以错误通知终止。singleOrDefault也是如此。\n" +
                "single(Func1)和singleOrDefault(T,Func1)发射满足条件的单个值，如果有多个数据满足条件，会以错误通知终止。\n\n" +
                "firstObservable = Observable.range(0, 4).first(new Func1<Integer, Boolean>() {\n" +
                "   @Override\n" +
                "   public Boolean call(Integer integer) {\n" +
                "       return integer == 1;\n" +
                "   }\n" +
                "});\n" +
                "singleObservable = Observable.range(0, 4).single();");

        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_first = (Button) findViewById(R.id.btn_subscribe);
        btn_first.setText("first");
        Button btn_single = (Button) findViewById(R.id.btn_subscribe2);
        btn_single.setText("single");
        btn_single.setVisibility(View.VISIBLE);

        firstObservable = Observable.range(0, 4).first(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer == 1;
            }
        });

        singleObservable = Observable.range(0, 4).single();

        btn_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                firstObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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
                    public void onNext(Integer o) {
                        sb.append("onNext() ");
                        sb.append(o);
                        sb.append("\n");
                        tv_bottom.setText(sb);
                    }
                });
            }
        });

        btn_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                singleObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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
                    public void onNext(Integer o) {
                        sb.append("onNext() ");
                        sb.append(o);
                        sb.append("\n");
                        tv_bottom.setText(sb);
                    }
                });
            }
        });
    }
}
