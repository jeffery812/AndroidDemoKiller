package com.max.tang.demokiller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.utils.device.AndroidSettingsUtil;
import com.max.tang.demokiller.utils.log.Logger;

public class DeviceActivity extends AppCompatActivity {
    final String TAG = "device";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.d(TAG, "onActivityResult: " + requestCode );
    }

    public void openLocationSetting(View view) {
        AndroidSettingsUtil.openLocaionSourceSettings(this, 9);
    }


    public void openPrivacySettings(View view) {
        AndroidSettingsUtil.openPrivacySettings(this, 91);
    }
}
