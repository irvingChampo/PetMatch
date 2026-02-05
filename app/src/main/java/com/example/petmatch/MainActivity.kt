package com.example.petmatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.petmatch.core.di.AppContainer
import com.example.petmatch.features.auth.di.AuthModule
import com.example.petmatch.features.petmatch.di.PetMatchModule
import com.example.petmatch.features.auth.presentation.screens.LoginScreen
import com.example.petmatch.features.petmatch.presentation.screens.DashboardScreen
import com.example.petmatch.features.petmatch.presentation.screens.AddPetScreen
import com.example.petmatch.ui.theme.PetMatchTheme
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = AppContainer(this)
        val authModule = AuthModule(appContainer)
        val petMatchModule = PetMatchModule(appContainer)

        setContent {
            PetMatchTheme {
                PetMatchAppNavigation(authModule, petMatchModule)
            }
        }
    }
}

@Composable
fun PetMatchAppNavigation(authModule: AuthModule, petModule: PetMatchModule) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        // Pantalla de Login
        composable("login") {
            LoginScreen(
                viewModel = viewModel(factory = authModule.provideFactory()),
                onNavToReg = { /* Implementar similar a login */ },
                onSuccess = { navController.navigate("dashboard") }
            )
        }

        // Pantalla Principal (Dashboard)
        composable("dashboard") {
            DashboardScreen(
                viewModel = viewModel(factory = petModule.provideFactory()),
                // Aquí podrías agregar botones en el Dashboard para navegar a los formularios:
                // onAddPet = { navController.navigate("add_pet") }
            )
        }

        // Pantalla de Formulario
        composable("add_pet") {
            AddPetScreen(
                onSave = { /* Llamar al viewModel para guardar y luego volver */
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}