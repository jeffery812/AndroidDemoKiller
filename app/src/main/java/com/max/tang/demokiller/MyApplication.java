package com.max.tang.demokiller;

import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.Toast;
import com.max.tang.demokiller.utils.FakeCrashLibrary;
import com.max.tang.demokiller.utils.log.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.wanjian.cockroach.Cockroach;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
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

        Cockroach.install(new Cockroach.ExceptionHandler() {

            // handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException

            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                //开发时使用Cockroach可能不容易发现bug，所以建议开发阶段在handlerException中用Toast谈个提示框，
                Observable.just(throwable)
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<Throwable, Throwable>() {
                        @Override public Throwable call(Throwable throwable) {
                            /**
                             * 如果有,耗时操作可以放在这里
                             */
                            return throwable;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Throwable>() {
                        @Override public void call(Throwable throwable) {
                            try {
                                //建议使用下面方式在控制台打印异常，这样就可以在Error级别看到红色log
                                Log.e("AndroidRuntime","--->CockroachException:"+thread+"<---",throwable);
                                Toast.makeText(MyApplication.this, "Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_SHORT).show();
                                //                        throw new RuntimeException("..."+(i++));
                            } catch (Throwable e) {
                                Log.e("AndroidRuntime","--->CockroachException:"+thread+"<---",e);
                            }
                        }
                    });
            }
        });
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
