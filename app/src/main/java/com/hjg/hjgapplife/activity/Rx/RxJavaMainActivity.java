package com.hjg.hjgapplife.activity.Rx;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hjg.baseapp.widget.dialog.BottomDialog;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipFinalActivity;
import com.hjg.hjgapplife.activity.baseRender.LayoutConstans;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RxJavaMainActivity extends BaseOthreRenderSwipFinalActivity {

    @BindView(R.id.tv_test)
    TextView tvTest;
    @BindView(R.id.tv_emitter)
    TextView tvEmitter;
    @BindView(R.id.tv_disposable)
    TextView tvDisposable;
    private Observable<String> sender;
    private Observer<String> receiver;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_rx_java_main;
    }

    @Override
    protected int getIncludeLayoutType() {
        return LayoutConstans.BAR_OUT_SV;
    }

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "RxJava");
    }

    @Override
    protected void initData() {
        //发送源，也就是被监听方，也就是被观察者
        sender = Observable.create(e -> {
            e.onNext("这是onNext");
            e.onNext("这是onNext1");
            e.onComplete();
        });
        //观察者，数据接收方
        receiver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                Toast.makeText(activity, value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(activity, "onComplete", Toast.LENGTH_SHORT).show();
            }
        };
        //发送者与接受者绑定
        sender.subscribe(receiver);

    }

    @OnClick({R.id.tv_test, R.id.tv_emitter, R.id.tv_disposable})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_test:
                BottomDialog bottomDialog = new BottomDialog(activity);
                bottomDialog.showDialog(getString(R.string.rx_framework));
                break;
            case R.id.tv_emitter:
                BottomDialog bottomDialogemitter = new BottomDialog(activity);
//                bottomDialogemitter
                break;
            case R.id.tv_disposable:
                break;
        }
    }
}
