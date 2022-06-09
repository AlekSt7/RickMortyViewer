package ru.alek.rickmortyviewer.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import retrofit2.Response
import ru.alek.rickmortyviewer.data.network.Interfaces.api.ApiService
import ru.alek.rickmortyviewer.domain.entities.CharacterModel

class CharacterCardPagingSource(private val apiService: ApiService, private val characterid: Int): PagingSource<Int, CharacterModel>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterModel>): Int? {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterModel> {

        val pageSize = params.loadSize
        val pageIndex = params.key ?: characterid / params.loadSize
        val characterOffset = characterid % params.loadSize
        val characterIndex = (pageIndex * pageSize) + characterOffset

        return try {
            val response: Response<List<CharacterModel>> = apiService.loadFullCharactersByIds(getStringWithComma(characterIndex, characterIndex + 1))
            val charactersCount = apiService.loadAllCharacters().body()!!.info.count

            LoadResult.Page(
                response.body()!!,
                if(pageIndex == 0) null else pageIndex - 1,
                if(pageIndex == charactersCount) null else pageIndex + 1
            )

        }catch (e: Exception){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }

    }

    private fun getStringWithComma(vararg n: Int): String{
        var r = ""
        if(n.size == 1) return n.toString()
        n.map { r = "$r$it," }
        return r
    }

}