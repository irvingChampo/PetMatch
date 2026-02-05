package com.example.petmatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.petmatch.core.di.AppContainer
import com.example.petmatch.core.navigation.AppNavigation
import com.example.petmatch.features.auth.di.AuthModule
import com.example.petmatch.features.petmatch.di.PetMatchModule
import com.example.petmatch.ui.theme.PetMatchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización de módulos (DI Manual estilo profesor)
        val appContainer = AppContainer(this)
        val authModule = AuthModule(appContainer)
        val petMatchModule = PetMatchModule(appContainer)

        setContent {
            PetMatchTheme {
                // Delegamos todo a la clase de navegación
                AppNavigation(authModule, petMatchModule)
            }
        }
    }
}