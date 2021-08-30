package com.example.pokedex.evolution_chain.data

import com.example.pokedex.data.model.EvolutionChain
import com.example.pokedex.util.NetworkClient
import com.example.pokedex.util.ServiceApi
import retrofit2.create

class EvolutionChainRepositoryImpl : EvolutionChainRepository {
    private val service = NetworkClient.buildRetrofitClient().create<ServiceApi>()

    override suspend fun getEvolutionChain(url: String): EvolutionChain {
        return service.getEvolutionChain(url)
    }
}