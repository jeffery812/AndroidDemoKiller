package com.max.tang.demokiller;

import android.support.multidex.MultiDexApplication;
import android.util.Log;
import com.max.tang.demokiller.utils.FakeCrashLibrary;
import com.max.tang.demokiller.utils.log.Logger;
import com.squareup.leakcanary.LeakCanary;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by zhihuitang on 2016-11-28.
 */

public class MyApplication extends MultiDexApplication {
    private static final String TAG = "MyApplication";
    @Override public void onCreate() {
        super.onCreate();

        // https://github.com/chrisjenx/Calligraphy
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build()
        );

        Logger.d(TAG, "onCreate: MyApplication");
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    /** A tree which logs important information for crash reporting. */
    private static class CrashReportingTree extends Timber.Tree {
        @Override protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            FakeCrashLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(t);
                }
            }
        }
    }
}
