package com.example.petmatch.features.petmatch.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.petmatch.features.petmatch.domain.repositories.PetMatchRepository
import com.example.petmatch.features.petmatch.domain.usecases.AssignPetUseCase


class FormViewModelFactory(
    private val repository: PetMatchRepository,
    private val assignPetUseCase: AssignPetUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FormViewModel(repository, assignPetUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}