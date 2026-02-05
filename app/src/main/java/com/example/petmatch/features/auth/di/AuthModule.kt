package com.example.petmatch.features.auth.di

import com.example.petmatch.core.di.AppContainer
import com.example.petmatch.features.auth.data.repositories.AuthRepositoryImpl
import com.example.petmatch.features.auth.domain.usecases.*
import com.example.petmatch.features.auth.presentation.viewmodels.AuthViewModelFactory

class AuthModule(private val appContainer: AppContainer) {

    private fun provideRepository() = AuthRepositoryImpl(appContainer.petMatchApi)

    fun provideAuthViewModelFactory(): AuthViewModelFactory {
        val repo = provideRepository()
        return AuthViewModelFactory(
            LoginUseCase(repo),
            RegisterUseCase(repo)
        )
    }
}