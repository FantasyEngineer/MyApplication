package org.icegeneral.rxjavaapi.transform;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.icegeneral.rxjavaapi.R;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class BufferActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<List<Integer>> observable;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);
        tv_top.setText("定期收集Observable的数据放进一个数据包裹，然后发射这些数据包裹，而不是一次发射一个值。buffer有多个重载方法，例子不一一列举。\n" +
                "注意：如果原来的Observable发射了一个onError通知，Buffer会立即传递这个通知，而不是首先发射缓存的数据，即使在这之前缓存中包含了原始Observable发射的数据。\n\n" +
                "Observable<List<Integer>> observable = Observable.just(0, 1, 2).buffer(2);");
        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_subscribe = (Button) findViewById(R.id.btn_subscribe);

        observable = Observable.just(0, 1, 2).buffer(3);

        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                observable.subscribe(new Subscriber<List<Integer>>() {
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
                    public void onNext(List<Integer> o) {
                        sb.append("onNext() ");
                        for (Integer i : o) {
                            sb.append(i);
                            sb.append(" ");
                        }
                        sb.append("\n");
                        tv_bottom.setText(sb);
                    }
                });
            }
        });
    }
}
