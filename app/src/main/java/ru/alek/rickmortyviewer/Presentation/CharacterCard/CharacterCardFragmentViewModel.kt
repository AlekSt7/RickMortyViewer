package ru.alek.rickmortyviewer.Presentation.CharacterCard

import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.alek.rickmortyviewer.Data.Network.NetworkHandler
import ru.alek.rickmortyviewer.Domain.Entities.CharacterModel
import ru.alek.rickmortyviewer.Domain.Entities.EpisodeModel
import ru.alek.rickmortyviewer.Domain.UseCases.GetCharacterUseCase
import ru.alek.rickmortyviewer.Domain.UseCases.GetEpisodesUseCase
import ru.alek.rickmortyviewer.еData.Repositories.MainRepoImpl

class CharacterCardFragmentViewModel : ViewModel() {

    private val mainRepo = MainRepoImpl(NetworkHandler.api)
    private val getCharacterUseCase = GetCharacterUseCase(mainRepo)
    private val getEpisodesUseCase = GetEpisodesUseCase(mainRepo)

    private val livedata = MutableLiveData<CharacterModel>(null)
    val episodesFlow = livedata.asFlow().flatMapConcat { getEpisodesUseCase.execute(it.episode, it.episodesChild!!) }

    fun fetchResults(id: Int) = getCharacterUseCase.execute(id)

    fun fetchEpisodes(character: CharacterModel){
        livedata.value = character
    }


}