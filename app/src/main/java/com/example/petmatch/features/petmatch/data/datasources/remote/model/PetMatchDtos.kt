package com.example.petmatch.features.petmatch.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class MascotaDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("especie") val especie: String,
    @SerializedName("edad") val edad: Int,
    @SerializedName("estadoSalud") val estadoSalud: String,
    @SerializedName("estado") val estado: String,
    @SerializedName("fotoUrl") val fotoUrl: String? = null,
    @SerializedName("hogarId") val hogarId: Int? = null
)

data class HogarDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("nombreVoluntario") val nombreVoluntario: String,
    @SerializedName("direccion") val direccion: String,
    @SerializedName("telefono") val telefono: String,
    @SerializedName("capacidad") val capacidad: Int,
    @SerializedName("ocupacionActual") val ocupacionActual: Int,
    @SerializedName("tipoMascotaAceptada") val tipoMascotaAceptada: String
)