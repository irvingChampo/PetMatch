package com.example.petmatch.features.auth.di

import com.example.petmatch.core.di.AppContainer
import com.example.petmatch.features.auth.data.repositories.AuthRepositoryImpl
import com.example.petmatch.features.auth.domain.usecases.*
import com.example.petmatch.features.auth.presentation.viewmodels.AuthViewModelFactory

class AuthModule(private val appContainer: AppContainer) {
    private val repository by lazy { AuthRepositoryImpl(appContainer.petMatchApi) }

    fun provideFactory() = AuthViewModelFactory(
        LoginUseCase(repository),
        RegisterUseCase(repository)
    )
}