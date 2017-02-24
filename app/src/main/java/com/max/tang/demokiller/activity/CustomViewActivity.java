package com.max.tang.demokiller.activity;

import android.os.Bundle;
import android.widget.SeekBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.mygithub.view.GaugeView;
import com.max.tang.demokiller.view.CircleGradientProgressbar;
import com.max.tang.demokiller.view.CircularIndicatorView;

public class CustomViewActivity extends BaseActivity {
    @BindView(R.id.gaugeView) GaugeView gaugeView;
    @BindView(R.id.circularView) CircularIndicatorView circularView;
    @BindView(R.id.circle_gradient_bar) CircleGradientProgressbar circleGradientProgressbar;
    @BindView(R.id.seek_bar) SeekBar seekBar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        ButterKnife.bind(this);

        gaugeView.setScore(80);
        circularView.setValue(80);
        final float segments[] = {40, 60, 80};
        circularView.setSegments(segments);
        circleGradientProgressbar.setProgress(80);
        //circularView.setPercentage(80);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                circularView.setValue(progress);
                circleGradientProgressbar.setProgress(progress);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
