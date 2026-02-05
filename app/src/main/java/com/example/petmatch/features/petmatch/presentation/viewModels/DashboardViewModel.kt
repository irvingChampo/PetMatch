package com.example.petmatch.features.petmatch.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petmatch.features.petmatch.domain.usecases.GetHomesUseCase
import com.example.petmatch.features.petmatch.domain.usecases.GetPetsUseCase
import com.example.petmatch.features.petmatch.presentation.screens.DashboardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getPetsUseCase: GetPetsUseCase,
    private val getHomesUseCase: GetHomesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val petsResult = getPetsUseCase()
            val homesResult = getHomesUseCase()

            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    mascotas = petsResult.getOrDefault(emptyList()),
                    hogares = homesResult.getOrDefault(emptyList()),
                    error = if (petsResult.isFailure) "Error de conexión" else null
                )
            }
        }
    }
}