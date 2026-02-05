package com.example.petmatch.features.petmatch.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.petmatch.features.petmatch.domain.usecases.GetHomesUseCase
import com.example.petmatch.features.petmatch.domain.usecases.GetPetsUseCase

class DashboardViewModelFactory(
    private val getPetsUseCase: GetPetsUseCase,
    private val getHomesUseCase: GetHomesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DashboardViewModel(getPetsUseCase, getHomesUseCase) as T
    }
}