package com.hjg.hjgapplife.activity.Rx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hjg.baseapp.util.ToastUtil;
import com.hjg.baseapp.widget.dialog.BottomDialog;
import com.hjg.hjgapplife.R;
import com.hjg.hjgapplife.activity.baseRender.BaseOthreRenderSwipFinalActivity;
import com.hjg.hjgapplife.activity.baseRender.LayoutConstans;

import org.icegeneral.rxjavaapi.GenerateActivity;
import org.icegeneral.rxjavaapi.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class RxJavaMainActivity extends BaseOthreRenderSwipFinalActivity {

    @BindView(R.id.tv_test)
    TextView tvTest;
    @BindView(R.id.tv_disposable)
    TextView tvDisposable;
    @BindView(R.id.tv_Nodisposable)
    TextView tvNodisposable;
    @BindView(R.id.tv_Scheduler)
    TextView tv_Scheduler;
    @BindView(R.id.tv_Single)
    TextView tvSingle;
    @BindView(R.id.tv_Subject)
    TextView tvSubject;
    @BindView(R.id.tv_AsyncSubject)
    TextView tvAsyncSubject;
    @BindView(R.id.tv_BehaviorSubject)
    TextView tvBehaviorSubject;
    @BindView(R.id.tv_PublishSubject)
    TextView tvPublishSubject;
    @BindView(R.id.tv_ReplaySubject)
    TextView tvReplaySubject;

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
        topBarManage.setRightButtonImgAndTxt(true, null, "注意事项", v -> {
            BottomDialog bottomDialog = new BottomDialog(activity);
            bottomDialog.showDialog("上游可以发送无限个onNext, 下游也可以接收无限个onNext.\n" +
                    "当上游发送了一个onComplete后, 上游onComplete之后的事件将会继续发送, 而下游收到onComplete事件之后将不再继续接收事件.\n" +
                    "当上游发送了一个onError后, 上游onError之后的事件将继续发送, 而下游收到onError事件之后将不再继续接收事件.\n" +
                    "上游可以不发送onComplete或onError.\n" +
                    "最为关键的是onComplete和onError必须唯一并且互斥, 即不能发多个onComplete, 也不能发多个onError, 也不能先发一个onComplete, 然后再发一个onError, 反之亦然\n" +
                    "注: 关于onComplete和onError唯一并且互斥这一点, 是需要自行在代码中进行控制, 如果你的代码逻辑中违背了这个规则, 并不一定会导致程序崩溃. 比如发送多个onComplete是可以正常运行的, 依然是收到第一个onComplete就不再接收了, 但若是发送多个onError, 则收到第二个onError事件会导致程序会崩溃.\n");
        });
        //调到RX-API案例
        topBarManage.setLeftButtonImgAndTxt(true, null, "RX-API范例", view -> startActivity(new Intent(activity, MainActivity.class)));
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_test, R.id.tv_sub_onnext, R.id.tv_sub_onnext_onerror, R.id.tv_sub_onnext_onerror_oncomp
            , R.id.tv_disposable, R.id.tv_Nodisposable, R.id.tv_Scheduler, R.id.tv_Single, R.id.tv_Subject,
            R.id.tv_AsyncSubject, R.id.tv_BehaviorSubject, R.id.tv_PublishSubject, R.id.tv_ReplaySubject, R.id.tv_Serialized})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_test:
                BottomDialog bottomDialog = new BottomDialog(activity);
                bottomDialog.showDialog(getString(R.string.rx_framework));
                break;
            case R.id.tv_sub_onnext:
                initSubOverride(1);
                break;
            case R.id.tv_sub_onnext_onerror:
                initSubOverride(2);
                break;
            case R.id.tv_sub_onnext_onerror_oncomp:
                initSubOverride(3);
                break;
            case R.id.tv_disposable://拦截规则之后的数据
                initDisposableDemo(true);
                break;
            case R.id.tv_Nodisposable://不接受拦截之后的数据
                initDisposableDemo(false);
                break;
            case R.id.tv_Scheduler://线程调度DEMO
                BottomDialog bottomDialogScheduler = new BottomDialog(activity);
                bottomDialogScheduler.showDialog(getString(R.string.thread_scheduler));
                initScheduler();
                break;
            case R.id.tv_Single://Single
                BottomDialog bottomDialogSingle = new BottomDialog(activity);
                bottomDialogSingle.showDialog(getString(R.string.rx_single));
                initSingle();
                break;
            case R.id.tv_Subject://Subject桥梁
                BottomDialog bottomDialogSubjec = new BottomDialog(activity);
                bottomDialogSubjec.showDialog(getString(R.string.rx_subject));
                break;
            case R.id.tv_AsyncSubject:
                BottomDialog bottomAsyncSubject = new BottomDialog(activity);
                bottomAsyncSubject.showDialog(getString(R.string.rx_asynSuject));
                initAsynSubject();
                break;
            case R.id.tv_BehaviorSubject:
                BottomDialog bottomBehaviorSubject = new BottomDialog(activity);
                bottomBehaviorSubject.showDialog(getString(R.string.rx_behaivarSuject));
                initbehaviorSubject();
                break;
            case R.id.tv_PublishSubject:
                initPulishSubject();
                break;
            case R.id.tv_ReplaySubject:
                initReplaySubject();
                break;
            case R.id.tv_Serialized:
                BottomDialog bottomSerialized = new BottomDialog(activity);
                bottomSerialized.showDialog(getString(R.string.rx_serialized));
                break;
        }
    }

