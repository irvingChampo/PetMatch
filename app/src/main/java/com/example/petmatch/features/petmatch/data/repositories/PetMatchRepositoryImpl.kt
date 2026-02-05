package com.example.petmatch.features.petmatch.data.repositories

import com.example.petmatch.core.network.PetMatchApi
import com.example.petmatch.features.petmatch.data.model.*

class PetMatchRepositoryImpl(private val api: PetMatchApi) {
    suspend fun getMascotas() = try { Result.success(api.getMascotas()) } catch (e: Exception) { Result.failure(e) }
    suspend fun getHogares() = try { Result.success(api.getHogares()) } catch (e: Exception) { Result.failure(e) }

    suspend fun crearMascota(m: MascotaDto) = try { Result.success(api.crearMascota(m)) } catch (e: Exception) { Result.failure(e) }
    suspend fun crearHogar(h: HogarDto) = try { Result.success(api.crearHogar(h)) } catch (e: Exception) { Result.failure(e) }

    // LA TRANSACCIÓN: Vincular Mascota a Hogar
    suspend fun asignarAcogida(idMascota: Int, idHogar: Int, nuevaOcupacion: Int): Result<Boolean> {
        return try {
            // 1. Actualizar Mascota
            api.actualizarMascota(idMascota, mapOf("estado" to "En acogida", "hogarId" to idHogar.toString()))
            // 2. Actualizar Hogar (Capacidad)
            api.actualizarHogar(idHogar, mapOf("ocupacionActual" to nuevaOcupacion))
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}