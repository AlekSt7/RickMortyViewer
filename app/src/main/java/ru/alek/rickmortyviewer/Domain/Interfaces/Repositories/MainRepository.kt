package ru.alek.rickmortyviewer.Domain.Interfaces.Repositories

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import ru.alek.rickmortyviewer.Domain.Entities.CharacterModel
import ru.alek.rickmortyviewer.Domain.Entities.EpisodeModel
import ru.alek.rickmortyviewer.Domain.Entities.SimpleCharacterModel
import ru.alek.rickmortyviewer.Domain.Entities.SimpleFilter

interface MainRepository {

    fun getCharacters(string: SimpleFilter): LiveData<PagingData<SimpleCharacterModel>>
    fun getFullCharactersList(id: Int): Flow<PagingData<CharacterModel>>
    suspend fun getEpisodesByIds(ids: String): Response<List<EpisodeModel.Episode>>?

}