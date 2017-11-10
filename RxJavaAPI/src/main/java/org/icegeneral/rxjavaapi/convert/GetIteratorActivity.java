package org.icegeneral.rxjavaapi.convert;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.icegeneral.rxjavaapi.R;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by jianjun.lin on 16/7/8.
 */
public class GetIteratorActivity extends AppCompatActivity {

    private TextView tv_bottom;


    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView tv_top = (TextView) findViewById(R.id.tv_top);

        tv_top.setText(
                "btn_subscribe.setOnClickListener(new View.OnClickListener() {\n" +
                        "   @Override\n" +
                        "   public void onClick(View v) {\n" +
                        "       sb.setLength(0);\n" +
                        "       tv_bottom.setText(sb.toString());\n" +
                        "       Iterator<Integer> iterator = Observable.range(0, 3).delay(2, TimeUnit.SECONDS).toBlocking().getIterator();\n" +
                        "       while (iterator.hasNext()) {\n" +
                        "           sb.append(iterator.next());\n" +
                        "           sb.append(\" \");\n" +
                        "       }\n" +
                        "       tv_bottom.setText(sb.toString());\n" +
                        "   }\n" +
                        "});");

        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        Button btn_subscribe = (Button) findViewById(R.id.btn_subscribe);

        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setLength(0);
                tv_bottom.setText(sb.toString());
                Iterator<Integer> iterator = Observable.range(0, 3).delay(2, TimeUnit.SECONDS).toBlocking().getIterator();
                while (iterator.hasNext()) {
                    sb.append(iterator.next());
                    sb.append(" ");
                }
                tv_bottom.setText(sb.toString());
            }
        });

    }
}
