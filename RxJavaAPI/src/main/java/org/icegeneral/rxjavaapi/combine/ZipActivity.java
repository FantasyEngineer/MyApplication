package org.icegeneral.rxjavaapi.combine;

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
import rx.functions.Func2;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class ZipActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<String> observable;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);

        tv_top.setText("将多个Observables的发射物结合到一起，它只发射与发射数据项最少的那个Observable一样多的数据。\n" +
                "Observable c = Observable.zip(a,b)等同于 Observable c = a.zipWith(b)\n\n" +
                "Observable<Integer> observable2 = Observable.create(new Observable.OnSubscribe<Integer>() {\n" +
                "   @Override\n" +
                "   public void call(Subscriber<? super Integer> subscriber) {\n" +
                "       int[] nums = {0, 1, 2,};\n" +
                "       long[] sleeps = {500, 500, 0};\n" +
                "       for (int i = 0; i < nums.length; i++) {\n" +
                "           subscriber.onNext(nums[i]);\n" +
                "           try {\n" +
                "               Thread.sleep(sleeps[i]);\n" +
                "           } catch (InterruptedException e) {\n" +
                "               e.printStackTrace();\n" +
                "           }\n" +
                "       }\n" +
                "       subscriber.onCompleted();\n" +
                "   }\n" +
                "}).subscribeOn(Schedulers.io());\n" +
                "Observable<String> observable3 = Observable.just(\"A\", \"B\", \"C\", \"D\");\n" +
                "\n" +
                "observable = Observable.zip(observable2, observable3, new Func2<Integer, String, String>() {\n" +
                "   @Override\n" +
                "   public String call(Integer integer, String s) {\n" +
                "       return s + integer;\n" +
                "   }\n" +
                "});");

        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_subscribe = (Button) findViewById(R.id.btn_subscribe);


        Observable<Integer> observable2 = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                int[] nums = {0, 1, 2,};
                long[] sleeps = {500, 500, 500};
                for (int i = 0; i < nums.length; i++) {
                    try {
                        Thread.sleep(sleeps[i]);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    subscriber.onNext(nums[i]);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
        Observable<String> observable3 = Observable.just("A", "B", "C", "D");

        observable = Observable.zip(observable2, observable3, new Func2<Integer, String, String>() {
            @Override
            public String call(Integer integer, String s) {
                return s + integer;
            }
        });

        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
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

    }
}
