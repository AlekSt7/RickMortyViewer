package ru.alek.rickmortyviewer.Domain.UseCases

import ru.alek.rickmortyviewer.Domain.Interfaces.Repositories.MainRepository

class GetCharacterUseCase(private val repo: MainRepository) {

    fun execute(id: Int) = repo.getFullCharactersList(id)

}