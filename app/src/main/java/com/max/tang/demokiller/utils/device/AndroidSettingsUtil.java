package com.max.tang.demokiller.utils.device;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.max.tang.demokiller.utils.log.Logger;
import java.util.List;

/**
 * Created by zhihuitang on 2016-11-21.
 */

public class AndroidSettingsUtil {

    public static Boolean areSettingsAvailable(Context context, Intent intent) {
        List<ResolveInfo>
            activities = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return activities != null && activities.size() > 0;
    }

    private static void openSetting(final Activity activity, final String setting, int requestCode) {

        Intent intent = new Intent(setting, null);

        /**
         * http://stackoverflow.com/questions/25308156/intent-settings-action-device-info-settings-is-not-working-on-4-1-2-and-4-2-andr
         * For android 4.1.1 and android 4.1.2 we show General Settings page
         */

        final boolean isAndroidJellyBean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
            && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT;

        if (isAndroidJellyBean || !areSettingsAvailable(activity, intent)) {
            intent = new Intent(Settings.ACTION_SETTINGS);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public static void openLocationSourceSettings(final Activity activity, final int requestCode) {
        openSetting(activity, Settings.ACTION_LOCATION_SOURCE_SETTINGS, requestCode);
    }

    public static void openPrivacySettings(final Activity activity, final int requestCode) {
        openSetting(activity, Settings.ACTION_PRIVACY_SETTINGS, requestCode);
    }

    public static String getImei(Context context) {
        String uid = "";
        try {
            TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            uid = tManager.getDeviceId();
        } catch (Exception e) {
            // device has no telephony support
            Logger.e(e);
        }
        return uid;
    }

}
