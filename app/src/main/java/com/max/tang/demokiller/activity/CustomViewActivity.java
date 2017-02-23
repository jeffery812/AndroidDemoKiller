package com.max.tang.demokiller.activity;

import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.mygithub.view.GaugeView;
import com.max.tang.demokiller.view.CircularIndicatorView;

public class CustomViewActivity extends BaseActivity {
    @BindView(R.id.gaugeView) GaugeView gaugeView;
    @BindView(R.id.circularView) CircularIndicatorView circularView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        ButterKnife.bind(this);

        gaugeView.setScore(80);
        circularView.setValue(80);
        //circularView.setPercentage(80);

    }
}
