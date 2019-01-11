package com.noahseidman.comcast.interfaces;

import com.noahseidman.comcast.models.PokemonDetails;
import com.noahseidman.comcast.models.Pokemons;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IPokemon {

    @GET("pokemon")
    Single<Response<Pokemons>> getPokemons();

    @GET("{url}")
    Single<Response<Pokemons>> getPokemons(@Path("url") String url);

    @GET("pokemon/{id}")
    Single<Response<PokemonDetails>> getPokemonDetails(@Path("id") String groupId);
}
