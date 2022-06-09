package ru.alek.rickmortyviewer.domain.entities

data class SimpleCharacterModel(
    val gender: String,
    val id: Int,
    val name: String,
    val species: String,
    val image: String,
)
