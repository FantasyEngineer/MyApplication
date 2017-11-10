package org.icegeneral.rxjavaapi.generate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.icegeneral.rxjavaapi.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class RepeatActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<Integer> observable;

    private Observable<Integer> whenObservable;

    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);
        tv_top.setText("创建一个发射特定数据重复多次的Observable，默认在trampoline调度器上执行。repeatWhen，完成的时候触发是否重试\n\n" +
                "observable = Observable.just(0, 1, 2).repeat(3);\n" +
                "\n" +
                "whenObservable = Observable.just(0, 1, 2).repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {\n" +
                "    @Override\n" +
                "    public Observable<?> call(Observable<? extends Void> observable) {\n" +
                "        observable = observable.delay(2, TimeUnit.SECONDS); //2s后发射\n" +
                "        return observable;\n" +
                "    }\n" +
                "});");
        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_repeat = (Button) findViewById(R.id.btn_subscribe);
        btn_repeat.setText("repeat");
        Button btn_repeat_when = (Button) findViewById(R.id.btn_subscribe2);
        btn_repeat_when.setText("repeatWhen");
        btn_repeat_when.setVisibility(View.VISIBLE);

        observable = Observable.just(0, 1, 2).repeat(3);

        whenObservable = Observable.just(0, 1, 2).repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Void> observable) {
                observable = observable.delay(2, TimeUnit.SECONDS); //2s后发射
                return observable;
            }
        });

        btn_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                observable.subscribe(new Subscriber<Integer>() {
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

        btn_repeat_when.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                whenObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        sb.append("onCompleted()");
                        sb.append("\n");
                        tv_bottom.setText(sb);
                    }

                    @Override
                    public void onError(Throwable e) {
                        sb.append("onError()");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
