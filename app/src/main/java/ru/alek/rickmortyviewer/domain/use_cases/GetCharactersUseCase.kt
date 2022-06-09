package ru.alek.rickmortyviewer.domain.use_cases

import ru.alek.rickmortyviewer.domain.entities.SimpleFilter
import ru.alek.rickmortyviewer.domain.Interfaces.repositories.MainRepository

class GetCharactersUseCase(private val repo: MainRepository) {

    fun execute(query: SimpleFilter) = repo.getCharacters(query)

}