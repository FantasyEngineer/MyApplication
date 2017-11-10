package org.icegeneral.rxjavaapi.generate;

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
public class EmptyNeverThrowActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<Integer> emptyObservable;

    private Observable<Integer> neverObservable;

    private Observable<Integer> throwObservable;

    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);
        tv_top.setText("Empty\n" +
                "\n" +
                "创建一个不发射任何数据但是正常终止的Observable\n" +
                "\n" +
                "Never\n" +
                "\n" +
                "创建一个不发射数据也不终止的Observable\n" +
                "\n" +
                "Throw\n" +
                "\n" +
                "创建一个不发射数据以一个错误终止的Observable\n" +
                "\n" +
                "这三个操作符生成的Observable行为非常特殊和受限。测试的时候很有用，有时候也用于结合其它的Observables，或者作为其它需要Observable的操作符的参数。\n\n" +
                "emptyObservable = Observable.empty();\n" +
                "\n" +
                "neverObservable = Observable.never();\n" +
                "\n" +
                "throwObservable = Observable.error(new Exception(\"RxJavaAPI test error\"));");
        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_never = (Button) findViewById(R.id.btn_subscribe);
        btn_never.setText("never");
        Button btn_empty = (Button) findViewById(R.id.btn_subscribe2);
        btn_empty.setText("empty");
        btn_empty.setVisibility(View.VISIBLE);
        Button btn_throw = (Button) findViewById(R.id.btn_subscribe3);
        btn_throw.setText("throw");
        btn_throw.setVisibility(View.VISIBLE);

        emptyObservable = Observable.empty();

        neverObservable = Observable.never();

        throwObservable = Observable.error(new Exception("RxJavaAPI test error"));

        btn_never.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                neverObservable.subscribe(new Subscriber<Integer>() {
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

        btn_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                emptyObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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

        btn_throw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                throwObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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
