package com.example.petmatch.features.petmatch.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petmatch.features.petmatch.domain.entities.Pet
import com.example.petmatch.features.petmatch.domain.repositories.PetMatchRepository
import com.example.petmatch.features.petmatch.domain.usecases.AssignPetUseCase
import com.example.petmatch.features.petmatch.presentation.screens.FormUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de la lógica de los formularios y transacciones.
 * Sigue estrictamente el patrón MVVM y Clean Architecture del profesor.
 */
class FormViewModel(
    private val repository: PetMatchRepository,
    private val assignPetUseCase: AssignPetUseCase
) : ViewModel() {

    // Estado de la UI siguiendo el flujo unidireccional (UDF) del profesor
    private val _uiState = MutableStateFlow(FormUiState())
    val uiState = _uiState.asStateFlow()

    /**
     * Función para guardar una nueva mascota (CRUD Básico)
     */
    fun savePet(nombre: String, especie: String, edad: String) {
        // Validación simple antes de enviar
        if (nombre.isBlank() || especie.isBlank() || edad.isBlank()) {
            _uiState.update { it.copy(error = "Por favor, rellena todos los campos") }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                val newPet = Pet(
                    id = 0, // El ID lo genera el backend
                    nombre = nombre,
                    especie = especie,
                    edad = edad.toIntOrNull() ?: 0,
                    estadoSalud = "Saludable",
                    estado = "Sin hogar",
                    fotoUrl = null
                )

                repository.createPet(newPet)

                // Si llegamos aquí, la creación fue exitosa
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Error al guardar la mascota") }
            }
        }
    }

    /**
     * Función para ejecutar la TRANSACCIÓN de asignación de mascota a hogar.
     * Coordina el cambio de estatus de la mascota y la actualización de capacidad del hogar.
     */
    fun assignPetToHome(petId: Int, homeId: Int, currentOccupancy: Int) {
        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            // Usamos el Caso de Uso (Domain Layer) para la transacción
            val result = assignPetUseCase(petId, homeId, currentOccupancy)

            result.fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message) }
                }
            )
        }
    }

    /**
     * Limpia el estado para que el formulario pueda ser usado de nuevo sin mostrar éxitos anteriores.
     */
    fun resetState() {
        _uiState.update { FormUiState() }
    }
}