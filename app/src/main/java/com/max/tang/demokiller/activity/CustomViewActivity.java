package com.max.tang.demokiller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.mygithub.view.GaugeView;
import com.max.tang.demokiller.view.CircleGradientProgressbar;
import com.max.tang.demokiller.view.CircularIndicatorView;

public class CustomViewActivity extends BaseActivity {
    @BindView(R.id.gaugeView) GaugeView gaugeView;
    @BindView(R.id.circularView) CircularIndicatorView circularView;
    @BindView(R.id.edit_value) EditText editValue;
    @BindView(R.id.circle_gradient_bar) CircleGradientProgressbar circleGradientProgressbar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        ButterKnife.bind(this);

        gaugeView.setScore(80);
        circularView.setValue(80);
        final float segments[] = {10, 40, 90};
        circularView.setSegments(segments);
        circleGradientProgressbar.setProgress(80);
        //circularView.setPercentage(80);

    }

    public void setValue(View view) {
        final String text = editValue.getText().toString();
        final int value = text.isEmpty() ? 0 : Integer.valueOf(text);
        circularView.setValue(value);
        circleGradientProgressbar.setProgress(value);
    }
}
