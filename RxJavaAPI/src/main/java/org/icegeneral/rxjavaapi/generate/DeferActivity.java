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
import rx.functions.Func0;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class DeferActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<String> justObservable;
    private Observable<String> deferObservable;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);
        tv_top.setText("直到有观察者订阅时才创建Observable，并且为每个观察者创建一个新的Observable。对比看下，使用just创建的并没有获取到最新的对象man\n" +
                "\n" +
                "final StringBuilder man = new StringBuilder(\"小明\");\n" +
                "justObservable = Observable.just(man.toString());\n" +
                "\n" +
                "deferObservable = Observable.defer(new Func0<Observable<String>>() {\n" +
                "   @Override\n" +
                "   public Observable<String> call() {\n" +
                "       return Observable.just(man.toString());\n" +
                "   }\n" +
                "});\n" +
                "man.setLength(0);\n" +
                "man.append(\"小红 女 5岁\");");
        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_just = (Button) findViewById(R.id.btn_subscribe);
        btn_just.setText("just");
        Button btn_defer = (Button) findViewById(R.id.btn_subscribe2);
        btn_defer.setVisibility(View.VISIBLE);
        btn_defer.setText("defer");

        final StringBuilder man = new StringBuilder("小明");
        justObservable = Observable.just(man.toString());

        deferObservable = Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable.just(man.toString());
            }
        });

        btn_just.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                justObservable.subscribe(new Subscriber<String>() {
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
                    public void onNext(String o) {
                        sb.append("onNext() ");
                        sb.append(o);
                        sb.append("\n");
                        tv_bottom.setText(sb);
                    }
                });
            }
        });

        btn_defer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                deferObservable.subscribe(new Subscriber<String>() {
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
                    public void onNext(String o) {
                        sb.append("onNext() ");
                        sb.append(o);
                        sb.append("\n");
                        tv_bottom.setText(sb);
                    }
                });
            }
        });

        man.setLength(0);
        man.append("小红 女 5岁");

    }
}