//--------------------------------------------------------------------------------------------------

    /**
     * testScheduler
     */
    private void initScheduler() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.d("RxJavaMainActivity", "被观察者所在的线程：" + Thread.currentThread().getName());
                e.onNext("测试");
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                Toast.makeText(activity, "观察者所在的线程：" + Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
    }

//--------------------------------------------------------------------------------------------------

    private void initSubOverride(int i) {
        Observable<String> observable = Observable.create(e -> {
            e.onNext("序列1");
            e.onNext("序列2");
            e.onNext("序列3");
            if (i == 2) {
                e.onError(new Throwable("遇到错误了"));
            } else {
                e.onComplete();
            }
        });

        //构造只接收onNext（）方法推送的数据（不接受错误和完成的消息）
        Consumer consumer = s -> Toast.makeText(activity, (CharSequence) s, Toast.LENGTH_SHORT).show();

        //作为subscribe的第二个参数，接受错误值
        Consumer consumerError = (Consumer<Throwable>) e -> Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();

        //作为subscribe的第三个传入参数，表现为传输完成
        Action action = () -> Toast.makeText(activity, "onComplete", Toast.LENGTH_SHORT).show();

        //发送者与接受者绑定
        //全部复写
//        observable.subscribe(receiver);
        switch (i) {
            case 1:
                //只接受ondext
                observable.subscribe(consumer);
                break;
            case 2:
                //只接受onnext和onerror
                observable.subscribe(consumer, consumerError);
                break;
            case 3:
                //和    sender.subscribe(receiver);相同，全部接受
                observable.subscribe(consumer, consumerError, action);
                break;
        }
    }
//--------------------------------------------------------------------------------------------------

    /**
     * 是否拦截
     *
     * @param isDispose true 是   false 不拦截
     */
    private void initDisposableDemo(boolean isDispose) {
        //发送源，也就是被监听方，也就是被观察者
        Observable.create((ObservableOnSubscribe<String>) e -> {
            e.onNext("序列1");
            e.onNext("序列2");
            e.onNext("序列3");
            e.onComplete();
        }).subscribe(new Observer<String>() {
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(String value) {
                if (isDispose) {
                    if (value.equals("序列2")) {//当接受到的值为***的时候，下次的消息将不再处理
                        mDisposable.dispose();
                    }
                }
                Toast.makeText(activity, value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                Toast.makeText(activity, "onComplete", Toast.LENGTH_SHORT).show();
            }
        });
    }
//--------------------------------------------------------------------------------------------------

    /**
     * single操作符(只能发送失败或者成功,且一次就会终止)
     */
    private void initSingle() {
        Single.create((SingleOnSubscribe<String>) e -> {
            Thread.sleep(3000);
            e.onError(new Throwable("失败了"));
//            e.onSuccess("成功");
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
            ToastUtil.show(activity, s);
        }, throwable -> {
            ToastUtil.show(activity, throwable.getMessage());
        });
    }

//--------------------------------------------------------------------------------------------------

    /**
     * AsynSubject(onnext的最后一个值和成功失败的状态会发送给观察者)
     */
    private void initAsynSubject() {
        AsyncSubject<String> asyncSubject = AsyncSubject.create();
        asyncSubject.onNext("订阅之前1");
        asyncSubject.onNext("订阅之前2");
        asyncSubject.onNext("订阅之前3");
//        asyncSubject.onComplete();//

        Observer observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                Toast.makeText(activity, "value:" + value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(activity, "onComplete", Toast.LENGTH_SHORT).show();
            }
        };

        asyncSubject.subscribe(observer);
        asyncSubject.onNext("订阅之后4");
        asyncSubject.onNext("订阅之后5");
        asyncSubject.onNext("订阅之后6");
        asyncSubject.onComplete();//必须要有完成状态
    }

    //--------------------------------------------------------------------------------------------------

    /**
     * BehaviorSubject  订阅之前的最后一条消息以及订阅之后的所有消息。
     */
    private void initbehaviorSubject() {
        BehaviorSubject behaviorSubject = BehaviorSubject.create();
        behaviorSubject.onNext("订阅之前1");
        behaviorSubject.onNext("订阅之前2");
        behaviorSubject.onNext("订阅之前3");
        Observer observer = new Observer<String>() {
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

            }
        };
        behaviorSubject.subscribe(observer);

        behaviorSubject.onNext("订阅之后1");
        behaviorSubject.onNext("订阅之后2");
        behaviorSubject.onNext("订阅之后3");
        behaviorSubject.onNext("订阅之后4");

    }
    //--------------------------------------------------------------------------------------------------


    private void initReplaySubject() {
        ReplaySubject replaysubject = ReplaySubject.create();
        replaysubject.onNext("订阅之前1");
        replaysubject.onNext("订阅之前2");
        replaysubject.onNext("订阅之前3");
        Observer observer = new Observer<String>() {
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

            }
        };
        replaysubject.subscribe(observer);

        replaysubject.onNext("订阅之后1");
        replaysubject.onNext("订阅之后2");
        replaysubject.onNext("订阅之后3");
        replaysubject.onNext("订阅之后4");

    }

    //--------------------------------------------------------------------------------------------------

    private void initPulishSubject() {

        PublishSubject pblish = PublishSubject.create();
        pblish.onNext("订阅之前1");
        pblish.onNext("订阅之前2");
        pblish.onNext("订阅之前3");
        Observer observer = new Observer<String>() {
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

            }
        };
        pblish.subscribe(observer);
        pblish.onNext("订阅之后1");
        pblish.onNext("订阅之后2");
        pblish.onNext("订阅之后3");
        pblish.onNext("订阅之后4");
    }


}
