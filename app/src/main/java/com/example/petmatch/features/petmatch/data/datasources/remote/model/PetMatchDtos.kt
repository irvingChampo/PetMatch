package com.example.petmatch.features.petmatch.data.datasources.remote.model

data class MascotaDto(
    val id: Int,
    val nombre: String,
    val especie: String,
    val edad: Int,
    val estadoSalud: String,
    val estado: String,
    val fotoUrl: String?
)

data class HogarDto(
    val id: Int,
    val nombreVoluntario: String,
    val direccion: String,
    val capacidad: Int,
    val ocupacionActual: Int,
    val tipoMascotaAceptada: String
)