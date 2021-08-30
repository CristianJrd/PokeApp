package com.example.pokedex.data.repository

import com.example.pokedex.data.model.ResponsePokemonDataRealm

interface ListPokemonDao {
    suspend fun insertPokemonObject(pokemonList: ResponsePokemonDataRealm)
}