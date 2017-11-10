package org.icegeneral.rxjavaapi.filter;

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
import rx.schedulers.Schedulers;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class ThrottleActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<Integer> throttleFirstObservable;
    private Observable<Integer> throttleLastObservable;
    private Observable<Integer> throttleWithTimeoutObservable;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);

        tv_top.setText("ThrottleFirst发射在那段时间内的第一项数据，ThrottleLast发射那段时间内的最后一项数据，ThrottleLast也可以写成Sample\n" +
                "throttleFirst和throttleLast取得数据后，从取得数据的那项开始计时。throttleWithTimeout是弱化版的debounce，debounce操作符也可以使用时间来进行过滤，这时它跟throttleWithTimeOut使用起来是一样，但是deounce操作符还可以根据一个函数来进行限流。\n\n" +
                "Observable observable = Observable.create(new Observable.OnSubscribe<Integer>() {\n" +
                "   @Override\n" +
                "   public void call(Subscriber<? super Integer> subscriber) {\n" +
                "       int[] nums = {0, 1, 2, 3, 4};\n" +
                "       long[] sleeps = {200, 200, 200, 200, 0};\n" +
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
                "}).subscribeOn(Schedulers.io());\n" +
                "throttleFirstObservable = observable.throttleFirst(300, TimeUnit.MILLISECONDS);//0ms~300ms(0,1)  0~600ms(2,3) 600ms~800ms(4)\n" +
                "throttleLastObservable = observable.throttleLast(300, TimeUnit.MILLISECONDS);//0ms~200ms(0,1)  200ms~500ms(2) 500ms~800ms(3,4)\n" +
                "throttleWithTimeoutObservable = observable.throttleWithTimeout(300, TimeUnit.MILLISECONDS);");

        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btnThrottleFirst = (Button) findViewById(R.id.btn_subscribe);
        btnThrottleFirst.setText("throttleFirst");

        Button btnThrottleLast = (Button) findViewById(R.id.btn_subscribe2);
        btnThrottleLast.setText("throttleLast");
        btnThrottleLast.setVisibility(View.VISIBLE);

        Button btnThrottleWithTimeout = (Button) findViewById(R.id.btn_subscribe3);
        btnThrottleWithTimeout.setText("throttleWithTimeout");
        btnThrottleWithTimeout.setVisibility(View.VISIBLE);

        Observable observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                int[] nums = {0, 1, 2, 3, 4};
                long[] sleeps = {200, 200, 200, 200, 0};
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
        }).subscribeOn(Schedulers.io());
        throttleFirstObservable = observable.throttleFirst(300, TimeUnit.MILLISECONDS);//0ms~300ms(0,1)  0~600ms(2,3) 600ms~800ms(4)
        throttleLastObservable = observable.throttleLast(300, TimeUnit.MILLISECONDS);//0ms~300ms(0,1)  200ms~500ms(2) 500ms~800ms(3,4)
        throttleWithTimeoutObservable = observable.throttleWithTimeout(300, TimeUnit.MILLISECONDS);

        btnThrottleFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                throttleFirstObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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

        btnThrottleLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                throttleLastObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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

        btnThrottleWithTimeout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                throttleWithTimeoutObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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
