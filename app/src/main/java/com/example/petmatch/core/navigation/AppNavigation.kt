package com.example.petmatch.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.petmatch.features.auth.di.AuthModule
import com.example.petmatch.features.auth.presentation.screens.LoginScreen
import com.example.petmatch.features.auth.presentation.screens.RegisterScreen
import com.example.petmatch.features.petmatch.di.PetMatchModule
import com.example.petmatch.features.petmatch.presentation.screens.*

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
                onNavigateToRegister = { navController.navigate(PetMatchScreens.Register.route) }
            )
        }

        composable(PetMatchScreens.Register.route) {
            RegisterScreen(
                viewModel = viewModel(factory = authModule.provideAuthViewModelFactory()),
                onRegisterSuccess = { navController.popBackStack() },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        composable(PetMatchScreens.Dashboard.route) {
            DashboardScreen(
                viewModel = viewModel(factory = petMatchModule.provideDashboardFactory()),
                onNavigateToAddPet = { navController.navigate(PetMatchScreens.AddPet.route) },
                onNavigateToAddHome = { navController.navigate(PetMatchScreens.AddHome.route) },
                onNavigateToAssign = { id, name ->
                    navController.navigate(PetMatchScreens.AssignPet.createRoute(id, name))
                }
            )
        }

        composable(PetMatchScreens.AddPet.route) {
            PetFormScreen(
                viewModel = viewModel(factory = petMatchModule.provideFormFactory()),
                onBack = { navController.popBackStack() }
            )
        }

        composable(PetMatchScreens.AddHome.route) {
            HomeFormScreen(
                viewModel = viewModel(factory = petMatchModule.provideFormFactory()),
                onBack = { navController.popBackStack() }
            )
        }

        // NUEVA RUTA DE ASIGNACIÓN
        composable(
            route = PetMatchScreens.AssignPet.route,
            arguments = listOf(
                navArgument("petId") { type = NavType.IntType },
                navArgument("petName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val petId = backStackEntry.arguments?.getInt("petId") ?: 0
            val petName = backStackEntry.arguments?.getString("petName") ?: ""

            AssignPetScreen(
                petId = petId,
                petName = petName,
                formViewModel = viewModel(factory = petMatchModule.provideFormFactory()),
                dashboardViewModel = viewModel(factory = petMatchModule.provideDashboardFactory()),
                onBack = { navController.popBackStack() }
            )
        }
    }
}