package com.max.tang.demokiller.utils;

import android.app.Activity;
import com.max.tang.demokiller.utils.log.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhihuitang on 2016-12-27.
 */

public class ActivityManager {
    List<Activity> activities = new ArrayList<Activity>();
    private static ActivityManager instance = new ActivityManager();
    private ActivityManager(){

    }

    static public ActivityManager getInstance() {
        if( instance == null ) {
            instance = new ActivityManager();
        }

        return instance;
    }

    public void addActivity(Activity activity) {
        Logger.d("shanghai", "ActivityManager add: " + activity.getLocalClassName());
        activities.add(activity);
    }

    public void removeActivity(Activity activity){
        Logger.d("shanghai", "ActivityManager remove: " + activity.getLocalClassName());
        activities.remove(activity);
    }

    public Activity getTopActivity(){
        if( activities.isEmpty() ){
            return null;
        }
        return activities.get(activities.size()-1);
    }

}
