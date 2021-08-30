package com.example.pokedex.info_species.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.model.PokemonDetail
import com.example.pokedex.info_species.data.repository.InfoSpeciesRepositoryImpl
import com.example.pokedex.util.LiveDataState
import com.example.pokedex.util.MutableLiveDataState
import com.example.pokedex.util.StateApp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InfoSpeciesViewModel : ViewModel() {

    private val dashboardRepository by lazy { InfoSpeciesRepositoryImpl() }

    private val _listPrincipalPokemon: MutableLiveDataState<PokemonDetail> by lazy { MutableLiveDataState() }
    val listPrincipalPokemon: LiveDataState<PokemonDetail> = _listPrincipalPokemon

    private val _loading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val loading: LiveData<Boolean> = _loading

    fun getListPrincipalPokemon(name: String) {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            _listPrincipalPokemon.postValue(StateApp.Error(throwable))
            _loading.postValue(false)
        }) {
            val response = dashboardRepository.getPokemonData(name = name)
            _listPrincipalPokemon.postValue(StateApp.Success(response))
            _loading.postValue(false)
        }
    }
}