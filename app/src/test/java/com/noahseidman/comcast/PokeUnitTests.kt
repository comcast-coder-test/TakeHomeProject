package com.noahseidman.comcast

import com.noahseidman.comcast.models.*
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PokeUnitTests {

    @Test
    fun testPokemonViewModel() {
        val pokemon = Pokemon("test pokemon name", "test pokemon url")
        val pokemonViewModel = PokemonViewModel(pokemon);

        val sprites = Sprites("", "", "", "", "", "", "", "")
        val types = LinkedList<Type>()

        val typeDetails = TypeDetails("test type name", "test type url")
        val type = Type(1, typeDetails)
        types.add(type)
        val typesArray = arrayOfNulls<Type>(types.size)
        types.toArray(typesArray)


        val stats = LinkedList<Stat>()
        val statDetails = StatDetails("test details name", "test details url")
        val stat = Stat(1, 1, statDetails)
        stats.add(stat)
        val statsArray = arrayOfNulls<Stat>(stats.size)
        stats.toArray(statsArray)

        val abilities = LinkedList<Ability>()
        val abilityDetails = AbilityDetails("test ability name", "test ability url")
        val ability = Ability(abilityDetails)
        abilities.add(ability)
        val abilitiesArray = arrayOfNulls<Ability>(abilities.size)
        abilities.toArray(abilitiesArray)


        val pokemonDetails = PokemonDetails(sprites, typesArray, statsArray, abilitiesArray)

        pokemonViewModel.populateDetails(pokemonDetails)

        assertNotNull(pokemonViewModel.name)
        assertNotNull(pokemonViewModel.image)
        assertNotNull(pokemonViewModel.abilities)
        assertNotNull(pokemonViewModel.types)
        assertNotNull(pokemonViewModel.abilities.first.ability.name)
        assertNotNull(pokemonViewModel.abilities.first.ability.url)
    }
}
