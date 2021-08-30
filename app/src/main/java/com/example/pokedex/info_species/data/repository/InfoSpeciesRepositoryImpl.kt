package com.example.pokedex.info_species.data.repository

import com.example.pokedex.data.model.PokemonDetail
import com.example.pokedex.util.NetworkClient
import com.example.pokedex.util.ServiceApi
import retrofit2.create

class InfoSpeciesRepositoryImpl : InfoSpeciesRepository {
    private val service = NetworkClient.buildRetrofitClient().create<ServiceApi>()

    override suspend fun getPokemonData(name: String): PokemonDetail {
        return service.getPokemonDetail(name)
    }

}