package com.example.pokedex.evolution_chain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.model.Chain
import com.example.pokedex.evolution_chain.data.EvolutionChainRepositoryImpl
import com.example.pokedex.util.LiveDataState
import com.example.pokedex.util.MutableLiveDataState
import com.example.pokedex.util.StateApp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EvolutionChainViewModel : ViewModel() {
    private val evolutionChainRepository by lazy { EvolutionChainRepositoryImpl() }

    private val _evolutionChainPokemon: MutableLiveDataState<Chain> by lazy { MutableLiveDataState() }
    val evolutionChainPokemon: LiveDataState<Chain> = _evolutionChainPokemon

    private val _loading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val loading: LiveData<Boolean> = _loading

    fun getEvolutionChainPokemon(url: String) {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            _evolutionChainPokemon.postValue(StateApp.Error(throwable))
            _loading.postValue(false)
        }) {
            val response = evolutionChainRepository.getEvolutionChain(url)
            _evolutionChainPokemon.postValue(StateApp.Success(response.chain))
            _loading.postValue(false)
        }
    }
}