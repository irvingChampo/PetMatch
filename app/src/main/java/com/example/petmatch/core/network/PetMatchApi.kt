package com.example.petmatch.core.network

import com.example.petmatch.features.auth.data.model.*
import com.example.petmatch.features.petmatch.data.model.*
import retrofit2.http.*

interface PetMatchApi {
    // Auth (Ya creados)
    @POST("usuarios/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse
    @POST("usuarios/registro")
    suspend fun registro(@Body request: RegisterRequest): AuthResponse

    // Mascotas
    @GET("mascotas")
    suspend fun getMascotas(): List<MascotaDto>
    @POST("mascotas")
    suspend fun crearMascota(@Body mascota: MascotaDto): MascotaDto
    @PUT("mascotas/{id}")
    suspend fun actualizarMascota(@Path("id") id: Int, @Body datos: Map<String, String>): MascotaDto

    // Hogares
    @GET("hogares")
    suspend fun getHogares(): List<HogarDto>
    @POST("hogares")
    suspend fun crearHogar(@Body hogar: HogarDto): HogarDto
    @PUT("hogares/{id}")
    suspend fun actualizarHogar(@Path("id") id: Int, @Body datos: Map<String, Int>): HogarDto
}