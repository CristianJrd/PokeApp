package com.example.pokedex.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.data.model.PokemonRealm
import com.example.pokedex.databinding.ItemPokemonCardBinding
import com.example.pokedex.util.Constants
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class PrincipalPokemonAdapter :
    RecyclerView.Adapter<PrincipalPokemonAdapter.PrincipalPokemonHolder>() {

    private val listItems: MutableList<PokemonRealm> = ArrayList()
    private var namePokemonFav: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrincipalPokemonHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemPokemonCardBinding.inflate(layoutInflater, parent, false)
        return PrincipalPokemonHolder(view)
    }

    override fun onBindViewHolder(holder: PrincipalPokemonHolder, position: Int) {
        holder.bind(listItems[position], namePokemonFav)
    }

    fun setData(data: MutableList<PokemonRealm>, pokemonFav: String?) {
        val size = listItems.size
        namePokemonFav = pokemonFav ?: ""
        listItems.addAll(data)
        val sizeNew = listItems.size
        notifyItemRangeChanged(size, sizeNew)
    }


    override fun getItemCount() = listItems.size

    inner class PrincipalPokemonHolder(itemView: ItemPokemonCardBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val titleTextView: TextView = itemView.tvItemPokemonName
        fun bind(pokemon: PokemonRealm, pokemonFav: String?) {

            itemView.run {

                if (pokemonFav!!.isNotBlank()) {
                    if (pokemon.name == pokemonFav) {
                        val random = (1..6).random()
                        if (random in 1..3) {
                            titleTextView.text =
                                context.getString(R.string.label_favorite_pokemon, pokemon.name)
                            Timer().schedule(5000) {
                                titleTextView.text = pokemon.name
                            }
                        } else {
                            titleTextView.text =
                                context.getString(R.string.label_error_pokemon, pokemon.name)
                            Timer().schedule(5000) {
                                titleTextView.text = pokemon.name
                            }
                        }
                    } else {
                        titleTextView.text = pokemon.name
                    }
                } else {
                    titleTextView.text = pokemon.name
                }
                rootView.setOnClickListener {
                    findNavController().navigate(
                        R.id.id_nav_action_dashboard_fragment_to_info_species_fragment,
                        bundleOf(Constants.POKEMON_NAME to pokemon.name)
                    )
                }
            }
        }
    }

}