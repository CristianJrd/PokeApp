package com.example.pokedex.abilities.data.repository

import com.example.pokedex.data.model.Abilities
import com.example.pokedex.util.NetworkClient
import com.example.pokedex.util.ServiceApi
import retrofit2.create

class AbilitiesRepositoryImpl : AbilitiesRepository {
    private val service = NetworkClient.buildRetrofitClient().create<ServiceApi>()

    override suspend fun getAbilitiesPokemon(name: String): Abilities {
        return service.getAbilities(name)
    }


}