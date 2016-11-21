package com.max.tang.demokiller.utils.device;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created by zhihuitang on 2016-11-21.
 */

public class AndroidSettingsUtil {
    private static void openSetting(final Activity activity, final String url, int requestCode) {
        Intent callGPSSettingIntent = new Intent(url);
        activity.startActivityForResult(callGPSSettingIntent, requestCode);
    }

    public static void openLocaionSourceSettings(final Activity activity, final int requestCode) {
        openSetting(activity, Settings.ACTION_LOCATION_SOURCE_SETTINGS, requestCode);
    }

    public static void openPrivacySettings(final Activity activity, final int requestCode) {
        openSetting(activity, Settings.ACTION_PRIVACY_SETTINGS, requestCode);
    }
}
