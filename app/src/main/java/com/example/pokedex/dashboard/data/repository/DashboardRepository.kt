package com.example.pokedex.dashboard.data.repository

import com.example.pokedex.data.model.ResponsePokemonDataRealm

interface DashboardRepository {
    suspend fun getListPokemon(): ResponsePokemonDataRealm
}