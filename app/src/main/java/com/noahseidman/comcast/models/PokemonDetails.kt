package com.noahseidman.comcast.models

data class PokemonDetails(val sprites: Sprites, val types: Array<Type?>, val stats: Array<Stat?>, val abilities: Array<Ability?>)