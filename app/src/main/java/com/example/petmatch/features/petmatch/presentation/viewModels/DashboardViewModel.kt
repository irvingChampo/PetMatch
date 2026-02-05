package com.example.petmatch.features.petmatch.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petmatch.features.petmatch.data.model.*
import com.example.petmatch.features.petmatch.data.repositories.PetMatchRepositoryImpl
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class DashboardUiState(
    val isLoading: Boolean = false,
    val mascotas: List<MascotaDto> = emptyList(),
    val hogares: List<HogarDto> = emptyList(),
    val error: String? = null
)

class DashboardViewModel(private val repository: PetMatchRepositoryImpl) : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    init { loadData() }

    fun loadData() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val petsResult = repository.getMascotas()
            val homesResult = repository.getHogares()

            _uiState.update { it.copy(
                isLoading = false,
                mascotas = petsResult.getOrDefault(emptyList()),
                hogares = homesResult.getOrDefault(emptyList()),
                error = if (petsResult.isFailure) "Error al conectar" else null
            )}
        }
    }
}