package com.example.petmatch.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.petmatch.features.auth.di.AuthModule
import com.example.petmatch.features.auth.presentation.screens.*
import com.example.petmatch.features.petmatch.di.PetMatchModule
import com.example.petmatch.features.petmatch.presentation.screens.*

@Composable
fun AppNavigation(authModule: AuthModule, petMatchModule: PetMatchModule) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = PetMatchScreens.Login.route) {

        composable(PetMatchScreens.Login.route) {
            LoginScreen(
                viewModel = viewModel(factory = authModule.provideAuthViewModelFactory()),
                onLoginSuccess = { navController.navigate(PetMatchScreens.Dashboard.route) { popUpTo(0) } },
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
                formViewModel = viewModel(factory = petMatchModule.provideFormFactory()),
                onNavigateToAddPet = { navController.navigate(PetMatchScreens.AddPet.route) },
                onNavigateToAddHome = { navController.navigate(PetMatchScreens.AddHome.route) },
                onNavigateToEditPet = { id, n, s, a -> navController.navigate(PetMatchScreens.EditPet.createRoute(id, n, s, a)) },
                onNavigateToEditHome = { id, n, d, c, t -> navController.navigate(PetMatchScreens.EditHome.createRoute(id, n, d, c, t)) },
                onNavigateToAssign = { id, name -> navController.navigate(PetMatchScreens.AssignPet.createRoute(id, name)) }
            )
        }

        // AGREGAR MASCOTA
        composable(PetMatchScreens.AddPet.route) {
            PetFormScreen(
                viewModel = viewModel(factory = petMatchModule.provideFormFactory()),
                onBack = { navController.popBackStack() }
            )
        }

        // EDITAR MASCOTA
        composable(
            route = PetMatchScreens.EditPet.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("name") { type = NavType.StringType },
                navArgument("specie") { type = NavType.StringType },
                navArgument("age") { type = NavType.IntType }
            )
        ) { backStack ->
            PetFormScreen(
                viewModel = viewModel(factory = petMatchModule.provideFormFactory()),
                petId = backStack.arguments?.getInt("id") ?: 0,
                initialName = backStack.arguments?.getString("name") ?: "",
                initialSpecie = backStack.arguments?.getString("specie") ?: "",
                initialAge = backStack.arguments?.getInt("age")?.toString() ?: "",
                onBack = { navController.popBackStack() }
            )
        }

        // AGREGAR HOGAR
        composable(PetMatchScreens.AddHome.route) {
            HomeFormScreen(
                viewModel = viewModel(factory = petMatchModule.provideFormFactory()),
                onBack = { navController.popBackStack() }
            )
        }

        // EDITAR HOGAR
        composable(
            route = PetMatchScreens.EditHome.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("name") { type = NavType.StringType },
                navArgument("dir") { type = NavType.StringType },
                navArgument("cap") { type = NavType.IntType },
                navArgument("type") { type = NavType.StringType }
            )
        ) { backStack ->
            HomeFormScreen(
                viewModel = viewModel(factory = petMatchModule.provideFormFactory()),
                homeId = backStack.arguments?.getInt("id") ?: 0,
                initialName = backStack.arguments?.getString("name") ?: "",
                initialDir = backStack.arguments?.getString("dir") ?: "",
                initialCap = backStack.arguments?.getInt("cap")?.toString() ?: "",
                initialType = backStack.arguments?.getString("type") ?: "",
                onBack = { navController.popBackStack() }
            )
        }

        // ASIGNACIÓN
        composable(
            route = PetMatchScreens.AssignPet.route,
            arguments = listOf(
                navArgument("petId") { type = NavType.IntType },
                navArgument("petName") { type = NavType.StringType }
            )
        ) { backStack ->
            AssignPetScreen(
                petId = backStack.arguments?.getInt("petId") ?: 0,
                petName = backStack.arguments?.getString("petName") ?: "",
                formViewModel = viewModel(factory = petMatchModule.provideFormFactory()),
                dashboardViewModel = viewModel(factory = petMatchModule.provideDashboardFactory()),
                onBack = { navController.popBackStack() }
            )
        }
    }
}