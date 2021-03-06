package ru.alek.rickmortyviewer.domain.entities

data class ServerResponse(
    val info: Info,
    val results: List<SimpleCharacterModel>
){
    data class Info(
        val count: Int = 0,
        val pages: Int = 0,
        val next: String = "",
        val prev: String = ""
    )
}