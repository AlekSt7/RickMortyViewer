package ru.alek.rickmortyviewer

import ru.alek.rickmortyviewer.domain.entities.SimpleFilter
import ru.alek.rickmortyviewer.domain.use_cases.GetCharacterUseCase
import ru.alek.rickmortyviewer.domain.use_cases.GetCharactersUseCase
import ru.alek.rickmortyviewer.domain.use_cases.GetEpisodesUseCase
import ru.alek.rickmortyviewer.presentation.character_card.CharacterCardViewModelFactory
import ru.alek.rickmortyviewer.presentation.characters_list.CharactersListViewModel
import ru.alek.rickmortyviewer.presentation.characters_list.CharactersListViewModelFactory
import ru.alek.rickmortyviewer.еData.Repositories.MainRepoImpl

object DiContainer {

    private val repository = MainRepoImpl(App.instance.getApi())

    private val getCharacterUseCase = GetCharacterUseCase(repository)
    private val getCharactersUseCase = GetCharactersUseCase(repository)
    private val getEpisodesUseCase = GetEpisodesUseCase(repository)
    private val filter = SimpleFilter()

    private val c = CharacterCardViewModelFactory(getCharacterUseCase, getEpisodesUseCase)
    private val cc = CharactersListViewModelFactory(getCharactersUseCase, filter)

    fun injectCharacterCardViewModel() = c
    fun injectCharactersListViewModel() = cc

}