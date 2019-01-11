package com.noahseidman.comcast.interfaces;

import android.view.View;
import com.noahseidman.comcast.PokemonViewModel;
import com.noahseidman.comcast.adapter.MultiTypeDataBoundAdapter;

public interface PokemonCallback extends MultiTypeDataBoundAdapter.ActionCallback {

    void onPokemonClick(View view, PokemonViewModel pokemonViewModel);
}
