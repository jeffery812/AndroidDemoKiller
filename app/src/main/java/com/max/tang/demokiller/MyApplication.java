package com.max.tang.demokiller;

import android.support.multidex.MultiDexApplication;
import com.max.tang.demokiller.utils.log.Logger;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by zhihuitang on 2016-11-28.
 */

public class MyApplication extends MultiDexApplication {
    private static final String TAG = "MyApplication";
    @Override public void onCreate() {
        super.onCreate();
        Logger.d(TAG, "onCreate: MyApplication");
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
