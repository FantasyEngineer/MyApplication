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

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class DistinctActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<Integer> observable;
    private Observable<Integer> distinctUntilChangedObservable;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);

        tv_top.setText("distinct：只允许还没有发射过的数据项通过。distinctUntilChanged：一个数据和它的前一个是不同的就可以发射。distinct和distinctUntilChanged都有一个重载方法distinct(Func1)和distinctUntilChanged(Func1)，。这个Func1函数根据原始Observable发射的数据项产生一个Key，然后比较这些Key而不是数据本身，来判定两个数据是否是不同的。\n\n" +
                "observable = Observable.just(1, 2, 3, 1, 1, 2).distinct();\n" +
                "distinctUntilChangedObservable = Observable.just(1, 2, 3, 1, 1, 2).distinctUntilChanged();");

        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_distinct = (Button) findViewById(R.id.btn_subscribe);
        btn_distinct.setText("distinct");

        Button btn_distinctUntilChanged = (Button) findViewById(R.id.btn_subscribe2);
        btn_distinctUntilChanged.setText("distinctUntilChanged");
        btn_distinctUntilChanged.setVisibility(View.VISIBLE);

        observable = Observable.just(1, 2, 3, 1, 1, 2).distinct();
        distinctUntilChangedObservable = Observable.just(1, 2, 3, 1, 1, 2).distinctUntilChanged();


        btn_distinct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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
        btn_distinctUntilChanged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                distinctUntilChangedObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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
