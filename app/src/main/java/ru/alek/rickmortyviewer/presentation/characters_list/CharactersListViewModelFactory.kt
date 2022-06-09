package ru.alek.rickmortyviewer.presentation.characters_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.alek.rickmortyviewer.domain.entities.SimpleFilter
import ru.alek.rickmortyviewer.domain.use_cases.GetCharactersUseCase

class CharactersListViewModelFactory(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val filter: SimpleFilter
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = CharactersListViewModel(getCharactersUseCase, filter) as T

}