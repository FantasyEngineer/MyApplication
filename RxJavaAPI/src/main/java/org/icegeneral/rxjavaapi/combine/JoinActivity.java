package org.icegeneral.rxjavaapi.combine;

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
import rx.functions.Func2;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class JoinActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<String> observable;
    private Observable<Observable<String>> groupJoinObservable;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);

        tv_top.setText("join操作符把类似于combineLatest操作符，也是两个Observable产生的结果进行合并，合并的结果组成一个新的Observable，但是join操作符可以控制每个Observable产生结果的生命周期，在每个结果的生命周期内，可以与另一个Observable产生的结果按照一定的规则进行合并。\n" +
                "observable = observable2.join(observable3, new Func1<Integer, Observable<Integer>>() {\n" +
                "   @Override\n" +
                "   public Observable<Integer> call(Integer integer) {\n" +
                "       return Observable.just(integer).delay(200, TimeUnit.MILLISECONDS);\n" +
                "   }\n" +
                "}, new Func1<String, Observable<String>>() {\n" +
                "   @Override\n" +
                "   public Observable<String> call(String s) {\n" +
                "       return Observable.just(s).delay(150, TimeUnit.MILLISECONDS);\n" +
                "   }\n" +
                "}, new Func2<Integer, String, String>() {\n" +
                "   @Override\n" +
                "   public String call(Integer integer, String s) {\n" +
                "       return s + integer;\n" +
                "   }\n" +
                "});");

        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        View btn_subscribe = findViewById(R.id.btn_subscribe);


        Observable<Integer> observable2 = Observable.just(0, 1, 2);
        Observable<String> observable3 = Observable.just("A", "B", "C", "D");

        observable = observable2.join(observable3, new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
                return Observable.just(integer).delay(200, TimeUnit.MILLISECONDS);
            }
        }, new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return Observable.just(s).delay(150, TimeUnit.MILLISECONDS);
            }
        }, new Func2<Integer, String, String>() {
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
