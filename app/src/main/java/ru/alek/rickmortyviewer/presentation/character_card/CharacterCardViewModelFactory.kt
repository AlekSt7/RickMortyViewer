package ru.alek.rickmortyviewer.presentation.character_card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.alek.rickmortyviewer.domain.use_cases.GetCharacterUseCase
import ru.alek.rickmortyviewer.domain.use_cases.GetEpisodesUseCase

class CharacterCardViewModelFactory(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val getEpisodesUseCase: GetEpisodesUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = CharacterCardFragmentViewModel(getCharacterUseCase, getEpisodesUseCase) as T

}