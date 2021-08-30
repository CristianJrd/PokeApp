package com.example.pokedex.abilities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.data.model.ResponseAbility
import com.example.pokedex.databinding.ItemAbilityPokemonBinding

class AbilitiesAdapter : RecyclerView.Adapter<AbilitiesAdapter.AbilitiesPokemonHolder>() {

    private val listItems: MutableList<ResponseAbility> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbilitiesPokemonHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemAbilityPokemonBinding.inflate(layoutInflater, parent, false)
        return AbilitiesPokemonHolder(view)
    }

    override fun onBindViewHolder(holder: AbilitiesPokemonHolder, position: Int) {
        holder.bind(listItems[position])
    }

    fun setData(data: MutableList<ResponseAbility>) {
        val size = listItems.size
        listItems.addAll(data)
        val sizeNew = listItems.size
        notifyItemRangeChanged(size, sizeNew)
    }


    override fun getItemCount() = listItems.size

    inner class AbilitiesPokemonHolder(itemView: ItemAbilityPokemonBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val titleTextView: TextView = itemView.tvNameAbilityPokemon
        fun bind(ability: ResponseAbility) {

            itemView.run {
                titleTextView.text = ability.ability.name
            }

        }
    }

}