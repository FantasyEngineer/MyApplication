package org.icegeneral.rxjavaapi.connect;

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
import rx.observables.ConnectableObservable;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class ConnectActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private ConnectableObservable<Integer> observable;

    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);

        tv_top.setText("可连接的Observable (connectable Observable)与普通的Observable差不多，不过它并不会在被订阅时开始发射数据，而是直到使用了Connect操作符时才会开始。用这个方法，你可以等待所有的观察者都订阅了Observable之后再开始发射数据。\n\n" +
                "ConnectableObservable<Integer> observable = Observable.just(0, 1, 2).publish();\n\n" +
                "btn_subscribe.setOnClickListener(new View.OnClickListener() {\n" +
                "   @Override\n" +
                "   public void onClick(View v) {\n" +
                "       ...\n" +
                "   }\n" +
                "});\n" +
                "btn_subscribe2.setOnClickListener(new View.OnClickListener() {\n" +
                "   @Override\n" +
                "   public void onClick(View v) {\n" +
                "       ...\n" +
                "       observable.connect();\n" +
                "   }\n" +
                "});");


        tv_bottom = (TextView) findViewById(R.id.tv_bottom);

        Button btn_subscribe = (Button) findViewById(R.id.btn_subscribe);
        Button btn_subscribe2 = (Button) findViewById(R.id.btn_subscribe2);
        btn_subscribe2.setVisibility(View.VISIBLE);


        observable = Observable.just(0, 1, 2).publish();

        btn_subscribe.setOnClickListener(new View.OnClickListener() {
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

        btn_subscribe2.setOnClickListener(new View.OnClickListener() {
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
                observable.connect();
            }
        });


    }
}
