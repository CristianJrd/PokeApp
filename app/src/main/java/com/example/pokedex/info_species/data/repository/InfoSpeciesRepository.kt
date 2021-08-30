package com.example.pokedex.info_species.data.repository

import com.example.pokedex.data.model.PokemonDetail

interface InfoSpeciesRepository {
    suspend fun getPokemonData(name: String): PokemonDetail
}