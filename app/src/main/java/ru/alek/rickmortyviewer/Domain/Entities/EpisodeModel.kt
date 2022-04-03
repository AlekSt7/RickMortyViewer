package ru.alek.rickmortyviewer.Domain.Entities

data class EpisodeModel(
    val id: Int?,
    var episodes: List<Episode>? = null
){
    data class Episode(
        val name: String,
        val air_date: String,
        val episode: String
    )
}