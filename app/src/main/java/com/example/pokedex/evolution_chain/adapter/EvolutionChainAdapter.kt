package com.example.pokedex.evolution_chain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.data.model.Species
import com.example.pokedex.databinding.ItemAbilityPokemonBinding
import com.example.pokedex.util.Constants

class EvolutionChainAdapter : RecyclerView.Adapter<EvolutionChainAdapter.EvolutionChainHolder>() {

    private val listItems: MutableList<Species> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvolutionChainHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemAbilityPokemonBinding.inflate(layoutInflater, parent, false)
        return EvolutionChainHolder(view)
    }

    override fun onBindViewHolder(holder: EvolutionChainHolder, position: Int) {
        holder.bind(listItems[position])
    }

    fun setData(data: MutableList<Species>) {
        val size = listItems.size
        listItems.addAll(data)
        val sizeNew = listItems.size
        notifyItemRangeChanged(size, sizeNew)
    }


    override fun getItemCount() = listItems.size

    inner class EvolutionChainHolder(itemView: ItemAbilityPokemonBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val titleTextView: TextView = itemView.tvNameAbilityPokemon
        fun bind(species: Species) {

            itemView.run {
                titleTextView.text = species.name
                rootView.setOnClickListener {
                    findNavController().navigate(
                        R.id.action_id_nav_graph_evolution_chain_fragment_to_id_nav_graph_dashboard_fragment,
                        bundleOf(Constants.POKEMON_FAV to species.name)
                    )
                }
            }

        }
    }

}