package tang.max.com.demokiller.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tang.max.com.demokiller.R;
import tang.max.com.demokiller.adapter.DemoAdapter;
import tang.max.com.demokiller.itemanimator.ItemAnimatorFactory;
import tang.max.com.demokiller.model.DemoEntity;
import tang.max.com.demokiller.utils.DataLoader;
import tang.max.com.demokiller.utils.log.Logger;

public class MainActivity extends BaseActivity {

    @BindView(R.id.recycler_view_main)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Logger.d("onCreate");

        List<DemoEntity> demoEntities = DataLoader.getInstance().getTestList();
        DemoAdapter demoAdapter = new DemoAdapter(this, demoEntities);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(ItemAnimatorFactory.slidein()); //设置Iem动画

        mRecyclerView.setAdapter(demoAdapter);
    }
}
