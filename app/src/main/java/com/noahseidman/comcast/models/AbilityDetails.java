package com.noahseidman.comcast.models;

import com.noahseidman.comcast.R;
import com.noahseidman.comcast.adapter.LayoutBinding;

import java.io.Serializable;

public class AbilityDetails implements Serializable, LayoutBinding {

    private String name;
    private String url;

    public AbilityDetails(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int getLayoutId() {
        return R.layout.ability;
    }
}
