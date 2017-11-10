package org.icegeneral.rxjavaapi.transform;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.icegeneral.rxjavaapi.R;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class MapActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<String> observable;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);
        tv_top.setText("对Observable发射的每一项数据应用一个函数，执行变换操作。\n" +
                "cast操作符将原始Observable发射的每一项数据都强制转换为一个指定的类型，然后再发射数据，它是map的一个特殊版本。其实只是强转而已，比较简单，例子就不实现了。\n\n" +
                "Observable<String> observable = Observable.just(0, 1, 2).map(new Func1<Integer, String>() {\n" +
                "   @Override\n" +
                "   public String call(Integer integer) {\n" +
                "       return \"A\" + integer;// 0 -> A0\n" +
                "   }\n" +
                "});\n" +
                "//Observable<String> observable = Observable.just(object).cast(String.class)");
        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_subscribe = (Button) findViewById(R.id.btn_subscribe);

        observable = Observable.just(0, 1, 2).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return "A" + integer;// 0 -> A0
            }
        });

        observable = Observable.just(0, 1, 2).cast(String.class);

        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                observable.subscribe(new Subscriber<String>() {
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
