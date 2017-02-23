package com.max.tang.demokiller.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.activity.NavigationActivity;
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

        int startCounter = 0;
        if( intent != null && intent.getExtras() != null){
            startCounter = intent.getExtras().getInt("start_counter",0);
        }

        Logger.d(TAG, "onStartCommand() executed : " + startCounter);
        final int offset = startCounter;
        Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
            .subscribe(new Subscriber<Long>() {
                @Override public void onCompleted() {

                }

                @Override public void onError(Throwable e) {

                }

                @Override public void onNext(Long aLong) {

                    final long v = aLong + offset;
                    notifyUI(v);
                    Logger.d("service is running: " + v);
                }
            });
        //return super.onStartCommand(intent, flags, startId);
        return START_REDELIVER_INTENT;
        //return START_NOT_STICKY;
        //return START_STICKY;
    }

    public void notifyUI(final long count) {
        Bitmap btm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        NotificationCompat.Builder builder =
            new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Service is running")
                .setContentText("counter: " + count);
        builder.setTicker("New message");//第一次提示消息的时候显示在通知栏上
        builder.setNumber(12);
        builder.setLargeIcon(btm);
        builder.setAutoCancel(true);//自己维护通知的消失

        //构建一个Intent
        Intent resultIntent = new Intent(this, NavigationActivity.class);
        //封装一个Intent
        PendingIntent resultPendingIntent =
            PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // 设置通知主题的意图
        builder.setContentIntent(resultPendingIntent);
        //获取通知管理器对象
        NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
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
