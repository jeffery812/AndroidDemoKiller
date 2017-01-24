package com.max.tang.demokiller.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import com.max.tang.demokiller.utils.log.Logger;

/**
 * Created by zhihuitang on 2017-01-17.
 */

public class ConnectionChangeReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

        NetworkInfo mobNetInfo = connectivityManager.getActiveNetworkInfo();
        //NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE );
        String text = "";
        if ( activeNetInfo != null ) {
            text = "Active Network Type : " + activeNetInfo.getTypeName();
        } if( mobNetInfo != null ) {
            text = "Mobile Network Type : " + mobNetInfo.getTypeName();
        }
        Logger.d("Network info: " + text);
        Toast.makeText( context, text, Toast.LENGTH_SHORT ).show();

    }
}
