package com.example.petmatch.features.petmatch.domain.repositories

import com.example.petmatch.features.petmatch.domain.entities.Home
import com.example.petmatch.features.petmatch.domain.entities.Pet

interface PetMatchRepository {
    suspend fun getPets(): List<Pet>
    suspend fun getHomes(): List<Home>
    suspend fun createPet(pet: Pet): Pet
    suspend fun createHome(home: Home): Home
    suspend fun assignPetToHome(petId: Int, homeId: Int, currentOccupancy: Int): Boolean
}