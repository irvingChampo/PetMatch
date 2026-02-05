package com.example.petmatch.features.petmatch.di

import com.example.petmatch.core.di.AppContainer
import com.example.petmatch.features.petmatch.data.repositories.PetMatchRepositoryImpl
import com.example.petmatch.features.petmatch.presentation.viewmodels.DashboardViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PetMatchModule(private val appContainer: AppContainer) {
    fun provideFactory() = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DashboardViewModel(appContainer.petMatchRepository) as T
        }
    }
}