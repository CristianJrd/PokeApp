package com.example.pokedex.data.database

import com.example.pokedex.data.model.PokemonRealm
import com.example.pokedex.data.model.ResponsePokemonDataRealm
import io.realm.annotations.RealmModule

@RealmModule(
    classes = arrayOf(
        ResponsePokemonDataRealm::class,
        PokemonRealm::class
    )
)
open class RealmModule