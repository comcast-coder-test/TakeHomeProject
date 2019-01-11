package com.noahseidman.comcast;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;
import com.noahseidman.comcast.adapter.DataBoundViewHolder;
import com.noahseidman.comcast.adapter.DynamicBinding;
import com.noahseidman.comcast.adapter.LayoutBinding;
import com.noahseidman.comcast.databinding.PokemonBinding;
import com.noahseidman.comcast.interfaces.IPokemon;
import com.noahseidman.comcast.models.*;
import com.squareup.picasso.Picasso;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;

public class PokemonViewModel extends BaseObservable implements LayoutBinding, DynamicBinding, Serializable {

    public static final String SERIALIZABLE = "PokemonViewModel:SERIALIZABLE";

    private String name;
    private String url;
    private String imageUrl;
    private String types;
    private String speed;
    private String attack;
    private String defense;
    private LinkedList<Ability> abilities;

    public PokemonViewModel(Pokemon pokemon) {
        this.name = pokemon.getName();
        this.url = pokemon.getUrl();
    }

    @Bindable
    public String getImage() {
        if (TextUtils.isEmpty(imageUrl)) {
            return "";
        }
        return imageUrl;
    }

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public String getTypes() {
        return types;
    }

    @Bindable
    public String getSpeed() {
        StringBuilder builder = new StringBuilder();
        builder.append(Application.context.getString(R.string.speed));
        builder.append(": ");
        builder.append(speed);
        return builder.toString();
    }

    @Bindable
    public String getDefense() {
        StringBuilder builder = new StringBuilder();
        builder.append(Application.context.getString(R.string.defense));
        builder.append(": ");
        builder.append(defense);
        return builder.toString();
    }

    @Bindable
    public String getAttack() {
        StringBuilder builder = new StringBuilder();
        builder.append(Application.context.getString(R.string.attack));
        builder.append(": ");
        builder.append(attack);
        return builder.toString();
    }

    public LinkedList<Ability> getAbilities() {
        return abilities;
    }

    @Override
    public int getLayoutId() {
        return R.layout.pokemon;
    }

    @BindingAdapter("image")
    public static void setImage(ImageView imageView, String image) {
        if (TextUtils.isEmpty(image)) {
            return;
        }
        Picasso.get().load(image).into(imageView);
    }

    @Override
    public void bind(DataBoundViewHolder holder) {
        holder.binding.executePendingBindings();
        if (!TextUtils.isEmpty(imageUrl)) {
            return;
        }
        PokemonBinding binding = (PokemonBinding) holder.binding;
        binding.image.setImageBitmap(null);
        binding.typesText.setText("");
        IPokemon pokemonApi = Networking.retrofit.create(IPokemon.class);

        Uri uri = Uri.parse(url);
        Single<Response<PokemonDetails>> pokemons = pokemonApi.getPokemonDetails(uri.getLastPathSegment());

        pokemons.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<PokemonDetails>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<PokemonDetails> pokemonDetailsResponse) {
                        populateDetails(pokemonDetailsResponse.body());
                        notifyPropertyChanged(BR.types);
                        notifyPropertyChanged(BR.image);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    public void populateDetails(PokemonDetails details) {
        imageUrl = details.getSprites().getFront_default();
        StringBuilder builder = new StringBuilder();
        for (Type type : details.getTypes()) {
            builder.append(type.getType().getName()).append(" ");
        }
        types = builder.toString();
        for (Stat stat : details.getStats()) {
            if ("speed".equals(stat.getStat().getName())) {
                speed = Integer.toString(stat.getBase_stat());
            }
            if ("attack".equals(stat.getStat().getName())) {
                attack = Integer.toString(stat.getBase_stat());
            }
            if ("defense".equals(stat.getStat().getName())) {
                defense = Integer.toString(stat.getBase_stat());
            }
        }
        abilities = new LinkedList<>(Arrays.asList(details.getAbilities()));
    }
}
