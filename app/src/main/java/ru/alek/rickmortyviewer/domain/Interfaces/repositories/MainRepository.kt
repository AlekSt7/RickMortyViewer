package ru.alek.rickmortyviewer.domain.Interfaces.repositories

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import retrofit2.Response
import ru.alek.rickmortyviewer.domain.entities.CharacterModel
import ru.alek.rickmortyviewer.domain.entities.EpisodeModel
import ru.alek.rickmortyviewer.domain.entities.SimpleCharacterModel
import ru.alek.rickmortyviewer.domain.entities.SimpleFilter

interface MainRepository {

    fun getCharacters(string: SimpleFilter): LiveData<PagingData<SimpleCharacterModel>>
    fun getFullCharactersList(id: Int): LiveData<PagingData<CharacterModel>>
    suspend fun getEpisodesByIds(ids: String): Response<List<EpisodeModel.Episode>>?

}