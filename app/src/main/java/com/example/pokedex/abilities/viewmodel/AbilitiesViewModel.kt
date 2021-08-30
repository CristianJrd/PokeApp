package com.example.pokedex.abilities.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.abilities.data.repository.AbilitiesRepositoryImpl
import com.example.pokedex.data.model.ResponseAbility
import com.example.pokedex.util.LiveDataState
import com.example.pokedex.util.MutableLiveDataState
import com.example.pokedex.util.StateApp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AbilitiesViewModel : ViewModel() {
    private val abilitiesRepository by lazy { AbilitiesRepositoryImpl() }

    private val _listAbilitiesPokemon: MutableLiveDataState<ArrayList<ResponseAbility>> by lazy { MutableLiveDataState() }
    val listAbilitiesPokemon: LiveDataState<ArrayList<ResponseAbility>> = _listAbilitiesPokemon

    private val _loading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val loading: LiveData<Boolean> = _loading

    fun getAbilitiesPokemon(name: String) {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            _listAbilitiesPokemon.postValue(StateApp.Error(throwable))
            _loading.postValue(false)
        }) {
            val response = abilitiesRepository.getAbilitiesPokemon(name)
            _listAbilitiesPokemon.postValue(StateApp.Success(response.abilities))
            _loading.postValue(false)
        }
    }
}