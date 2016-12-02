package com.max.tang.demokiller.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.max.tang.demokiller.R;
import rx.functions.Action1;

public class RxBindingActivity extends BaseActivity {

    //@BindView(R.id.rxbinding_t_toolbar) Toolbar mTToolbar;
    @BindView(R.id.rxbinding_et_usual_approach) EditText mEtUsualApproach;
    @BindView(R.id.rxbinding_et_reactive_approach) EditText mEtReactiveApproach;
    @BindView(R.id.rxbinding_tv_show) TextView mTvShow;
    //@BindView(R.id.rxbinding_fab_fab) FloatingActionButton mFabFab;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_binding);
        ButterKnife.bind(this);

        initEditText();
    }

    private void initEditText() {
        // 正常方式
        mEtUsualApproach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTvShow.setText(s);
            }

            @Override public void afterTextChanged(Editable s) {

            }

        });

        // Rx方式
        //RxTextView.textChanges(mEtReactiveApproach).subscribe(mTvShow::setText);
        RxTextView.textChanges(mEtReactiveApproach).subscribe(new Action1<CharSequence>() {
            @Override public void call(CharSequence charSequence) {
                mTvShow.setText(charSequence);
            }
        });
    }
}
