package org.icegeneral.rxjavaapi.transform;

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
public class FlatMapActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<String> observable;
    private Observable<String> concatMapObservable;
    private Observable<String> switchMapObservable;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);
        tv_top.setText("将一个发射数据的Observable变换为多个Observables，然后将它们发射的数据合并后放进一个单独的Observable。\n" +
                "这个方法是很有用的，例如，当你有一个这样的Observable：它发射一个数据序列，这些数据本身包含Observable成员或者可以变换为Observable，因此你可以创建一个新的Observable发射这些次级Observable发射的数据的完整集合。\n" +
                "注意：FlatMap对这些Observables发射的数据做的是merge操作，因此它们可能是交错的。为了防止交错的发生，可以使用与之类似的concatMap()操作符。\n" +
                "map和flatMapmap的区别：map transform one event to another, flatMap transform one event to zero or more event.\n\n" +
                "Observable<String> observable = Observable.just(0, 1, 2).flatMap(new Func1<Integer, Observable<String>>() {\n" +
                "   @Override\n" +
                "   public Observable<String> call(Integer integer) {\n" +
                "       int delay = 2 - integer;// 0延迟2s，1延迟1s，2无延迟\n" +
                "       return Observable.just(\"A\" + integer).delay(delay, TimeUnit.SECONDS);\n" +
                "   }\n" +
                "});\n" +
                "Observable<String> concatMapObservable = Observable.just(0, 1, 2).concatMap(new Func1<Integer, Observable<String>>() {\n" +
                "   @Override\n" +
                "   public Observable<String> call(Integer integer) {\n" +
                "       int delay = 2 - integer;// 0延迟2s，1延迟1s，2无延迟\n" +
                "       return Observable.just(\"A\" + integer).delay(delay, TimeUnit.SECONDS);\n" +
                "   }\n" +
                "});\n" +
                "Observable<String> switchMapObservable = Observable.just(0, 1, 2).switchMap(new Func1<Integer, Observable<String>>() {\n" +
                "   @Override\n" +
                "   public Observable<String> call(Integer integer) {\n" +
                "       int delay = 2 - integer;// 0延迟2s，1延迟1s，2无延迟\n" +
                "       return Observable.just(\"A\" + integer).delay(delay, TimeUnit.SECONDS);\n" +
                "   }\n" +
                "});");
        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_subscribe = (Button) findViewById(R.id.btn_subscribe);
        btn_subscribe.setText("flatMap");
        Button btn_subscribe2 = (Button) findViewById(R.id.btn_subscribe2);
        btn_subscribe2.setText("concatMap");
        btn_subscribe2.setVisibility(View.VISIBLE);
        Button btn_subscribe3 = (Button) findViewById(R.id.btn_subscribe3);
        btn_subscribe3.setText("switchMap");
        btn_subscribe3.setVisibility(View.VISIBLE);


        observable = Observable.just(0, 1, 2).flatMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer integer) {
                int delay = 2 - integer;// 0延迟2s，1延迟1s，2无延迟
                return Observable.just("A" + integer).delay(delay, TimeUnit.SECONDS);
            }
        });
        concatMapObservable = Observable.just(0, 1, 2).concatMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer integer) {
                int delay = 2 - integer;// 0延迟2s，1延迟1s，2无延迟
                return Observable.just("A" + integer).delay(delay, TimeUnit.SECONDS);
            }
        });
        switchMapObservable = Observable.just(0, 1, 2).switchMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer integer) {
                int delay = 2 - integer;// 0延迟2s，1延迟1s，2无延迟
                return Observable.just("A" + integer).delay(delay, TimeUnit.SECONDS);
            }
        });
        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb);
                observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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

        btn_subscribe2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb);
                concatMapObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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

        btn_subscribe3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb);
                switchMapObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
    }
}
