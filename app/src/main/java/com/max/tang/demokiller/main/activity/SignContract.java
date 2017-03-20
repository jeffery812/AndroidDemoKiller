package com.max.tang.demokiller.main.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.max.tang.demokiller.BasePresenter;
import com.max.tang.demokiller.BaseView;

/**
 * Created by zhihuitang on 2017-03-20.
 */

public interface SignContract {

    interface View extends BaseView<Presenter>, GoogleApiClient.OnConnectionFailedListener {
        @Override void onConnectionFailed(@NonNull ConnectionResult connectionResult);
        
        void signInSucceed(GoogleSignInAccount account);

        void signInFailed();

        void signedOut();
    }

    interface Presenter extends BasePresenter {
        void signIn();

        void signOut();

        void signInSilently();

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void onConnectionFailed(@NonNull ConnectionResult connectionResult);
    }
}
