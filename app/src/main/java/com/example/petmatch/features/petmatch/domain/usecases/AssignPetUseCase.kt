package com.example.petmatch.features.petmatch.domain.usecases

import com.example.petmatch.features.petmatch.domain.repositories.PetMatchRepository

/**
 * Caso de uso para la transacción de asignar una mascota a un hogar.
 * Sigue el patrón de "Invoke" que utiliza el profesor en Clean Architecture.
 */
class AssignPetUseCase(private val repository: PetMatchRepository) {

    suspend operator fun invoke(pId: Int, hId: Int, occ: Int): Result<Boolean> {
        return try {
            // Cambiamos 'assignAcogida' por 'assignPetToHome' para que coincida con la Interfaz
            val result = repository.assignPetToHome(pId, hId, occ)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}