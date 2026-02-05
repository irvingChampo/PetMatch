package com.example.petmatch.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petmatch.features.auth.domain.usecases.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class AuthUiState(val isLoading: Boolean = false, val error: String? = null, val isSuccess: Boolean = false)

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    fun login(e: String, p: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            loginUseCase(e, p).fold(
                onSuccess = { _uiState.update { it.copy(isLoading = false, isSuccess = true) } },
                onFailure = { err -> _uiState.update { it.copy(isLoading = false, error = err.message) } }
            )
        }
    }

    fun register(n: String, e: String, p: String, t: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            registerUseCase(n, e, p, t).fold(
                onSuccess = { _uiState.update { it.copy(isLoading = false, isSuccess = true) } },
                onFailure = { err -> _uiState.update { it.copy(isLoading = false, error = err.message) } }
            )
        }
    }
}