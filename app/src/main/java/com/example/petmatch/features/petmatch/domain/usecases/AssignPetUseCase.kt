package com.example.petmatch.features.petmatch.domain.usecases

import com.example.petmatch.features.petmatch.domain.repositories.PetMatchRepository


class AssignPetUseCase(private val repository: PetMatchRepository) {

    suspend operator fun invoke(pId: Int, hId: Int, occ: Int): Result<Boolean> {
        return try {
            val result = repository.assignPetToHome(pId, hId, occ)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}