package com.max.tang.demokiller.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.max.tang.demokiller.utils.log.Logger;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by zhihuitang on 2017-02-13.
 */

public class DemoService extends Service{
    final String TAG = "service-demo";

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d(TAG, "onCreate() executed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d(TAG, "onStartCommand() executed");
        Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
            .subscribe(new Subscriber<Long>() {
                @Override public void onCompleted() {

                }

                @Override public void onError(Throwable e) {

                }

                @Override public void onNext(Long aLong) {

                    Logger.d("service is running: " + aLong);
                }
            });
        return super.onStartCommand(intent, flags, startId);
        //return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(TAG, "onDestroy() executed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
