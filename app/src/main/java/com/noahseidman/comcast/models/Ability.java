package com.noahseidman.comcast.models;

import java.io.Serializable;

public class Ability implements Serializable {

    private AbilityDetails ability;

    public Ability(AbilityDetails ability) {
        this.ability = ability;
    }

    public AbilityDetails getAbility() {
        return ability;
    }
}
