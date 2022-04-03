package ru.alek.rickmortyviewer.Domain.UseCases

import ru.alek.rickmortyviewer.Domain.Entities.SimpleFilter
import ru.alek.rickmortyviewer.Domain.Interfaces.Repositories.MainRepository

class GetCharactersUseCase(private val repo: MainRepository) {

    fun execute(query: SimpleFilter) = repo.getCharacters(query)

}