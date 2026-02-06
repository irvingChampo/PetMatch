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
    petId: Int = 0,
    initialName: String = "",
    initialSpecie: String = "Perro",
    initialAge: String = "",
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var nombre by remember { mutableStateOf(initialName) }
    var edad by remember { mutableStateOf(initialAge) }
    var especieSeleccionada by remember { mutableStateOf(if(initialSpecie.isEmpty()) "Perro" else initialSpecie) }
    var expanded by remember { mutableStateOf(false) }

    if (state.isSuccess) {
        LaunchedEffect(Unit) { viewModel.resetState(); onBack() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (petId == 0) "Nueva Mascota" else "Editar Mascota", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(24.dp)) {
            OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))

            ExposedDropdownMenuBox(expanded, { expanded = !expanded }, Modifier.fillMaxWidth()) {
                OutlinedTextField(especieSeleccionada, {}, readOnly = true, label = { Text("Especie") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) }, modifier = Modifier.menuAnchor().fillMaxWidth())
                ExposedDropdownMenu(expanded, { expanded = false }) {
                    listOf("Perro", "Gato", "Otro").forEach {
                        DropdownMenuItem(text = { Text(it) }, onClick = { especieSeleccionada = it; expanded = false })
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(edad, { if (it.all { c -> c.isDigit() }) edad = it }, label = { Text("Edad") }, modifier = Modifier.fillMaxWidth())

            if (state.error != null) Text(state.error!!, color = Color.Red, modifier = Modifier.padding(top = 8.dp))

            Button(
                onClick = { viewModel.savePet(nombre, especieSeleccionada, edad, petId) },
                modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
                enabled = !state.isLoading
            ) {
                if (state.isLoading) CircularProgressIndicator(Modifier.size(24.dp), color = Color.White)
                else Text(if (petId == 0) "Guardar" else "Actualizar")
            }
        }
    }
}