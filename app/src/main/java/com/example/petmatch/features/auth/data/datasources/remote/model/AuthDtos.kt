package com.example.petmatch.features.auth.data.datasources.remote.model

data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val nombre: String, val email: String, val password: String, val telefono: String)
data class AuthResponse(val token: String)