package com.max.tang.demokiller.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.utils.NetUtils;
import com.max.tang.demokiller.utils.log.Logger;

public class DemoNotification extends BaseActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_notification);
    }

    public void notification1(View view) {
        Bitmap btm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        NotificationCompat.Builder builder =
            new NotificationCompat.Builder(DemoNotification.this).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Please check Bubble Sort")
                .setContentText("crafttang@gmail.com");
        builder.setTicker("New message");//第一次提示消息的时候显示在通知栏上
        builder.setNumber(12);
        builder.setLargeIcon(btm);
        builder.setAutoCancel(true);//自己维护通知的消失

        //构建一个Intent
        Intent resultIntent = new Intent(DemoNotification.this, SortActivity.class);
        //封装一个Intent
        PendingIntent resultPendingIntent =
            PendingIntent.getActivity(DemoNotification.this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // 设置通知主题的意图
        builder.setContentIntent(resultPendingIntent);
        //获取通知管理器对象
        NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    public void notification2(View view) {
        Bitmap btm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher2);
        Intent intent = new Intent(DemoNotification.this, WatchActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(DemoNotification.this, 0, intent,
            PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new NotificationCompat.Builder(DemoNotification.this).setSmallIcon(
            R.mipmap.ic_launcher2)
            .setLargeIcon(btm)
            .setNumber(13)
            .setContentIntent(pendingIntent)
            //.setAutoCancel(true)
            .setStyle(new NotificationCompat.InboxStyle().addLine(
                "M.Twain (Google+) Haiku is more than a cert...")
                .addLine("M.Twain Reminder")
                .addLine("M.Twain Lunch?")
                .addLine("M.Twain Revised Specs")
                .addLine("M.Twain ")
                .addLine("Google Play Celebrate 25 billion apps with Goo..")
                .addLine("Stack Exchange StackOverflow weekly Newsl...")
                .setBigContentTitle("6 new message")
                .setSummaryText("crafttang@gmail.com"))
            .build();

        NotificationManager mNotificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, notification);
    }

    public void checkNetwork(View view) {
        ConnectivityManager connectivitymanager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivitymanager.getActiveNetworkInfo();

        if( networkInfo != null ) {
            Logger.d("shanghai", "network " + networkInfo.getDetailedState()+ ", " + networkInfo.getTypeName());
        }else {
            Logger.d("shanghai", "network is none" );
        }

        if( NetUtils.isAirplaneModeOn(this) ) {
            Toast.makeText(this, "Airplane mode is on", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Airplane mode is off", Toast.LENGTH_SHORT).show();
        }

    }
}
