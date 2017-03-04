package com.max.tang.demokiller.RxBinding;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.activity.BaseActivity;
import com.max.tang.demokiller.utils.RxBus;
import com.max.tang.demokiller.utils.device.AndroidSettingsUtil;
import com.max.tang.demokiller.utils.log.Logger;
import com.tbruyelle.rxpermissions.RxPermissions;
import java.util.Locale;
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

    DeviceInfoAdapter mDeviceInfoAdapter;
    Observable<Void> verifyCodeObservable;
    Subscription subscription;
    Subscription rxBusSubscription;

    //@BindView(R.id.rxbinding_fab_fab) FloatingActionButton mFabFab;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_binding);
        ButterKnife.bind(this);

        mDeviceInfoAdapter = new DeviceInfoAdapter(this);
        //mRecyclerViewInfo.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
        mRecyclerViewInfo.setLayoutManager(new GridLayoutManager(this, 1));//这里用线性宫格显示 类似于grid view
        //        mRecyclerViewInfo.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流

        mRecyclerViewInfo.setAdapter(mDeviceInfoAdapter);

        initEditText();

        initButton();

        /**
         * warning: 单例, 注意要取消订阅, rxBusSubscription.unsubscribe()
         * 否则会重复订阅
         */
        rxBusSubscription = RxBus.instanceOf().getEvents().subscribe(new Action1<Object>() {
            @Override public void call(Object o) {
                if (o instanceof String) {
                    Logger.d("got event: " + o);
                }
            }
        });
    }

    private void initButton() {
        /**
         * 验证码倒计时
         * http://blog.csdn.net/qq_17766199/article/details/54646011
         */
        int SECOND = 20;
        mBtnRegister.setText("Get Verification Code");
        verifyCodeObservable = RxView.clicks(mBtnRegister)
            .throttleFirst(SECOND, TimeUnit.SECONDS) //防止20秒内连续点击,或者只使用doOnNext部分
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnNext(new Action1<Void>() {
                @Override public void call(Void aVoid) {
                    RxView.enabled(mBtnRegister).call(false);
                }
            });

        verifyCodeObservable.subscribe(new Action1<Void>() {
            @Override public void call(Void aVoid) {
                subscription =
                    Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .take(SECOND)
                        .subscribe(new Observer<Long>() {
                            @Override public void onCompleted() {
                                RxTextView.text(mBtnRegister).call("Get Verification Code");
                                RxView.enabled(mBtnRegister).call(true);
                            }

                            @Override public void onError(Throwable e) {
                                Logger.e(TAG, e.toString());
                            }

                            @Override public void onNext(Long aLong) {
                                RxTextView.text(mBtnRegister).call((SECOND - aLong) + "s left");
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

    private void loadData() {

        RxPermissions.getInstance(this).request(android.Manifest.permission.READ_PHONE_STATE)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(granted -> {
                String imei = "";
                if( granted ) {
                    imei = AndroidSettingsUtil.getImei(this);
                    Logger.d(TAG, "IMEI: " + imei);
                }else{
                    imei = "need permission";
                }
                mDeviceInfoAdapter.addData(new DeviceInfo("IMEI", imei));
            });
        mDeviceInfoAdapter.clearData();
        mDeviceInfoAdapter.addData(new DeviceInfo("Android Version", Build.VERSION.RELEASE));
        mDeviceInfoAdapter.addData(new DeviceInfo("Brand", Build.BRAND));
        mDeviceInfoAdapter.addData(new DeviceInfo("Manufacture", Build.MANUFACTURER));
        mDeviceInfoAdapter.addData(new DeviceInfo("Model", Build.MODEL));
        mDeviceInfoAdapter.addData(new DeviceInfo("BuildNumber", Build.DISPLAY));
        mDeviceInfoAdapter.addData(new DeviceInfo("Language",
            Locale.getDefault().getDisplayLanguage() + ", " + Locale.getDefault().getLanguage()));

        Toast.makeText(this, "Finished", Toast.LENGTH_SHORT).show();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        verifyCodeObservable.unsubscribeOn(AndroidSchedulers.mainThread());

        if (subscription != null) {
            subscription.unsubscribe();
        }

        if (rxBusSubscription != null) {
            rxBusSubscription.unsubscribe();
        }
    }

    @Override protected void onStop() {
        super.onStop();
        Logger.d("RxBindingActivity onStop");
    }
}
