package com.max.tang.demokiller.helper;

import android.content.Context;
import android.content.Intent;

import com.max.tang.demokiller.main.activity.NavigationActivity;


public class AppNavigator {
    private final Context mContext;

    public AppNavigator(Context context) {
        mContext = context;
    }

    public void gotoMainScreen() {
        mContext.startActivity(new Intent(mContext, NavigationActivity.class));
    }
}
