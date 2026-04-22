package com.hirebeat.com.app.danmon.feature.profile.domain.usecases

import com.hirebeat.com.app.danmon.feature.profile.domain.entities.CatalogItem
import com.hirebeat.com.app.danmon.feature.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class GetCatalogsUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend fun execute(): Pair<List<CatalogItem>, List<CatalogItem>> {
        val instruments = repository.getInstruments()
        val genres = repository.getGenres()
        return Pair(instruments, genres)
    }
}