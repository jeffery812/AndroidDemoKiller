package com.max.tang.demokiller.RxBinding;

/**
 * Created by zhihuitang on 2017-03-04.
 */

public class DeviceInfo {
    private String name;
    private String value;

    public DeviceInfo(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
