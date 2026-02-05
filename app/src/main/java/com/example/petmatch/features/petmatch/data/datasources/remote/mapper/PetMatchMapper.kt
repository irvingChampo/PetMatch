package com.example.petmatch.features.petmatch.data.datasources.remote.mapper

import com.example.petmatch.features.petmatch.data.datasources.remote.model.*
import com.example.petmatch.features.petmatch.domain.entities.*

fun MascotaDto.toDomain(): Pet = Pet(id, nombre, especie, edad, estadoSalud, estado, fotoUrl)

fun HogarDto.toDomain(): Home = Home(id, nombreVoluntario, direccion, capacidad, ocupacionActual, tipoMascotaAceptada)