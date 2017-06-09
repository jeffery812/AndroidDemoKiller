package com.max.tang.demokiller.RxBinding;

import com.max.tang.demokiller.BasePresenter;
import com.max.tang.demokiller.BaseView;
import java.util.List;

/**
 * Created by zhihuitang on 2017-05-10.
 */

public interface DeviceContract {
    interface Presenter extends BasePresenter {

        List<DeviceInfo> getData();
    }

    interface View extends BaseView<Presenter> {

    }
}
