package com.example.pokedex.dashboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.dashboard.data.repository.DashboardRepositoryImpl
import com.example.pokedex.data.model.PokemonRealm
import com.example.pokedex.data.repository.ListPokemonDao
import com.example.pokedex.data.repository.ListPokemonDaoImpl
import com.example.pokedex.util.LiveDataState
import com.example.pokedex.util.MutableLiveDataState
import com.example.pokedex.util.StateApp
import io.realm.RealmList
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val dashboardRepository by lazy { DashboardRepositoryImpl() }

    private val _listPrincipalPokemon: MutableLiveDataState<RealmList<PokemonRealm>> by lazy { MutableLiveDataState() }
    val listPrincipalPokemon: LiveDataState<RealmList<PokemonRealm>> = _listPrincipalPokemon

    private val _loading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val loading: LiveData<Boolean> = _loading

    fun getListPrincipalPokemon() {
        _loading.postValue(true)
        val listPokemonDao: ListPokemonDao by lazy { ListPokemonDaoImpl() }
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            _listPrincipalPokemon.postValue(StateApp.Error(throwable))
            _loading.postValue(false)
        }) {
            val response = dashboardRepository.getListPokemon()
            listPokemonDao.insertPokemonObject(response)
            _listPrincipalPokemon.postValue(StateApp.Success(response.results))
            _loading.postValue(false)
        }
    }
}