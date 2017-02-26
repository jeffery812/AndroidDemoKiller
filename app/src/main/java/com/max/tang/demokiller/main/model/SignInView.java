package com.max.tang.demokiller.main.model;

import android.support.annotation.NonNull;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

/**
 * Created by zhihuitang on 2017-02-11.
 */

public interface SignInView extends OnConnectionFailedListener {
    @Override void onConnectionFailed(@NonNull ConnectionResult connectionResult);

    void signInSucceed(GoogleSignInAccount account);

    void signInFailed();

    void signedOut();
}
