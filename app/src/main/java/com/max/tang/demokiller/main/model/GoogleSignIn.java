package com.max.tang.demokiller.main.model;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.max.tang.demokiller.utils.log.Logger;

/**
 * Created by zhihuitang on 2017-02-11.
 */

public class GoogleSignIn implements SignIn {

    private final String TAG = "google-sign-in";
    private final int RC_SIGN_IN = 101;

    private GoogleApiClient mGoogleApiClient;
    private SignInView mSignView;
    private AppCompatActivity activity;

    public GoogleSignIn(AppCompatActivity activity, SignInView view) {
        this.activity = activity;
        this.mSignView = view;

        initSignIn();
    }

    private void initSignIn() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso =
            new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient =
            new GoogleApiClient.Builder(activity).enableAutoManage(activity /* FragmentActivity */,
                mSignView /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient)
            .setResultCallback(new ResultCallback<Status>() {
                @Override public void onResult(Status status) {
                    // ...
                    if (status.isSuccess()) {
                        mSignView.signedOut();
                    }
                }
            });
    }

    @Override public void signInSilently() {
        OptionalPendingResult<GoogleSignInResult> pendingResult =
            Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (pendingResult.isDone()) {
            // There's immediate result available.
            //updateButtonsAndStatusFromSignInResult(pendingResult.get());
            handleSignInResult(pendingResult.get());
        } else {
            // There's no immediate result ready, displays some progress indicator and waits for the
            // async callback.
            //showProgressIndicator();
            pendingResult.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override public void onResult(@NonNull GoogleSignInResult result) {
                    //updateButtonsAndStatusFromSignInResult(result);
                    //hideProgressIndicator();
                }
            });
        }
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Logger.e(TAG, "Google onConnectionFailed: " + connectionResult.getErrorMessage());
    }

    private void handleSignInResult(GoogleSignInResult result) {
        //Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            Logger.d(TAG, "Google sign-in succeed");
            GoogleSignInAccount acct = result.getSignInAccount();
            mSignView.signInSucceed(acct);
        } else {
            Logger.d(TAG, "Google sign-in failed: " + result.getStatus());
            // Signed out, show unauthenticated UI.
            mSignView.signInFailed();
        }
    }
}
