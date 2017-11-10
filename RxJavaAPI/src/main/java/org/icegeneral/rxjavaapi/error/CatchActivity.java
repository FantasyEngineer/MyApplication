package org.icegeneral.rxjavaapi.error;

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
import rx.functions.Func1;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class CatchActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<Integer> onErrorReturnObservable;
    private Observable<Integer> onErrorResumeNextObservable;
    private Observable<Integer> onExceptionResumeNextObservable;

    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);

        tv_top.setText("Catch操作符拦截原始Observable的onError通知，将它替换为其它的数据项或数据序列，让产生的Observable能够正常终止或者根本不终止。\n" +
                "onErrorReturn\n" +
                "让Observable遇到错误时发射一个特殊的项并且正常终止。\n" +
                "\n" +
                "onErrorResumeNext\n" +
                "让Observable在遇到错误时开始发射第二个Observable的数据序列。\n" +
                "\n" +
                "onExceptionResumeNext\n" +
                "和onErrorResumeNext很相似，不同的是，如果onError收到的Throwable不是一个Exception，它会将错误传递给观察者的onError方法，不会使用备用的Observable。\n\n" +
                "onErrorReturnObservable = Observable.create(new Observable.OnSubscribe<Integer>() {\n" +
                "   @Override\n" +
                "   public void call(Subscriber<? super Integer> subscriber) {\n" +
                "       subscriber.onError(new Error(\"Test Error\"));\n" +
                "   }\n" +
                "}).onErrorReturn(new Func1<Throwable, Integer>() {\n" +
                "   @Override\n" +
                "   public Integer call(Throwable throwable) {\n" +
                "       return 999;\n" +
                "   }\n" +
                "});\n" +
                "\n" +
                "onErrorResumeNextObservable = Observable.create(new Observable.OnSubscribe<Integer>() {\n" +
                "   @Override\n" +
                "   public void call(Subscriber<? super Integer> subscriber) {\n" +
                "       subscriber.onError(new Error(\"Test Error\"));\n" +
                "   }\n" +
                "}).onErrorResumeNext(Observable.just(0, 1, 2));\n" +
                "\n" +
                "onExceptionResumeNextObservable = Observable.create(new Observable.OnSubscribe<Integer>() {\n" +
                "   @Override\n" +
                "   public void call(Subscriber<? super Integer> subscriber) {\n" +
                "       subscriber.onError(new Error(\"Test Error\"));\n" +
                "   }\n" +
                "}).onExceptionResumeNext(Observable.just(0, 1, 2));");

        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_subscribe = (Button) findViewById(R.id.btn_subscribe);
        btn_subscribe.setText("onErrorReturn");

        Button btn_subscribe2 = (Button) findViewById(R.id.btn_subscribe2);
        btn_subscribe2.setText("onErrorResumeNext");
        btn_subscribe2.setVisibility(View.VISIBLE);

        Button btn_subscribe3 = (Button) findViewById(R.id.btn_subscribe3);
        btn_subscribe3.setText("onExceptionResumeNext");
        btn_subscribe3.setVisibility(View.VISIBLE);

        onErrorReturnObservable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onError(new Error("Test Error"));
            }
        }).onErrorReturn(new Func1<Throwable, Integer>() {
            @Override
            public Integer call(Throwable throwable) {
                return 999;
            }
        });

        onErrorResumeNextObservable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onError(new Error("Test Error"));
            }
        }).onErrorResumeNext(Observable.just(0, 1, 2));

        onExceptionResumeNextObservable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onError(new Error("Test Error"));
            }
        }).onExceptionResumeNext(Observable.just(0, 1, 2));

        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                onErrorReturnObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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
                onErrorResumeNextObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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

        btn_subscribe3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                onExceptionResumeNextObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
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
