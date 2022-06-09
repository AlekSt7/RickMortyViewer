package ru.alek.rickmortyviewer.domain.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.alek.rickmortyviewer.domain.entities.EpisodeModel
import ru.alek.rickmortyviewer.domain.Interfaces.repositories.MainRepository

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