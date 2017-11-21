package com.hjg.hjgapplife.activity.okhttp;

import android.view.View;
import android.widget.Button;

import com.example.hjg.okhttp.IResponseListener;
import com.example.hjg.okhttp.intfacecapsulation.MyRequest;
import com.example.hjg.okhttp.toolcapsulation.OkHttpUtils;
import com.hjg.baseapp.util.ToastUtil;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipFinalActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DemoOKActivity extends BaseOthreRenderSwipFinalActivity {
    String url = "http://api.openweathermap.org/data/2.5/weather?q=shenzhen&mode=json&APPID=6c113432fd84a6e28268af291821db16";
    @BindView(R.id.button0)
    Button mButton0;
    @BindView(R.id.button1)
    Button mButton1;
    @BindView(R.id.button2)
    Button mButton2;
    @BindView(R.id.button3)
    Button mButton3;
    @BindView(R.id.button4)
    Button mButton4;
    @BindView(R.id.button5)
    Button mButton5;
    private MyRequest myRequest;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_demo_ok;
    }

    @Override
    protected int getIncludeLayoutType() {
        return 0;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "OKHTTP的使用案例");
    }

    @Override
    protected void initData() {

    }

    private void BaseRequest() {
        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder().
                connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .cache(new Cache(activity.getExternalFilesDir("okhttp"), 1024 * 1024 * 10));
        OkHttpClient client = mBuilder.build();

        Request.Builder builder = new Request.Builder().url(url);
        Request build = builder.build();
        client.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(() -> ToastUtil.show(activity, e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                activity.runOnUiThread(() -> {
                    try {
                        ToastUtil.show(activity, response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });


            }
        });
    }

    @OnClick({R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button0:
                BaseRequest();
                break;
            case R.id.button1:
                Map<String, String> map = new HashMap();
                map.put("111", "2222");
                OkHttpUtils.doGet(activity, url, map, new IResponseListener() {
                    @Override
                    public void success(String data) {
                        activity.runOnUiThread(() -> ToastUtil.show(activity, data));
                    }

                    @Override
                    public void fail(String code, String codDes) {
                        activity.runOnUiThread(() -> ToastUtil.show(activity, codDes));
                    }
                });
                break;
            case R.id.button2:
                myRequest = new MyRequest();
                myRequest.doGet(url, "1", new IResponseListener() {
                    @Override
                    public void success(String data) {
                        activity.runOnUiThread(() -> ToastUtil.show(activity, data));
                    }

                    @Override
                    public void fail(String code, String codDes) {
                        activity.runOnUiThread(() -> ToastUtil.show(activity, codDes));
                    }
                });
                break;
            case R.id.button3:
                break;
            case R.id.button4:
                break;
            case R.id.button5:
                myRequest.cancelRequest("1");
                break;
        }
    }
}
