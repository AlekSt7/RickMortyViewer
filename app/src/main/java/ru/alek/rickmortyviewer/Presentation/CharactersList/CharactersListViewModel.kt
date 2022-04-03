package ru.alek.rickmortyviewer.Presentation.CharactersList

import androidx.lifecycle.*
import androidx.paging.cachedIn
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import ru.alek.rickmortyviewer.Data.Network.NetworkHandler
import ru.alek.rickmortyviewer.Domain.Entities.SimpleFilter
import ru.alek.rickmortyviewer.Domain.UseCases.GetCharactersUseCase
import ru.alek.rickmortyviewer.еData.Repositories.MainRepoImpl

class CharactersListViewModel : ViewModel() {

    private val mainRepo = MainRepoImpl(NetworkHandler.api)
    private val getCharactersUseCase = GetCharactersUseCase(mainRepo)
    private val stateFIlter = MutableLiveData(SimpleFilter())

    val simpleFilter = stateFIlter
    //val results = simpleFilter.asFlow().debounce(650).flatMapLatest { getCharactersUseCase.execute(it) }.cachedIn(viewModelScope)
    var results = simpleFilter.switchMap { getCharactersUseCase.execute(it) }.cachedIn(viewModelScope)
    fun fetchResultsByFilter(filter: SimpleFilter){
            stateFIlter.value = filter
    }

}