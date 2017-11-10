package org.icegeneral.rxjavaapi.filter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.icegeneral.rxjavaapi.R;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class DebounceActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<Integer> observable;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);
        tv_top.setText("过滤掉发射速率过快的数据项，只要2次发射的间隔小于debounce，那么前一个就会被抛弃。\n" +
                "\n\n" +
                "Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {\n" +
                "   @Override\n" +
                "   public void call(Subscriber<? super Integer> subscriber) {\n" +
                "       int[] nums = {0, 1, 2, 3, 4，5};\n" +
                "       long[] sleeps = {100, 400, 100, 100, 200, 0};//发射0，经过100ms，发射1，因为100ms<debounce的300ms，所以0被抛弃\n" +
                "       for (int i = 0; i < nums.length; i++) {\n" +
                "           subscriber.onNext(nums[i]);\n" +
                "           try {\n" +
                "               Thread.sleep(sleeps[i]);\n" +
                "           } catch (InterruptedException e) {\n" +
                "                        e.printStackTrace();\n" +
                "           }\n" +
                "       }\n" +
                "       subscriber.onCompleted();\n" +
                "   }\n" +
                "}).subscribeOn(Schedulers.io()).debounce(300, TimeUnit.MILLISECONDS);");
        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_subscribe = (Button) findViewById(R.id.btn_subscribe);

        observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                int[] nums = {0, 1, 2, 3, 4, 5};
                long[] sleeps = {100, 400, 100, 100, 200, 0};//发射0，经过100ms，发射1，因为100ms<debounce的300ms，所以0被抛弃
                for (int i = 0; i < nums.length; i++) {
                    subscriber.onNext(nums[i]);
                    try {
                        Thread.sleep(sleeps[i]);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).debounce(300, TimeUnit.MILLISECONDS);

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
    }
}
