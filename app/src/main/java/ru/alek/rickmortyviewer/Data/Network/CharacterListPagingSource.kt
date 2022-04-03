package ru.alek.rickmortyviewer.Data.Network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import retrofit2.Response
import ru.alek.rickmortyviewer.Data.Network.Interfaces.Api.ApiService
import ru.alek.rickmortyviewer.Domain.Entities.SimpleCharacterModel
import ru.alek.rickmortyviewer.Domain.Entities.ServerResponse
import ru.alek.rickmortyviewer.Domain.Entities.SimpleFilter

class CharacterListPagingSource(private val apiService: ApiService, private val filter: SimpleFilter): PagingSource<Int, SimpleCharacterModel>() {

    override fun getRefreshKey(state: PagingState<Int, SimpleCharacterModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SimpleCharacterModel> {

        val pageIndex = params.key ?: 1

        return try {
            val response: Response<ServerResponse> = apiService.loadAllCharacters(pageIndex, filter.name, filter.status, filter.gender)

            if(response.code() == 404){
                throw Exception(response.code().toString())
            }

            LoadResult.Page(
                response.body()!!.results,
                if(pageIndex == 1) null else pageIndex - 1,
                if(pageIndex == response.body()!!.info.pages) null else pageIndex + 1
            )

        }catch (e: Exception){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }

    }

}