package com.max.tang.demokiller.utils.device;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by zhihuitang on 2016-11-21.
 */

public class AndroidSettingsUtil {
    private static void openSetting(final Activity activity, final String url, int retCode) {
        Intent callGPSSettingIntent = new Intent(url);
        activity.startActivityForResult(callGPSSettingIntent, retCode);
    }

    public static void openNetwork(final Activity activity, final int retCode) {
        openSetting(activity, android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS, retCode);
    }
}
