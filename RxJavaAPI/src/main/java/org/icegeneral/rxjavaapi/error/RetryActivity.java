package org.icegeneral.rxjavaapi.error;

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
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class RetryActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<Integer> retryObservable;
    private Observable<Integer> retryWhenObservable;

    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);

        tv_top.setText("Retry操作符不会将原始Observable的onError通知传递给观察者，它会订阅这个Observable，再给它一次机会无错误地完成它的数据序列。Retry总是传递onNext通知给观察者，由于重新订阅，可能会造成数据项重复。无论收到多少次onError通知，无参数版本的retry都会继续订阅并发射原始Observable。接受单个count参数的retry会最多重新订阅指定的次数，如果次数超了，它不会尝试再次订阅，它会把最新的一个onError通知传递给它的观察者。\n" +
                "还有一个版本的retry接受一个谓词函数作为参数，这个函数的两个参数是：重试次数和导致发射onError通知的Throwable。这个函数返回一个布尔值，如果返回true，retry应该再次订阅和镜像原始的Observable，如果返回false，retry会将最新的一个onError通知传递给它的观察者。\n" +
                "retry操作符默认在trampoline调度器上执行。\n" +
                "retryWhen和retry类似，区别是，retryWhen将onError中的Throwable传递给一个函数，这个函数产生另一个Observable，retryWhen观察它的结果再决定是不是要重新订阅原始的Observable。如果这个Observable发射了一项数据，它就重新订阅，如果这个Observable发射的是onError通知，它就将这个通知传递给观察者然后终止。\n" +
                "retryWhen默认在trampoline调度器上执行，你可以通过参数指定其它的调度器。\n\n" +
                "retryObservable = Observable.create(new Observable.OnSubscribe<Integer>() {\n" +
                "   @Override\n" +
                "   public void call(Subscriber<? super Integer> subscriber) {\n" +
                "       sb.append(\"subscriber.onNext(0)\\n\");\n" +
                "       subscriber.onNext(0);\n" +
                "       int error = 1 / 0;\n" +
                "   }\n" +
                "}).subscribeOn(Schedulers.io()).retry(2);\n" +
                "\n" +
                "retryWhenObservable = Observable.create(new Observable.OnSubscribe<Integer>() {\n" +
                "   @Override\n" +
                "   public void call(Subscriber<? super Integer> subscriber) {\n" +
                "       sb.append(\"subscriber.onNext(1)\\n\");\n" +
                "       subscriber.onNext(1);\n" +
                "       int error = 1 / 0;\n" +
                "    }\n" +
                "}).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {\n" +
                "   @Override\n" +
                "   public Observable<?> call(Observable<? extends Throwable> observable) {\n" +
                "       return observable.zipWith(Observable.range(1, 3), new Func2<Throwable, Integer, Integer>() {\n" +
                "           @Override\n" +
                "           public Integer call(Throwable throwable, Integer integer) {\n" +
                "               return integer;\n" +
                "           }\n" +
                "       }).flatMap(new Func1<Integer, Observable<?>>() {\n" +
                "           @Override\n" +
                "           public Observable<?> call(Integer o) {\n" +
                "               return Observable.timer(100 * o, TimeUnit.MILLISECONDS);\n" +
                "           }\n" +
                "       });\n" +
                "   }\n" +
                "});");

        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_subscribe = (Button) findViewById(R.id.btn_subscribe);
        btn_subscribe.setText("retry");

        Button btn_subscribe2 = (Button) findViewById(R.id.btn_subscribe2);
        btn_subscribe2.setText("retryWhen");
        btn_subscribe2.setVisibility(View.VISIBLE);

        retryObservable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                sb.append("subscriber.onNext(0)\n");
                subscriber.onNext(0);
                int error = 1 / 0;
            }
        }).subscribeOn(Schedulers.io()).retry(2);

        retryWhenObservable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                sb.append("subscriber.onNext(1)\n");
                subscriber.onNext(1);
                int error = 1 / 0;
            }
        }).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                return observable.zipWith(Observable.range(1, 3), new Func2<Throwable, Integer, Integer>() {
                    @Override
                    public Integer call(Throwable throwable, Integer integer) {
                        return integer;
                    }
                }).flatMap(new Func1<Integer, Observable<?>>() {
                    @Override
                    public Observable<?> call(Integer o) {
                        return Observable.timer(100 * o, TimeUnit.MILLISECONDS);
                    }
                });
            }
        });


        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                retryObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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
                retryWhenObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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
