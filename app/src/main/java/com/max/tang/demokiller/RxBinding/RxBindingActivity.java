package com.max.tang.demokiller.RxBinding;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.activity.BaseActivity;
import com.max.tang.demokiller.utils.RxBus;
import com.max.tang.demokiller.utils.log.Logger;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class RxBindingActivity extends BaseActivity {
    private static final String TAG = "RxBinding";
    //@BindView(R.id.rxbinding_t_toolbar) Toolbar mTToolbar;
    @BindView(R.id.rxbinding_et_usual_approach) EditText mEtUsualApproach;
    @BindView(R.id.rxbinding_et_reactive_approach) EditText mEtReactiveApproach;
    @BindView(R.id.rxbinding_tv_show) TextView mTvShow;
    @BindView(R.id.button_load) Button mBtnLoad;
    @BindView(R.id.recycler_view_info) RecyclerView mRecyclerViewInfo;
    @BindView(R.id.button_register) Button mBtnRegister;

    Observable<Void> verifyCodeObservable;
    Subscription subscription;
    Subscription rxBusSubscription;

    //@BindView(R.id.rxbinding_fab_fab) FloatingActionButton mFabFab;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_binding);
        ButterKnife.bind(this);

        initEditText();

        initButton();

        /**
         * warning: 单例, 注意要取消订阅, rxBusSubscription.unsubscribe()
         */
        rxBusSubscription = RxBus.instanceOf().getEvents().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if( o instanceof String ){
                    Logger.d("got event: " + o);
                }
            }
        });

    }

    private void initButton(){
        /**
         * 验证码倒计时
         * http://blog.csdn.net/qq_17766199/article/details/54646011
         */
        int SECOND = 20;
       verifyCodeObservable = RxView.clicks(mBtnRegister)
            .throttleFirst(SECOND, TimeUnit.SECONDS) //防止20秒内连续点击,或者只使用doOnNext部分
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnNext(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    RxView.enabled(mBtnRegister).call(false);
                }
            });

         verifyCodeObservable.subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                subscription = Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                    .take(SECOND)
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onCompleted() {
                            RxTextView.text(mBtnRegister).call("获取验证码");
                            RxView.enabled(mBtnRegister).call(true);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Logger.e(TAG, e.toString());
                        }

                        @Override
                        public void onNext(Long aLong) {
                            RxTextView.text(mBtnRegister).call("剩余" + (SECOND - aLong) + "秒");
                            Logger.d(TAG, "剩余" + (SECOND - aLong) + "秒");
                        }
                    });
            }
        });

        /**
         * 利用操作符throttleFirst取时间间隔内第一次点击事件。同样利用操作符throttleLast、debounce也可以实现。
         */
        RxView.clicks(mBtnLoad)
            .throttleFirst(3, TimeUnit.SECONDS)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<Void>() {
                @Override public void call(Void aVoid) {
                    Logger.d(TAG, "load data");

                    RxBus.instanceOf().postEvent("event sent: Button clicked");

                    loadData();

                }
            });
    }

    private void initEditText() {
        // 正常方式
        mEtUsualApproach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTvShow.setText(s);
            }

            @Override public void afterTextChanged(Editable s) {

            }

        });

        // Rx方式
        //RxTextView.textChanges(mEtReactiveApproach).subscribe(mTvShow::setText);
        RxTextView.textChanges(mEtReactiveApproach).subscribe(new Action1<CharSequence>() {
            @Override public void call(CharSequence charSequence) {
                mTvShow.setText(charSequence);
            }
        });
    }

    private void loadData(){

        //RxDataSource<String> rxDataSource = new RxDataSource<>(dataSet);

    }

    @Override protected void onStop() {
        super.onStop();
        Logger.d("RxBindingActivity onStop");
        verifyCodeObservable.unsubscribeOn(AndroidSchedulers.mainThread());
        if( subscription != null ) {
            subscription.unsubscribe();
        }

        if( rxBusSubscription != null ){
            rxBusSubscription.unsubscribe();
        }
    }
}
