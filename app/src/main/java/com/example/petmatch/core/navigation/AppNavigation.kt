package com.example.petmatch.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petmatch.features.auth.di.AuthModule
import com.example.petmatch.features.auth.presentation.screens.LoginScreen
import com.example.petmatch.features.auth.presentation.screens.RegisterScreen
import com.example.petmatch.features.petmatch.di.PetMatchModule
import com.example.petmatch.features.petmatch.presentation.screens.DashboardScreen
import com.example.petmatch.features.petmatch.presentation.screens.PetFormScreen

@Composable
fun AppNavigation(authModule: AuthModule, petMatchModule: PetMatchModule) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = PetMatchScreens.Login.route) {

        composable(PetMatchScreens.Login.route) {
            LoginScreen(
                viewModel = viewModel(factory = authModule.provideAuthViewModelFactory()),
                onLoginSuccess = {
                    navController.navigate(PetMatchScreens.Dashboard.route) {
                        popUpTo(PetMatchScreens.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(PetMatchScreens.Register.route)
                }
            )
        }

        composable(PetMatchScreens.Register.route) {
            RegisterScreen(
                viewModel = viewModel(factory = authModule.provideAuthViewModelFactory()),
                onRegisterSuccess = {
                    navController.popBackStack() // Vuelve al login tras registrarse
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(PetMatchScreens.Dashboard.route) {
            DashboardScreen(
                viewModel = viewModel(factory = petMatchModule.provideDashboardFactory()),
                onNavigateToAddPet = { navController.navigate(PetMatchScreens.AddPet.route) }
            )
        }

        composable(PetMatchScreens.AddPet.route) {
            PetFormScreen(
                viewModel = viewModel(factory = petMatchModule.provideFormFactory()),
                onBack = { navController.popBackStack() }
            )
        }
    }
}