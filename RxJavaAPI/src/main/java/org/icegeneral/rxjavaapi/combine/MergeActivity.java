package org.icegeneral.rxjavaapi.combine;

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
import rx.schedulers.Schedulers;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class MergeActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<Integer> mergeObservable;
    private Observable<Integer> concatObservable;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);

        tv_top.setText("使用Merge操作符你可以将多个Observables的输出合并，就好像它们是一个单个的Observable一样。Merge可能会让合并的Observables发射的数据交错（有一个类似的操作符Concat不会让数据交错，它会按顺序一个接着一个发射多个Observables的发射物）。\n" +
                "Observable c = Observable.zip(a,b)等同于 Observable c = a.zipWith(b)\n\n" +
                "Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {\n" +
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
                "   }).subscribeOn(Schedulers.io());\n" +
                "Observable<Integer> observable2 = Observable.just(3, 4, 5);\n" +
                "\n" +
                "mergeObservable = Observable.merge(observable, observable2);\n" +
                "concatObservable = Observable.concat(observable, observable2);");

        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_merge = (Button) findViewById(R.id.btn_subscribe);
        btn_merge.setText("merge");
        Button btn_concat = (Button) findViewById(R.id.btn_subscribe2);
        btn_concat.setText("concat");
        btn_concat.setVisibility(View.VISIBLE);

        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                int[] nums = {0, 1, 2,};
                long[] sleeps = {500, 500, 0};
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
        Observable<Integer> observable2 = Observable.just(3, 4, 5);

        mergeObservable = Observable.merge(observable, observable2);
        concatObservable = Observable.concat(observable, observable2);

        btn_merge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                mergeObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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

        btn_concat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                concatObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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
