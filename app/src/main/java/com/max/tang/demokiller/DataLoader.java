package com.max.tang.demokiller;

import com.max.tang.demokiller.RxBinding.RxBindingActivity;
import com.max.tang.demokiller.activity.CoordinatorLayoutActivity;
import com.max.tang.demokiller.activity.CustomViewActivity;
import com.max.tang.demokiller.activity.DemoDataBindingActivity;
import com.max.tang.demokiller.activity.DemoNotification;
import com.max.tang.demokiller.activity.DeviceActivity;
import com.max.tang.demokiller.activity.ListViewDemo;
import com.max.tang.demokiller.activity.LoginActivity;
import com.max.tang.demokiller.activity.MessActivity;
import com.max.tang.demokiller.activity.NetworkMonitorMainActivity;
import com.max.tang.demokiller.activity.SortActivity;
import com.max.tang.demokiller.activity.WatchActivity;
import com.max.tang.demokiller.animation.AnimationActivity;
import com.max.tang.demokiller.model.DemoEntity;
import com.max.tang.demokiller.mygithub.activity.MyGithubActivity;
import java.util.ArrayList;
import java.util.List;

/**
 */

public class DataLoader {
    private static DataLoader mLoder;

    public static DataLoader getInstance() {
        if (mLoder == null) {
            mLoder = new DataLoader();
        }
        return mLoder;
    }

    //获取本地测试的Activity列表
    public List<DemoEntity> getTestList() {
        List<DemoEntity> list = new ArrayList<>();
        list.add(new DemoEntity("1 My Github", MyGithubActivity.class));
        list.add(new DemoEntity("2 Sort Demo", SortActivity.class));
        list.add(new DemoEntity("3 Login Demo", LoginActivity.class));
        list.add(new DemoEntity("4 Watch Activity", WatchActivity.class));
        list.add(new DemoEntity("5 Data Binding", DemoDataBindingActivity.class));
        list.add(new DemoEntity("6 Device demo", DeviceActivity.class));
        list.add(new DemoEntity("7 Custom Views", CustomViewActivity.class));
        list.add(new DemoEntity("8 RxBinding demo", RxBindingActivity.class));
        list.add(new DemoEntity("9 Notification demo", DemoNotification.class));
        list.add(new DemoEntity("10 CoordinatorLayout demo", CoordinatorLayoutActivity.class));
        list.add(new DemoEntity("11 ListView demo", ListViewDemo.class));
        list.add(new DemoEntity("12 大杂烩 demo", MessActivity.class));
        list.add(new DemoEntity("13 animation demo", AnimationActivity.class));
        list.add(new DemoEntity("14 Network Monitor", NetworkMonitorMainActivity.class));
        return list;
    }
}
