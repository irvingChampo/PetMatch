package com.example.petmatch.features.petmatch.domain.usecases

import com.example.petmatch.features.petmatch.domain.repositories.PetMatchRepository
import com.example.petmatch.features.petmatch.domain.entities.Home

class GetHomesUseCase(private val repository: PetMatchRepository) {
    suspend operator fun invoke(): Result<List<Home>> {
        return try {
            val homes = repository.getHomes()
            Result.success(homes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}