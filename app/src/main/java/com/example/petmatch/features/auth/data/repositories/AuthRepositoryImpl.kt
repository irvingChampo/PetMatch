package com.example.petmatch.features.auth.data.repositories

import com.example.petmatch.core.network.PetMatchApi
import com.example.petmatch.features.auth.data.model.*
import com.example.petmatch.features.auth.domain.repositories.AuthRepository

class AuthRepositoryImpl(private val api: PetMatchApi) : AuthRepository {
    override suspend fun login(email: String, password: String): Result<String> = try {
        val response = api.login(LoginRequest(email, password))
        Result.success(response.token)
    } catch (e: Exception) { Result.failure(e) }

    override suspend fun registro(nombre: String, email: String, password: String, telefono: String): Result<String> = try {
        val response = api.registro(RegisterRequest(nombre, email, password, telefono))
        Result.success(response.token)
    } catch (e: Exception) { Result.failure(e) }
}