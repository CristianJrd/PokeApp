package com.example.pokedex.evolution_chain.data

import com.example.pokedex.data.model.EvolutionChain

interface EvolutionChainRepository {
    suspend fun getEvolutionChain(url: String): EvolutionChain
}