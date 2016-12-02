package com.max.tang.demokiller.activity;

import android.os.Bundle;
import android.view.View;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.utils.RxBus;
import com.max.tang.demokiller.utils.log.Logger;
import rx.functions.Action1;

public class RxActivity extends BaseActivity {
    private static final String TAG = "RxActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        RxBus.instanceOf().getEvents().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if( o instanceof String ){
                    Logger.d("got event: " + o);
                }
            }
        });
    }

    public void sendEvent(View view) {
        RxBus.instanceOf().postEvent("button clicked");
    }


}
