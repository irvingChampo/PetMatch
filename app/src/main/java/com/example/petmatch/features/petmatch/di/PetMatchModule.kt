package com.example.petmatch.features.petmatch.di

import com.example.petmatch.core.di.AppContainer
import com.example.petmatch.features.petmatch.domain.usecases.*
import com.example.petmatch.features.petmatch.presentation.viewmodels.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PetMatchModule(private val appContainer: AppContainer) {

    private fun provideRepository() = appContainer.petMatchRepository

    fun provideDashboardFactory() = DashboardViewModelFactory(
        GetPetsUseCase(provideRepository()),
        GetHomesUseCase(provideRepository())
    )

    fun provideFormFactory() = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FormViewModel(provideRepository(), AssignPetUseCase(provideRepository())) as T
        }
    }

    fun provideFormViewModelFactory(): FormViewModelFactory {
        val repository = appContainer.petMatchRepository
        return FormViewModelFactory(
            repository = repository,
            assignPetUseCase = AssignPetUseCase(repository)
        )
    }
}