package com.example.pokedex.info_species.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.pokedex.R
import com.example.pokedex.data.model.PokemonDetail
import com.example.pokedex.databinding.FragmentInfoSpeciesPokemonBinding
import com.example.pokedex.info_species.viewmodel.InfoSpeciesViewModel
import com.example.pokedex.util.Constants
import com.example.pokedex.util.common.fragment.LoadingDialog

class InfoSpeciesFragment : Fragment() {
    private lateinit var binding: FragmentInfoSpeciesPokemonBinding
    private val viewModel by viewModels<InfoSpeciesViewModel>()
    private val loadingDialog = LoadingDialog()
    private var name: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            name = this.getString(Constants.POKEMON_NAME) ?: ""
            getData()
        }
    }

    private fun getData() {
        viewModel.getListPrincipalPokemon(name)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_info_species_pokemon,
            container,
            false
        )
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })

        binding.run {
            btnInfoSpeciesAbilities.setOnClickListener {
                findNavController().navigate(
                    R.id.action_id_nav_graph_info_species_fragment_to_abilities_fragment,
                    bundleOf(Constants.POKEMON_NAME to name)
                )
            }
        }

        init()
    }

    private fun init() {
        viewModel.listPrincipalPokemon.observe(viewLifecycleOwner) { state ->
            state.fold({
                setDataPokemonInfo(it)
            }, { e, _ ->
                Log.e(InfoSpeciesFragment::class.java.simpleName, e.message.toString())
            })
        }

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                showLoadingDialog()
            } else {
                hideLoadingDialog()
            }
        })
    }

    private fun setDataPokemonInfo(pokemonDetail: PokemonDetail) {
        binding.run {
            tvTitleNamePokemon.text = name
            tvInfoSpeciesBaseHappiness.text =
                getString(R.string.label_base_happiness, pokemonDetail.base_happiness.toString())
            tvInfoSpeciesCaptureRatio.text =
                getString(R.string.label_capture_ratio, pokemonDetail.capture_rate.toString())

            tvInfoSpeciesEggGroup.text =
                getString(R.string.label_egg_group, pokemonDetail.egg_groups.joinToString {
                    it.name
                })

            btnInfoSpeciesEvolutionChain.setOnClickListener {
                findNavController().navigate(
                    R.id.action_id_nav_graph_info_species_fragment_to_evolution_chain_fragment,
                    bundleOf(Constants.URL_EVOLUTION_CHAIN to pokemonDetail.evolution_chain.url.toString())
                )
            }
        }
    }

    private fun showLoadingDialog() {
        loadingDialog.safeShow(parentFragmentManager)
    }

    private fun hideLoadingDialog() {
        loadingDialog.safeDismiss()
    }
}