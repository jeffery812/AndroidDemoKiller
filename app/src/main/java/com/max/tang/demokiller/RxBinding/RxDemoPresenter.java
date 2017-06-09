package com.max.tang.demokiller.RxBinding;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import com.max.tang.demokiller.BuildConfig;
import com.max.tang.demokiller.utils.device.AndroidSettingsUtil;
import com.max.tang.demokiller.utils.log.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by zhihuitang on 2017-05-10.
 */

public class RxDemoPresenter implements DeviceContract.Presenter {
    DeviceContract.View view;
    Context context;

    public RxDemoPresenter(DeviceContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override public void start() {
        this.view.setPresenter(this);
    }

    @Override public List<DeviceInfo> getData() {

        final List<DeviceInfo> data = new ArrayList<>();

        data.add(new DeviceInfo("Android Version", Build.VERSION.RELEASE));
        data.add(new DeviceInfo("Brand", Build.BRAND));
        data.add(new DeviceInfo("Manufacture", Build.MANUFACTURER));
        data.add(new DeviceInfo("Model", Build.MODEL));
        data.add(new DeviceInfo("BuildNumber", Build.DISPLAY));
        data.add(new DeviceInfo("AppVersion1", retrieveAppVersion(context)));
        data.add(new DeviceInfo("AppVersion2", BuildConfig.VERSION_NAME));
        data.add(new DeviceInfo("Internal Available Memory", AndroidSettingsUtil.getAvailableInternalMemorySize()));
        data.add(new DeviceInfo("Internal Total Memory", AndroidSettingsUtil.getTotalInternalMemorySize()));
        data.add(new DeviceInfo("External Available Memory", AndroidSettingsUtil.getAvailableExternalMemorySize()));
        data.add(new DeviceInfo("External Total Memory", AndroidSettingsUtil.getTotalExternalMemorySize()));
        data.add(new DeviceInfo("Phone Number", getPhoneNumber(context)));
        data.add(new DeviceInfo("Language",
            Locale.getDefault().getDisplayLanguage() + ", " + Locale.getDefault().getLanguage()));
        try {
            int brightness = Settings.System.getInt(context.getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
            data.add(new DeviceInfo("Brightness", String.valueOf(brightness)));
        }catch (Exception e){
            e.printStackTrace();
        }

        return data;
    }


    private String retrieveAppVersion(final Context context) {
        final PackageManager manager = context.getPackageManager();
        try {
            final PackageInfo info = manager.getPackageInfo(
                context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "N/A";
        }
    }

    public static String getPhoneNumber(Context context) {
        String number = "";
        try {
            int permissionCheck = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_PHONE_STATE);
            if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
                TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                number = tMgr.getLine1Number();
            }
        }catch (Exception e) {
            Logger.e("Get phone number error", e);
        }
        return number;
    }
}
