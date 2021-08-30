package com.example.pokedex.util

import com.example.pokedex.data.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ServiceApi {
    @GET("pokemon?limit=151")
    suspend fun getListPokemon(): ResponsePokemonDataRealm

    @GET("pokemon-species/{name}")
    suspend fun getPokemonDetail(@Path("name") name: String): PokemonDetail

    @GET("pokemon/{name}")
    suspend fun getAbilities(@Path("name") name: String): Abilities

    @GET
    suspend fun getEvolutionChain(@Url url: String): EvolutionChain
}