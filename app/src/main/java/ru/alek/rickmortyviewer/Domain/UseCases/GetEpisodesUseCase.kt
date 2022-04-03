package ru.alek.rickmortyviewer.Domain.UseCases

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Response
import ru.alek.rickmortyviewer.Domain.Entities.EpisodeModel
import ru.alek.rickmortyviewer.Domain.Interfaces.Repositories.MainRepository

class GetEpisodesUseCase(private val repo: MainRepository) {

    var flow = MutableStateFlow<EpisodeModel?>(null)

    suspend fun execute(episodes: List<String>, episodeModel: EpisodeModel): Flow<EpisodeModel> {
        var s: String = ""
        episodes.map {
            s = "$s${it.substring(it.lastIndexOf("/")+1)},"
        }

        episodeModel.episodes = repo.getEpisodesByIds(s)?.body()
        flow.value = episodeModel
        return flow as Flow<EpisodeModel>
    }

}