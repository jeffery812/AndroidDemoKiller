package com.max.tang.demokiller.model;

import java.util.List;

/**
 * Created by zhihuitang on 2017-02-14.
 */

public class TestBean {
    private String name;
    private String url;
    private List<NestBean> nest;

    public TestBean(String name, String url, List<NestBean> nest) {
        this.name = name;
        this.url = url;
        this.nest = nest;
    }

    public List<NestBean> getNest() {
        return nest;
    }

    public void setNest(List<NestBean> nest) {
        this.nest = nest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TestBean(String name, String url) {
        this.name = name;
        this.url = url;
    }
}