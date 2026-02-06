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

    // Funciones para Mascotas
    fun savePet(nombre: String, especie: String, edad: String, id: Int = 0) {
        if (nombre.isBlank() || especie.isBlank() || edad.isBlank()) {
            _uiState.update { it.copy(error = "Rellena todos los campos") }
            return
        }
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                val pet = Pet(id, nombre.trim(), especie, edad.toIntOrNull() ?: 0, "Saludable", "Sin hogar", null)
                if (id == 0) repository.createPet(pet) else repository.updatePet(pet)
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun deletePet(id: Int) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                repository.deletePet(id)
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "No se pudo eliminar") }
            }
        }
    }

    // Funciones para Hogares
    fun saveHome(nombre: String, direccion: String, telefono: String, capacidad: String, tipo: String, id: Int = 0) {
        if (nombre.isBlank() || direccion.isBlank() || telefono.isBlank() || capacidad.isBlank()) {
            _uiState.update { it.copy(error = "Campos obligatorios vacíos") }
            return
        }
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                val home = Home(id, nombre.trim(), direccion.trim(), capacidad.toIntOrNull() ?: 0, 0, tipo)
                if (id == 0) repository.createHome(home, telefono) else repository.updateHome(home, telefono)
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun deleteHome(id: Int) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                repository.deleteHome(id)
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Error al eliminar hogar") }
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