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

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class SwitchActivity extends AppCompatActivity {

    private TextView tv_bottom;

    private Observable<Integer> observable;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);

        tv_top.setText("将一个发射多个Observables的Observable转换成另一个单独的Observable，注意：当原始Observable发射了一个新的Observable时，它将取消订阅之前的那个Observable。这意味着，在后来那个Observable产生之后到它开始发射数据之前的这段时间里，前一个Observable发射的数据将被丢弃。）\n" +
                "Observable<Observable<Integer>> observable2 = Observable.just(\n" +
                "   Observable.just(0, 1, 2).delay(1, TimeUnit.SECONDS),\n" +
                "   Observable.just(3, 4, 5),\n" +
                "   Observable.just(6, 7, 8));\n" +
                "observable = Observable.switchOnNext(observable2);");

        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_subscribe = (Button) findViewById(R.id.btn_subscribe);


        Observable<Observable<Integer>> observable2 = Observable.just(
                Observable.just(0, 1, 2).delay(1, TimeUnit.SECONDS),
                Observable.just(3, 4, 5),
                Observable.just(6, 7, 8));
        observable = Observable.switchOnNext(observable2);

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
