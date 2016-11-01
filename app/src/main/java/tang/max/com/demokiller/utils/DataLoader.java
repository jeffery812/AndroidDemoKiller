package tang.max.com.demokiller.utils;

import java.util.ArrayList;
import java.util.List;

import tang.max.com.demokiller.activity.LoginActivity;
import tang.max.com.demokiller.model.DemoEntity;

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
        list.add(new DemoEntity("Login Demo", LoginActivity.class));
        list.add(new DemoEntity("Login Demo", LoginActivity.class));
        list.add(new DemoEntity("Login Demo", LoginActivity.class));
        list.add(new DemoEntity("Login Demo", LoginActivity.class));
        list.add(new DemoEntity("Login Demo", LoginActivity.class));
        list.add(new DemoEntity("Login Demo", LoginActivity.class));
        list.add(new DemoEntity("Login Demo", LoginActivity.class));
        list.add(new DemoEntity("Login Demo", LoginActivity.class));
        return list;
    }
}
