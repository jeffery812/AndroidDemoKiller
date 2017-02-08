package com.max.tang.demokiller.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.utils.SortAlgo;
import com.max.tang.demokiller.utils.log.Logger;
import com.max.tang.demokiller.view.HistogramView;
import com.max.tang.demokiller.view.SortView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class SortActivity extends BaseActivity implements SortView {
    private static final String TAG = "sort";
    @BindView(R.id.histogram_view)
    HistogramView mHistogramView;
    List<Integer> mData;
    SortAlgo mSortAlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        ButterKnife.bind(this);
        Timber.tag("Shanghai");
        mData = new ArrayList<>();

        Intent intent = getIntent();
        Uri uri = intent.getData();
        Logger.d("data in intent: " + uri.toString());

        mSortAlog = new SortAlgo();
        reset();
    }


    private void reset() {
        Timber.w("dataReset: -_-!!!");
        Logger.w("dataReset: -_-!!!");
        mData.clear();
        for (int i = 0; i < 25; i++) {
            mData.add((int)(Math.random() * 100));
        }
        mHistogramView.setData(mData);
    }
    @OnClick(R.id.reset)
    public void dataReset(View view) {
        reset();
    }

    @OnClick(R.id.bubble_sort)
    public void bubbleSort(View v){
        mSortAlog.bubbleSort(new WeakReference<SortView>(this), mData);
    }

    @Override public void updateUI(List data) {
        mHistogramView.setData(mData);
    }

    @Override public void finish(String text) {
        Toast.makeText(this, text + " finished", Toast.LENGTH_SHORT).show();
    }

    @Override protected void onStop() {
        mSortAlog.cancel();
        super.onStop();
    }
}
