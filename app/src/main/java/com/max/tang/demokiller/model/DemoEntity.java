package com.max.tang.demokiller.model;

import android.view.View;

import com.max.tang.demokiller.utils.log.Logger;

/**
 * Created by zhihuitang on 2016-11-01.
 */

public class DemoEntity {
    private String mDescription;
    private Class mClassName;

    public DemoEntity(String description, Class aClass) {
        mDescription = description;
        mClassName = aClass;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Class getClassName() {
        return mClassName;
    }

    public void setClassName(Class className) {
        mClassName = className;
    }

    public void onClicked(View view){
        Logger.d("open demo here");
    }
}
