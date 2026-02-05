package com.example.petmatch.features.petmatch.presentation.screens

/**
 * Estado de la UI para los formularios de PetMatch (Alta de mascotas, hogares y transacciones).
 * Sigue estrictamente el patrón de manejo de estados de UI (UiState) que utiliza el profesor.
 */
data class FormUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)