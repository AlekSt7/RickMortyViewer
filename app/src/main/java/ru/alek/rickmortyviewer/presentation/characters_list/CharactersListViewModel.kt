package ru.alek.rickmortyviewer.presentation.characters_list

import androidx.lifecycle.*
import androidx.paging.cachedIn
import ru.alek.rickmortyviewer.App
import ru.alek.rickmortyviewer.domain.entities.SimpleFilter
import ru.alek.rickmortyviewer.domain.use_cases.GetCharacterUseCase
import ru.alek.rickmortyviewer.domain.use_cases.GetCharactersUseCase
import ru.alek.rickmortyviewer.еData.Repositories.MainRepoImpl

class CharactersListViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val filter: SimpleFilter) : ViewModel() {

    private val stateFIlter = MutableLiveData(filter)

    val simpleFilter = stateFIlter
    //val results = simpleFilter.asFlow().debounce(650).flatMapLatest { getCharactersUseCase.execute(it) }.cachedIn(viewModelScope)
    var results = simpleFilter.switchMap { getCharactersUseCase.execute(it) }.cachedIn(viewModelScope)
    fun fetchResultsByFilter(filter: SimpleFilter){
            stateFIlter.value = filter
    }

}