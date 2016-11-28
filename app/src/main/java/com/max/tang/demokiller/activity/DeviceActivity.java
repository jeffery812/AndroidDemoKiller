package com.max.tang.demokiller.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.utils.device.AndroidSettingsUtil;
import com.max.tang.demokiller.utils.log.Logger;
import java.util.Locale;

public class DeviceActivity extends AppCompatActivity {
    final String TAG = "device";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        permissionToDrawOverlays();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.d(TAG, "onActivityResult: " + requestCode );

        if (requestCode == PERM_REQUEST_CODE_DRAW_OVERLAYS) {
            if (android.os.Build.VERSION.SDK_INT >= 23) {   //Android M Or Over
                if (!Settings.canDrawOverlays(this)) {
                    // ADD UI FOR USER TO KNOW THAT UI for SYSTEM_ALERT_WINDOW permission was not granted earlier...
                    Logger.d(TAG, "this shit UI: " );
                }
            }
        }
    }

    public void openLocationSetting(View view) {
        AndroidSettingsUtil.openLocationSourceSettings(this, 9);
    }


    public void openPrivacySettings(View view) {
        AndroidSettingsUtil.openPrivacySettings(this, 91);
    }

    public void getLanguageCode(View view) {
        String lan = Locale.getDefault().getDisplayLanguage();
        String lanCode = Locale.getDefault().getLanguage();
        Logger.d(TAG, "getLanguage: " + lanCode);
        Logger.d(TAG, "getDisplayLanguage: " + lan);
    }

    public final static int PERM_REQUEST_CODE_DRAW_OVERLAYS = 1234;
    /**
     * Permission to draw Overlays/On Other Apps, related to 'android.permission.SYSTEM_ALERT_WINDOW' in Manifest
     * Resolves issue of popup in Android M and above "Screen overlay detected- To change this permission setting you first have to turn off the screen overlay from Settings > Apps"
     * If app has not been granted permission to draw on the screen, create an Intent &
     * set its destination to Settings.ACTION_MANAGE_OVERLAY_PERMISSION &
     * add a URI in the form of "package:<package name>" to send users directly to your app's page.
     * Note: Alternative Ignore URI to send user to the full list of apps.
     */
    public void permissionToDrawOverlays() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {   //Android M Or Over
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, PERM_REQUEST_CODE_DRAW_OVERLAYS);
            }
        }
    }

}
