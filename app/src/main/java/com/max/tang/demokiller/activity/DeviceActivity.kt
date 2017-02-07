package com.max.tang.demokiller.activity

import android.content.ComponentName
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.max.tang.demokiller.R
import com.max.tang.demokiller.utils.device.AndroidSettingsUtil
import com.max.tang.demokiller.utils.log.Logger
import kotlinx.android.synthetic.main.activity_device.*
import java.util.*

class DeviceActivity : BaseActivity() {
  internal val TAG = "device"
  @BindView(R.id.tv_model) internal var tvModel: TextView? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_device)
    ButterKnife.bind(this)
    //tvModel!!.text = Build.MODEL
    permissionToDrawOverlays()

    tv_model.text = "this device is ${Build.MODEL}"
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    super.onActivityResult(requestCode, resultCode, data)
    Logger.d(TAG, "onActivityResult: " + requestCode)

    if (requestCode == PERM_REQUEST_CODE_DRAW_OVERLAYS) {
      if (android.os.Build.VERSION.SDK_INT >= 23) {   //Android M Or Over
        if (!Settings.canDrawOverlays(this)) {
          // ADD UI FOR USER TO KNOW THAT UI for SYSTEM_ALERT_WINDOW permission was not granted earlier...
          Logger.d(TAG, "this shit UI: ")
        }
      }
    }
  }

  fun openLocationSetting(view: View) {
    AndroidSettingsUtil.openLocationSourceSettings(this, 9)
  }

  fun changeAppIcon(view: View) {
    val pm = packageManager
    val mainActivityName = "com.max.tang.demokiller.activity.NavigationActivity"
    pm.setComponentEnabledSetting(ComponentName(this, mainActivityName),
        PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)
    pm.setComponentEnabledSetting(ComponentName(this, "com.max.tang.demokiller.MainAliasActivity"),
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)

  }

  fun openPrivacySettings(view: View) {
    AndroidSettingsUtil.openPrivacySettings(this, 91)
  }

  override fun onResume() {
    super.onResume()
    Logger.d(TAG, "hello Device demo in Kotlin")
  }

  fun getLanguageCode(view: View) {
    val lan = Locale.getDefault().displayLanguage
    val lanCode = Locale.getDefault().language
    Logger.d(TAG, "getLanguage: " + lanCode)
    Logger.d(TAG, "getDisplayLanguage: " + lan)
    Toast.makeText(this, String.format("language: %s, code: %s", lan, lanCode),
        Toast.LENGTH_SHORT).show()
  }

  /**
   * Permission to draw Overlays/On Other Apps, related to 'android.permission.SYSTEM_ALERT_WINDOW' in Manifest
   * Resolves issue of popup in Android M and above "Screen overlay detected- To change this permission setting you first have to turn off the screen overlay from Settings > Apps"
   * If app has not been granted permission to draw on the screen, create an Intent &
   * set its destination to Settings.ACTION_MANAGE_OVERLAY_PERMISSION &
   * add a URI in the form of "package:<package name>" to send users directly to your app's page.
   * Note: Alternative Ignore URI to send user to the full list of apps.
  </package> */
  fun permissionToDrawOverlays() {
    if (android.os.Build.VERSION.SDK_INT >= 23) {   //Android M Or Over
      if (!Settings.canDrawOverlays(this)) {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:" + packageName))
        startActivityForResult(intent, PERM_REQUEST_CODE_DRAW_OVERLAYS)
      }
    }
  }

  fun enableLocationService(view: View) {

    val REQUEST_CHECK_SETTINGS = 101
    val googleApiClient = GoogleApiClient.Builder(this)
        .addApi(LocationServices.API).build()
    googleApiClient.connect()

    val locationRequest = LocationRequest.create()
    locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    locationRequest.interval = 10000
    locationRequest.fastestInterval = (10000 / 2).toLong()

    val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
    builder.setAlwaysShow(true)

    val result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient,
        builder.build())
    result.setResultCallback { result ->
      val status = result.status
      when (status.statusCode) {
        LocationSettingsStatusCodes.SUCCESS -> {
          Logger.i(TAG, "All location settings are satisfied.")
          Toast.makeText(this@DeviceActivity, "All location settings are satisfied.",
              Toast.LENGTH_SHORT).show()
        }
        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
          Logger.i(TAG,
              "Location settings are not satisfied. Show the user a dialog to upgrade location settings ")

          try {
            // Show the dialog by calling startResolutionForResult(), and check the result
            // in onActivityResult().
            status.startResolutionForResult(this@DeviceActivity, REQUEST_CHECK_SETTINGS)
          } catch (e: IntentSender.SendIntentException) {
            Logger.i(TAG, "PendingIntent unable to execute request.")
          }

        }
        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> Logger.i(TAG,
            "Location settings are inadequate, and cannot be fixed here. Dialog not created.")
      }
    }
  }

  fun gotoDeviceSettings(view: View) {
    startIntentForSettings(this, Settings.ACTION_DEVICE_INFO_SETTINGS)
  }

  fun checkSoftwareVersion(view: View) {
    startIntentForSettings(this, "android.settings.SYSTEM_UPDATE_SETTINGS")
  }

  companion object {

    val PERM_REQUEST_CODE_DRAW_OVERLAYS = 1234
  }
}
