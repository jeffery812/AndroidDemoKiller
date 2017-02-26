package com.max.tang.demokiller.main.model;

import android.content.Intent;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;

/**
 * Created by zhihuitang on 2017-02-11.
 */

public interface SignIn {
    void signIn();

    void signOut();

    void signInSilently();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onConnectionFailed(@NonNull ConnectionResult connectionResult);
}
