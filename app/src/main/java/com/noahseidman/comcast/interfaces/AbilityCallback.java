package com.noahseidman.comcast.interfaces;

import com.noahseidman.comcast.adapter.MultiTypeDataBoundAdapter;
import com.noahseidman.comcast.models.AbilityDetails;

public interface AbilityCallback extends MultiTypeDataBoundAdapter.ActionCallback {

    public void onAbilityClick(AbilityDetails abilityDetails);
}
