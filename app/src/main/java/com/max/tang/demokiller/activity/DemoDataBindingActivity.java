package com.max.tang.demokiller.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.max.tang.demokiller.R;
import com.max.tang.demokiller.databinding.ActivityBindingDataDemoBinding;
import com.max.tang.demokiller.helper.AppNavigator;
import com.max.tang.demokiller.helper.ToastPresenter;
import com.max.tang.demokiller.model.LoginViewModel;
import com.max.tang.demokiller.utils.log.Logger;

public class DemoDataBindingActivity extends BaseActivity {
    private ActivityBindingDataDemoBinding mBinding;
    private LoginViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_binding_data_demo);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_binding_data_demo);

        ToastPresenter toastPresenter = new ToastPresenter(getApplicationContext());
        AppNavigator navigator = new AppNavigator(this);
        mViewModel = new LoginViewModel(navigator, toastPresenter, getResources());
        mBinding.setData(mViewModel);
        mViewModel.username.set("tanghzihui");
        attachListeners();
    }


    private void attachListeners() {
        mBinding.existingOrNewUser.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                uiToModel();
                mViewModel.updateDependentViews();

                Logger.d("existing or new user clicked");
            }
        });
    }

    private void uiToModel() {
        mViewModel.isExistingUserChecked.set(mBinding.returningUserRb.isChecked());
        mViewModel.password.set(mBinding.password.getText().toString());
        mViewModel.username.set(mBinding.username.getText().toString());
    }

    private void ensureModelDataIsLodaded() {
        if (!mViewModel.isLoaded()) {
            mViewModel.loadAsync();
        }
    }

    public void logInClicked(View view) {
        uiToModel();
        mViewModel.logInClicked();
    }
}
