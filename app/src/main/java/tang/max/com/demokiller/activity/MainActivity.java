package tang.max.com.demokiller.activity;

import android.os.Bundle;

import tang.max.com.demokiller.R;
import tang.max.com.demokiller.utils.log.Logger;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.d("onCreate");
    }
}
