package com.example.pokedex.abilities.data.repository

import com.example.pokedex.data.model.Abilities

interface AbilitiesRepository {
    suspend fun getAbilitiesPokemon(name: String): Abilities
}