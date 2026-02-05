package com.example.petmatch.features.auth.domain.repositories

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<String>
    suspend fun registro(nombre: String, email: String, password: String, telefono: String): Result<String>
}