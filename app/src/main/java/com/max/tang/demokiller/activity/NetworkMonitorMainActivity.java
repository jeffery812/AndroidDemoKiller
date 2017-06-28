package com.max.tang.demokiller.activity;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.utils.log.Logger;

@TargetApi(Build.VERSION_CODES.N) public class NetworkMonitorMainActivity extends BaseActivity {
    private static final String TAG = "NetworkMonitorMainActiv";

    NetworkStatsManager networkStatsManager;
    private NetworkStatsManager.UsageCallback usageCallback = new NetworkStatsManager.UsageCallback() {

        @Override public void onThresholdReached(int networkType, String subscriberId) {

            Logger.d(TAG, "NetworkStatsManager UsageCallback triggered");
        }
    };

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_monitor_main);

       networkStatsManager = (NetworkStatsManager)this.getSystemService(
            Context.NETWORK_STATS_SERVICE);

        hasPermissionToReadNetworkStats();

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void networkStatistic(View view){
        if( Build.VERSION.SDK_INT < Build.VERSION_CODES.M ){
            return;
        }
        final float mobileTotal1 = (TrafficStats.getMobileRxBytes() + TrafficStats.getMobileTxBytes())/1024;
        final float mobileTotal2 = getMoileTotal();
        Logger.d(TAG, "traffic1 Rx+Tx: " + mobileTotal1);
        Logger.d(TAG, "traffic2 Rx+Tx: " + mobileTotal2);
    }

    public void registerUsageCallback(View view){
        Logger.d(TAG, "register network callback");
        networkStatsManager.registerUsageCallback(ConnectivityManager.TYPE_MOBILE, "subscribId", 10, usageCallback);
    }

    public void unregisterUsageCallback(View view){

        networkStatsManager.unregisterUsageCallback(usageCallback);
    }

    private long getMoileTotal(){
        long total = 0;
        try {

            NetworkStats.Bucket bucket = null;// 获取到目前为止设备的Wi-Fi流量统计
            bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_MOBILE, "", 0, System.currentTimeMillis());
            total = bucket.getRxBytes() + bucket.getTxBytes();

        } catch (Exception e){
            Logger.e(TAG, e);
        }
        return total/1024;
    }

    private long[] getDataFromBucket(NetworkStats bucketForApp) {
        long dataTx = 0;
        long dataRx = 0;

        NetworkStats.Bucket bucket;

        while (bucketForApp.hasNextBucket()) {
            bucket = new NetworkStats.Bucket();
            bucketForApp.getNextBucket(bucket);
            dataTx += bucket.getTxBytes();
            dataRx += bucket.getRxBytes();

        }

        return new long[]{dataTx, dataRx};
    }

    private boolean hasPermissionToReadNetworkStats() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        final AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
        android.os.Process.myUid(), getPackageName());
        if (mode == AppOpsManager.MODE_ALLOWED) {
            return true;
        } else {

//            requestReadNetworkStats();
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
            return false;
        }
        // 打开“有权查看使用情况的应用”页面private void requestReadNetworkStats() {
        /*
        */
}
}
