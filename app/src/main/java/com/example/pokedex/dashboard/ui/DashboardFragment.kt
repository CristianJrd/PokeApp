package com.example.pokedex.dashboard.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.R
import com.example.pokedex.dashboard.adapter.PrincipalPokemonAdapter
import com.example.pokedex.dashboard.viewmodel.DashboardViewModel
import com.example.pokedex.data.model.PokemonRealm
import com.example.pokedex.databinding.FragmentDashboardBinding
import com.example.pokedex.util.Constants
import com.example.pokedex.util.common.fragment.LoadingDialog
import io.realm.RealmList

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val viewModel by viewModels<DashboardViewModel>()
    private val principalPokemonAdapter by lazy { PrincipalPokemonAdapter() }
    private val loadingDialog = LoadingDialog()
    private var name: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            name = this.getString(Constants.POKEMON_FAV) ?: ""
        }
        viewModel.getListPrincipalPokemon()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewModel.listPrincipalPokemon.observe(viewLifecycleOwner) { state ->
            state.fold({
                setPrincipalPokemon(it)
            }, { e, _ ->
                Log.e(DashboardFragment::class.java.simpleName, e.message.toString())
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

    private fun setPrincipalPokemon(pokemon: RealmList<PokemonRealm>) {
        binding.run {
            if (name.isNotBlank()) {
                principalPokemonAdapter.setData(pokemon, name)
            } else {
                principalPokemonAdapter.setData(pokemon, "")
            }
            rvDashboardListPokemon.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL, false
            )
            rvDashboardListPokemon.adapter = principalPokemonAdapter
        }
    }

    private fun showLoadingDialog() {
        loadingDialog.safeShow(parentFragmentManager)
    }

    private fun hideLoadingDialog() {
        loadingDialog.safeDismiss()
    }
}