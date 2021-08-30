package com.example.pokedex.dashboard.data.repository

import com.example.pokedex.data.model.ResponsePokemonDataRealm
import com.example.pokedex.util.NetworkClient
import com.example.pokedex.util.ServiceApi
import retrofit2.create

class DashboardRepositoryImpl : DashboardRepository {
    private val service = NetworkClient.buildRetrofitClient().create<ServiceApi>()

    override suspend fun getListPokemon(): ResponsePokemonDataRealm {
        return service.getListPokemon()
    }
}