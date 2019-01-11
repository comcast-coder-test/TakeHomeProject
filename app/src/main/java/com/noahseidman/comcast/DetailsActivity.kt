package com.noahseidman.comcast

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.noahseidman.comcast.adapter.MultiTypeDataBoundAdapter
import com.noahseidman.comcast.databinding.ActivityDetailsBinding
import com.noahseidman.comcast.interfaces.AbilityCallback
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    lateinit var pokemonViewModel: PokemonViewModel
    lateinit var activityDetailsBinding : ActivityDetailsBinding

    private val abilityCallback: AbilityCallback = AbilityCallback {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonViewModel = intent.extras.getSerializable(PokemonViewModel.SERIALIZABLE) as PokemonViewModel
        activityDetailsBinding = DataBindingUtil.setContentView<ActivityDetailsBinding>(this, R.layout.activity_details)
        activityDetailsBinding.data = pokemonViewModel
        setTitle(pokemonViewModel.name)
        abilities_recycler.layoutManager = LinearLayoutManager(this)
        val adapter = MultiTypeDataBoundAdapter(abilityCallback)
        abilities_recycler.adapter = adapter
        for (abilityDetails in pokemonViewModel.abilities) {
            adapter.addItem(abilityDetails.ability)
        }
    }
}