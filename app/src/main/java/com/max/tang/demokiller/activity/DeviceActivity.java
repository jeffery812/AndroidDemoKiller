package com.max.tang.demokiller.activity;

import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.utils.device.AndroidSettingsUtil;
import com.max.tang.demokiller.utils.log.Logger;
import java.util.Locale;

public class DeviceActivity extends BaseActivity {
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
        Toast.makeText(this, String.format("language: %s, code: %s", lan, lanCode), Toast.LENGTH_SHORT).show();
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

    public void enableLocationService(View view) {

        final int REQUEST_CHECK_SETTINGS = 101;
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
            .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult>
            result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Logger.i(TAG, "All location settings are satisfied.");
                        Toast.makeText(DeviceActivity.this, "All location settings are satisfied.", Toast.LENGTH_SHORT).show();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Logger.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(DeviceActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Logger.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Logger.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    public void gotoDeviceSettings(View view) {
        startIntentForSettings(this, Settings.ACTION_DEVICE_INFO_SETTINGS);
    }
    public void checkSoftwareVersion(View view) {
        startIntentForSettings(this, "android.settings.SYSTEM_UPDATE_SETTINGS");
    }
}
