package ru.alek.rickmortyviewer.domain.use_cases

import ru.alek.rickmortyviewer.domain.Interfaces.repositories.MainRepository

class GetCharacterUseCase(private val repo: MainRepository) {

    fun execute(id: Int) = repo.getFullCharactersList(id)

}