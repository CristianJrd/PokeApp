package com.example.pokedex.abilities.ui

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
import com.example.pokedex.abilities.adapter.AbilitiesAdapter
import com.example.pokedex.abilities.viewmodel.AbilitiesViewModel
import com.example.pokedex.data.model.ResponseAbility
import com.example.pokedex.databinding.FragmentAbilitiesPokemonBinding
import com.example.pokedex.util.Constants
import com.example.pokedex.util.common.fragment.LoadingDialog

class AbilitiesFragment : Fragment() {
    private lateinit var binding: FragmentAbilitiesPokemonBinding
    private val viewModel by viewModels<AbilitiesViewModel>()
    private val abilitiesAdapter by lazy { AbilitiesAdapter() }
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
        viewModel.getAbilitiesPokemon(name)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_abilities_pokemon, container, false)
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
        viewModel.listAbilitiesPokemon.observe(viewLifecycleOwner) { state ->
            state.fold({
                setAbilitiesPokemon(it)
            }, { e, _ ->
                Log.e(AbilitiesFragment::class.java.simpleName, e.message.toString())
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

    private fun setAbilitiesPokemon(abilities: ArrayList<ResponseAbility>) {
        binding.run {
            tvTitleNamePokemon.text = name
            abilitiesAdapter.setData(abilities)
            rvAbilitiesPokemon.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL, false
            )
            rvAbilitiesPokemon.adapter = abilitiesAdapter
        }
    }

    private fun showLoadingDialog() {
        loadingDialog.safeShow(parentFragmentManager)
    }

    private fun hideLoadingDialog() {
        loadingDialog.safeDismiss()
    }
}