package com.max.tang.demokiller.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.utils.SortAlgo;
import com.max.tang.demokiller.view.HistogramView;
import com.max.tang.demokiller.view.SortView;
import java.util.ArrayList;
import java.util.List;

public class SortActivity extends BaseActivity implements SortView {
    private static final String TAG = "sort";
    @BindView(R.id.histogram_view)
    HistogramView mHistogramView;
    List<Integer> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        ButterKnife.bind(this);
        mData = new ArrayList<>();

        reset();
    }


    private void reset() {
        Log.d(TAG, "dataReset: -_-!!!");
        mData.clear();
        for (int i = 0; i < 25; i++) {
            mData.add((int) (Math.random() * 100));
        }
        mHistogramView.setData(mData);
    }
    @OnClick(R.id.reset)
    public void dataReset(View view) {
        reset();
    }

    @OnClick(R.id.bubble_sort)
    public void bubbleSort(View v){
        SortAlgo.bubbleSort(this, mData);
    }

    @Override public void updateUI(List data) {
        mHistogramView.setData(mData);
    }

    @Override public void finish(String text) {
        Toast.makeText(this, text + " finished", Toast.LENGTH_SHORT).show();
    }
}
