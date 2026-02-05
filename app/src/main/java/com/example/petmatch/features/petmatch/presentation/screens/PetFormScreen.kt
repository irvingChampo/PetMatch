package com.example.petmatch.features.petmatch.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.petmatch.features.petmatch.presentation.viewmodels.FormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetFormScreen(
    viewModel: FormViewModel,
    onBack: () -> Unit
) {
    // Obtenemos el estado del ViewModel
    val state by viewModel.uiState.collectAsState()

    // Estados locales para los campos del formulario
    var nombre by remember { mutableStateOf("") }
    var especie by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }

    // Si la operación fue exitosa, regresamos a la pantalla anterior
    if (state.isSuccess) {
        LaunchedEffect(Unit) {
            viewModel.resetState() // Limpiamos el estado antes de volver
            onBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Mascota", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre de la mascota") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = especie,
                onValueChange = { especie = it },
                label = { Text("Especie (Perro, Gato...)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = edad,
                onValueChange = { edad = it },
                label = { Text("Edad aproximada") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Mostrar error si existe
            state.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                // CAMBIAMOS 'createPet' por 'savePet' para que coincida con el ViewModel
                onClick = { viewModel.savePet(nombre, especie, edad) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading,
                shape = MaterialTheme.shapes.medium
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Guardar Mascota")
                }
            }

            TextButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text("Cancelar")
            }
        }
    }
}