package org.icegeneral.rxjavaapi.assist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.icegeneral.rxjavaapi.R;

import java.util.concurrent.TimeUnit;

import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class DoActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<Integer> doOnEachObservable;
    private Observable<Integer> doOnNextObservable;

    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);

        tv_top.setText("doOnEach操作符让你可以注册一个回调，它产生的Observable每发射一项数据就会调用它一次。\n" +
                "doOnNext操作符类似于doOnEach(Action1)，但是它的Action不是接受一个Notification参数，而是接受发射的数据项。\n" +
                "doOnSubscribe、doOnCompleted、doOnError、\n" +
                "doOnTerminate 操作符注册一个动作，当它产生的Observable终止之前会被调用，无论是正常还是异常终止。\n" +
                "finallyDo 操作符注册一个动作，当它产生的Observable终止之后会被调用，无论是正常还是异常终止。\n\n" +
                "doOnEachObservable = Observable.just(0, 1, 2).doOnEach(new Subscriber<Integer>() {\n" +
                "   @Override\n" +
                "   public void onCompleted() {\n" +
                "       Toast.makeText(DoActivity.this, \"doOnEach onCompleted()\", Toast.LENGTH_SHORT).show();\n" +
                "   }\n" +
                "\n" +
                "   @Override\n" +
                "   public void onError(Throwable e) {\n" +
                "\n" +
                "   }\n" +
                "\n" +
                "   @Override\n" +
                "   public void onNext(Integer integer) {\n" +
                "       Toast.makeText(DoActivity.this, integer + \"\", Toast.LENGTH_SHORT).show();\n" +
                "   }\n" +
                "});\n" +
                "\n" +
                "doOnNextObservable = Observable.just(3, 4, 5).doOnNext(new Action1<Integer>() {\n" +
                "   @Override\n" +
                "   public void call(Integer integer) {\n" +
                "       Toast.makeText(DoActivity.this, integer + \"\", Toast.LENGTH_SHORT).show();\n" +
                "   }\n" +
                "});");

        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_subscribe = (Button) findViewById(R.id.btn_subscribe);
        btn_subscribe.setText("doOnEach");

        Button btn_subscribe2 = (Button) findViewById(R.id.btn_subscribe2);
        btn_subscribe2.setText("doOnNext");
        btn_subscribe2.setVisibility(View.VISIBLE);

        doOnEachObservable = Observable.just(0, 1, 2).doOnEach(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Toast.makeText(DoActivity.this, "doOnEach onCompleted()", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Toast.makeText(DoActivity.this, integer + "", Toast.LENGTH_SHORT).show();
            }
        });

        doOnNextObservable = Observable.just(3, 4, 5).doOnNext(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Toast.makeText(DoActivity.this, integer + "", Toast.LENGTH_SHORT).show();
            }
        });

        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                doOnEachObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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
                doOnNextObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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
