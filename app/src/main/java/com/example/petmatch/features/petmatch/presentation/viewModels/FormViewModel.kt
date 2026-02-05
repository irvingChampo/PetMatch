package com.example.petmatch.features.petmatch.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petmatch.features.petmatch.domain.entities.Home
import com.example.petmatch.features.petmatch.domain.entities.Pet
import com.example.petmatch.features.petmatch.domain.repositories.PetMatchRepository
import com.example.petmatch.features.petmatch.domain.usecases.AssignPetUseCase
import com.example.petmatch.features.petmatch.presentation.screens.FormUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FormViewModel(
    private val repository: PetMatchRepository,
    private val assignPetUseCase: AssignPetUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormUiState())
    val uiState = _uiState.asStateFlow()

    fun savePet(nombre: String, especie: String, edad: String) {
        if (nombre.isBlank() || especie.isBlank() || edad.isBlank()) {
            _uiState.update { it.copy(error = "Por favor, rellena todos los campos") }
            return
        }
        val edadInt = edad.toIntOrNull()
        if (edadInt == null) {
            _uiState.update { it.copy(error = "La edad debe ser un número") }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                val newPet = Pet(0, nombre.trim(), especie, edadInt, "Saludable", "Sin hogar", null)
                repository.createPet(newPet)
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Error: ${e.message}") }
            }
        }
    }

    fun saveHome(nombre: String, direccion: String, telefono: String, capacidad: String, tipo: String) {
        if (nombre.isBlank() || direccion.isBlank() || telefono.isBlank() || capacidad.isBlank()) {
            _uiState.update { it.copy(error = "Todos los campos son obligatorios") }
            return
        }
        val capInt = capacidad.toIntOrNull()
        if (capInt == null) {
            _uiState.update { it.copy(error = "La capacidad debe ser un número") }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                val newHome = Home(
                    id = 0,
                    nombreVoluntario = nombre.trim(),
                    direccion = direccion.trim(),
                    capacidad = capInt,
                    ocupacionActual = 0,
                    tipoMascotaAceptada = tipo // 'Perros', 'Gatos', 'Ambos'
                )
                // Usamos el repository. Aquí pasaremos también el teléfono
                repository.createHome(newHome)
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Error al crear hogar") }
            }
        }
    }

    fun assignPetToHome(petId: Int, homeId: Int, currentOccupancy: Int) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val result = assignPetUseCase(petId, homeId, currentOccupancy)
            result.fold(
                onSuccess = { _uiState.update { it.copy(isLoading = false, isSuccess = true) } },
                onFailure = { error -> _uiState.update { it.copy(isLoading = false, error = error.message) } }
            )
        }
    }

    fun resetState() {
        _uiState.update { FormUiState() }
    }
}