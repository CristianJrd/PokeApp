package com.example.pokedex.data.repository

import com.example.pokedex.data.model.ResponsePokemonDataRealm

class ListPokemonDaoImpl : PokemonDao<ResponsePokemonDataRealm>(), ListPokemonDao {
    override val modelClass: Class<ResponsePokemonDataRealm>
        get() = ResponsePokemonDataRealm::class.java

    override suspend fun insertPokemonObject(pokemonList: ResponsePokemonDataRealm) {
        createOrUpdate(realmObject = pokemonList)
    }
}