package ru.alek.rickmortyviewer.еData.Repositories


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import retrofit2.Response
import ru.alek.rickmortyviewer.Data.Network.CharacterCardPagingSource
import ru.alek.rickmortyviewer.Data.Network.CharacterListPagingSource
import ru.alek.rickmortyviewer.Data.Network.Interfaces.Api.ApiService
import ru.alek.rickmortyviewer.Domain.Entities.EpisodeModel
import ru.alek.rickmortyviewer.Domain.Entities.SimpleFilter
import ru.alek.rickmortyviewer.Domain.Interfaces.Repositories.MainRepository

class MainRepoImpl(private val api: ApiService): MainRepository {

    override fun getCharacters(filter: SimpleFilter) = Pager(
           PagingConfig(
               pageSize = 20,
               prefetchDistance = 2,
           ),
           pagingSourceFactory = { CharacterListPagingSource(api, filter) }
       ).liveData

    override fun getFullCharactersList(id: Int) = Pager(
        PagingConfig(
            pageSize = 2,
            initialLoadSize = 2
        ),
        pagingSourceFactory = { CharacterCardPagingSource(api, id) }
        ).liveData

    override suspend fun getEpisodesByIds(ids: String): Response<List<EpisodeModel.Episode>>? {
        return try {
            api.loadEpisodesByIds(ids)
        }   catch (e: Exception){
            null
        }
    }


}