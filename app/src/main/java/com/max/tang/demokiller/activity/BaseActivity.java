package com.max.tang.demokiller.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import com.max.tang.demokiller.utils.ActivityManager;
import com.max.tang.demokiller.utils.log.Logger;
import java.util.List;

/**
 * Created by zhihuitang on 2016-11-01.
 */

public class BaseActivity extends AppCompatActivity {

    @Override protected void onStop() {
        Logger.i("shanghai", "remove activity: " + this.getLocalClassName());
        ActivityManager.getInstance().removeActivity(this);
        super.onStop();
    }

    @Override protected void onStart() {
        super.onStart();
        Logger.i("shanghai", "add activity: " + this.getLocalClassName());
        ActivityManager.getInstance().addActivity(this);
    }

    public void startIntentForSettings(Context context, String setting) {
        Intent intent = new Intent(setting, null);

        /**
         * http://stackoverflow.com/questions/25308156/intent-settings-action-device-info-settings-is-not-working-on-4-1-2-and-4-2-andr
         * For android 4.1.1 and android 4.1.2 we show General Settings page
         */

        final boolean isAndroidJellyBean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
            && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT;

        if (isAndroidJellyBean || !areSettingsAvailable(context, intent)) {
            intent = new Intent(Settings.ACTION_SETTINGS);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
    }



    public Boolean areSettingsAvailable(Context context, Intent intent) {
        List<ResolveInfo>
            activities = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return activities != null && !activities.isEmpty();
    }
}
