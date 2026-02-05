package com.example.petmatch.features.petmatch.data.repositories

import com.example.petmatch.core.network.PetMatchApi
import com.example.petmatch.features.petmatch.data.datasources.remote.mapper.toDomain
import com.example.petmatch.features.petmatch.data.datasources.remote.model.HogarDto
import com.example.petmatch.features.petmatch.data.datasources.remote.model.MascotaDto
import com.example.petmatch.features.petmatch.domain.entities.Home
import com.example.petmatch.features.petmatch.domain.entities.Pet
import com.example.petmatch.features.petmatch.domain.repositories.PetMatchRepository

class PetMatchRepositoryImpl(private val api: PetMatchApi) : PetMatchRepository {
    override suspend fun getPets(): List<Pet> = api.getMascotas().map { it.toDomain() }
    override suspend fun getHomes(): List<Home> = api.getHogares().map { it.toDomain() }

    override suspend fun createPet(pet: Pet): Pet {
        val dto = MascotaDto(0, pet.nombre, pet.especie, pet.edad, pet.estadoSalud, pet.estado, null)
        return api.crearMascota(dto).toDomain()
    }

    override suspend fun createHome(home: Home): Home {
        val dto = HogarDto(0, home.nombreVoluntario, home.direccion, home.capacidad, home.ocupacionActual, home.tipoMascotaAceptada)
        return api.crearHogar(dto).toDomain()
    }

    override suspend fun assignPetToHome(petId: Int, homeId: Int, currentOccupancy: Int): Boolean {
        return try {
            // Transacción: Parte 1 - Actualizar Mascota
            api.actualizarMascota(petId, mapOf("estado" to "En acogida", "hogarId" to homeId.toString()))
            // Transacción: Parte 2 - Actualizar Hogar
            api.actualizarHogar(homeId, mapOf("ocupacionActual" to currentOccupancy + 1))
            true
        } catch (e: Exception) {
            false
        }
    }
}