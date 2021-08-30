package com.example.pokedex.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.net.URL

@RealmClass
open class ResponsePokemonDataRealm(
    @PrimaryKey
    var count: Long? = null,
    var results: RealmList<PokemonRealm> = RealmList()
) : RealmObject()

@RealmClass
open class PokemonRealm(
    var name: String = ""
) : RealmObject()

data class PokemonDetail(
    @SerializedName("base_happiness")
    val base_happiness: Long,
    @SerializedName("capture_rate")
    val capture_rate: Long,
    @SerializedName("egg_groups")
    val egg_groups: ArrayList<EggGroups>,
    @SerializedName("evolution_chain")
    val evolution_chain: UrlEvolutionChain
)

data class UrlEvolutionChain(
    @SerializedName("url")
    val url: URL
)

data class EggGroups(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class Abilities(
    @SerializedName("abilities")
    val abilities: ArrayList<ResponseAbility>
)

data class ResponseAbility(
    @SerializedName("ability")
    val ability: Ability
)

data class Ability(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class EvolutionChain(
    val chain: Chain
)

data class Chain(
    val evolves_to: ArrayList<Chain>,
    val species: Species
)

data class Species(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)