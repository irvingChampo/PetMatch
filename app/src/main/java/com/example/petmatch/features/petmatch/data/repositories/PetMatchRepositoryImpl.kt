package com.example.petmatch.features.petmatch.data.repositories

import com.example.petmatch.core.network.PetMatchApi
import com.example.petmatch.features.petmatch.data.datasources.remote.mapper.toDomain
import com.example.petmatch.features.petmatch.data.datasources.remote.model.HogarDto
import com.example.petmatch.features.petmatch.data.datasources.remote.model.MascotaDto
import com.example.petmatch.features.petmatch.domain.entities.Home
import com.example.petmatch.features.petmatch.domain.entities.Pet
import com.example.petmatch.features.petmatch.domain.repositories.PetMatchRepository

class PetMatchRepositoryImpl(private val api: PetMatchApi) : PetMatchRepository {

    override suspend fun getPets(): List<Pet> = try {
        api.getMascotas().map { it.toDomain() }
    } catch (e: Exception) {
        emptyList()
    }

    override suspend fun getHomes(): List<Home> = try {
        api.getHogares().map { it.toDomain() }
    } catch (e: Exception) {
        emptyList()
    }

    override suspend fun createPet(pet: Pet): Pet {
        val dto = MascotaDto(
            nombre = pet.nombre,
            especie = pet.especie,
            edad = pet.edad,
            estadoSalud = pet.estadoSalud,
            estado = pet.estado,
            fotoUrl = null
        )
        return api.crearMascota(dto).toDomain()
    }

    override suspend fun createHome(home: Home): Home {
        val dto = HogarDto(
            nombreVoluntario = home.nombreVoluntario,
            direccion = home.direccion,
            telefono = "999000000", // Valor por defecto temporal hasta implementar el form de hogar
            capacidad = home.capacidad,
            ocupacionActual = home.ocupacionActual,
            tipoMascotaAceptada = home.tipoMascotaAceptada
        )
        return api.crearHogar(dto).toDomain()
    }

    override suspend fun assignPetToHome(petId: Int, homeId: Int, currentOccupancy: Int): Boolean {
        return try {
            api.actualizarMascota(petId, mapOf("estado" to "En acogida", "hogarId" to homeId.toString()))
            api.actualizarHogar(homeId, mapOf("ocupacionActual" to (currentOccupancy + 1)))
            true
        } catch (e: Exception) {
            false
        }
    }
}