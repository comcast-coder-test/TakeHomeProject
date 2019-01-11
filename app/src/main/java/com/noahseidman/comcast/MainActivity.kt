package com.noahseidman.comcast

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.ImageView
import com.noahseidman.comcast.Networking.retrofit
import com.noahseidman.comcast.adapter.MultiTypeDataBoundAdapter
import com.noahseidman.comcast.interfaces.IPokemon
import com.noahseidman.comcast.interfaces.PokemonCallback
import com.noahseidman.comcast.models.Pokemons
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    var pokemonsData: Pokemons? = null

    private val previousCallback: View.OnClickListener = View.OnClickListener {
        if (pokemonsData!!.previous != null) {
            //There is no example of what a previous url looks like to account for the Retrofit base path
            loadPokemons(pokemonsData!!.previous);
        }
    }
    private val nextCallback: View.OnClickListener = View.OnClickListener {
        if (pokemonsData!!.next != null) {
            //There is no example of what a next url looks like to account for the Retrofit base path
            loadPokemons(pokemonsData!!.next);
        }
    }

    private val pokemonCallback: PokemonCallback = PokemonCallback { view, pokemonViewModel ->
        val pokemonImage = view.findViewById<ImageView>(R.id.image)
        startActivity(intent)

        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(PokemonViewModel.SERIALIZABLE, pokemonViewModel)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pokemonImage as View, "pokemon_image")
        startActivity(intent, options.toBundle())
    }

    val adapter: MultiTypeDataBoundAdapter =
        MultiTypeDataBoundAdapter(pokemonCallback)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        previous.setOnClickListener(previousCallback)
        next.setOnClickListener(nextCallback)
        recycler_view.itemAnimator = null
        recycler_view.layoutManager = GridLayoutManager(this, 3)
        recycler_view.adapter = adapter

        val pokemonApi = retrofit.create(IPokemon::class.java)
        val pokemons = pokemonApi.pokemons

        pokemons.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Response<Pokemons>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(pokemons: Response<Pokemons>) {
                    pokemonsData = pokemons.body();
                    for (pokemon in pokemons.body()?.results!!) {
                        adapter.addItem(PokemonViewModel(pokemon))
                    }

                }

                override fun onError(e: Throwable) {
                    e.printStackTrace();
                }
            })
    }

    private fun loadPokemons(url: String) {
        adapter.clear()
        val pokemonApi = retrofit.create(IPokemon::class.java)
        val pokemons = pokemonApi.getPokemons(url)
        pokemons.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Response<Pokemons>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(pokemons: Response<Pokemons>) {
                    pokemonsData = pokemons.body();
                    for (pokemon in pokemons.body()?.results!!) {
                        adapter.addItem(PokemonViewModel(pokemon))
                    }

                }

                override fun onError(e: Throwable) {
                    e.printStackTrace();
                }
            })
    }
}
