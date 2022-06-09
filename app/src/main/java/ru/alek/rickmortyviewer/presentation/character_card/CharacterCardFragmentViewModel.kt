package ru.alek.rickmortyviewer.presentation.character_card

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import ru.alek.rickmortyviewer.App
import ru.alek.rickmortyviewer.domain.entities.CharacterModel
import ru.alek.rickmortyviewer.domain.use_cases.GetCharacterUseCase
import ru.alek.rickmortyviewer.domain.use_cases.GetEpisodesUseCase
import ru.alek.rickmortyviewer.еData.Repositories.MainRepoImpl

class CharacterCardFragmentViewModel(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val getEpisodesUseCase: GetEpisodesUseCase) : ViewModel() {

    private val livedata = MutableLiveData<CharacterModel>(null)
    val episodesFlow = livedata.asFlow().flatMapConcat { getEpisodesUseCase.execute(it.episode, it.episodesChild!!) }

    fun fetchResults(id: Int) = getCharacterUseCase.execute(id)

    fun fetchEpisodes(character: CharacterModel){
        livedata.value = character
    }


}