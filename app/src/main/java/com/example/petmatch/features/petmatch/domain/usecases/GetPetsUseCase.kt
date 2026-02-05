package com.example.petmatch.features.petmatch.domain.usecases

import com.example.petmatch.features.petmatch.domain.repositories.PetMatchRepository
import com.example.petmatch.features.petmatch.domain.entities.Pet

class GetPetsUseCase(private val repository: PetMatchRepository) {
    suspend operator fun invoke(): Result<List<Pet>> {
        return try {
            val pets = repository.getPets()
            Result.success(pets)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}