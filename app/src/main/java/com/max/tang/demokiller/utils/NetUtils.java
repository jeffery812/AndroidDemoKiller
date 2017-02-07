package com.max.tang.demokiller.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

/**
 * Created by zhihuitang on 2017-01-30.
 */

public class NetUtils {

    @SuppressWarnings("deprecation")
    public static boolean isAirplaneModeOn(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }
    }
}
