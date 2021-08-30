package com.example.pokedex.evolution_chain.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.R
import com.example.pokedex.data.model.Chain
import com.example.pokedex.data.model.Species
import com.example.pokedex.databinding.FragmentEvolutionChainPokemonBinding
import com.example.pokedex.evolution_chain.adapter.EvolutionChainAdapter
import com.example.pokedex.evolution_chain.viewmodel.EvolutionChainViewModel
import com.example.pokedex.info_species.ui.InfoSpeciesFragment
import com.example.pokedex.util.Constants
import com.example.pokedex.util.common.fragment.LoadingDialog

class EvolutionChainFragment : Fragment() {
    private lateinit var binding: FragmentEvolutionChainPokemonBinding
    private val viewModel by viewModels<EvolutionChainViewModel>()
    private val evolutionChainAdapter by lazy { EvolutionChainAdapter() }
    private val loadingDialog = LoadingDialog()
    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            url = this.getString(Constants.URL_EVOLUTION_CHAIN) ?: ""
            getData()
        }
    }

    private fun getData() {
        viewModel.getEvolutionChainPokemon(url)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_evolution_chain_pokemon,
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

        init()
    }

    private fun init() {
        viewModel.evolutionChainPokemon.observe(viewLifecycleOwner) { state ->
            state.fold({
                setEvolutionChainPokemon(it)
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

    private fun setEvolutionChainPokemon(chain: Chain) {
        binding.run {
            val arrayEvolutionChain = arrayListOf<Species>()
            arrayEvolutionChain.add(chain.species)
            var chain2 = chain.evolves_to
            while (chain2.isNotEmpty()) {
                chain2.forEach {
                    arrayEvolutionChain.add(it.species)
                    chain2 = it.evolves_to
                }
            }
            evolutionChainAdapter.setData(arrayEvolutionChain)
            rvEvolutionChainPokemon.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL, false
            )
            rvEvolutionChainPokemon.adapter = evolutionChainAdapter
        }
    }

    private fun showLoadingDialog() {
        loadingDialog.safeShow(parentFragmentManager)
    }

    private fun hideLoadingDialog() {
        loadingDialog.safeDismiss()
    }
}