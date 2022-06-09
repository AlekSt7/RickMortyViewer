package ru.alek.rickmortyviewer.data.network.Interfaces.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.alek.rickmortyviewer.domain.entities.CharacterModel
import ru.alek.rickmortyviewer.domain.entities.EpisodeModel
import ru.alek.rickmortyviewer.domain.entities.ServerResponse


interface ApiService {

    @GET("character/")
    suspend fun loadAllCharacters(@Query("page") page: Int? = 1,
                                  @Query("name") name: String? = null,
                                  @Query("status") status: String? = null,
                                  @Query("gender") gender: String? = null): Response<ServerResponse>

    @GET("character/{ids}")
    suspend fun loadFullCharactersByIds(@Path("ids") ids: String): Response<List<CharacterModel>>

    @GET("episode/{ids}")
    suspend fun loadEpisodesByIds(@Path("ids") ids: String): Response<List<EpisodeModel.Episode>>

}