package com.example.petmatch.features.petmatch.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.petmatch.features.petmatch.presentation.viewmodels.FormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFormScreen(
    viewModel: FormViewModel,
    homeId: Int = 0,
    initialName: String = "",
    initialDir: String = "",
    initialCap: String = "",
    initialType: String = "Perros",
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    // Inicializamos con los valores que vienen de la navegación
    var nombre by remember { mutableStateOf(initialName) }
    var direccion by remember { mutableStateOf(initialDir) }
    var telefono by remember { mutableStateOf("") }
    var capacidad by remember { mutableStateOf(initialCap) }

    val opcionesTipo = listOf("Perros", "Gatos", "Ambos")
    var expanded by remember { mutableStateOf(false) }
    var tipoSeleccionado by remember { mutableStateOf(if(initialType.isEmpty()) opcionesTipo[0] else initialType) }

    if (state.isSuccess) {
        LaunchedEffect(Unit) {
            viewModel.resetState()
            onBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (homeId == 0) "Registrar Hogar" else "Editar Hogar", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre del Voluntario") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(direccion, { direccion = it }, label = { Text("Dirección") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(telefono, { telefono = it }, label = { Text("Confirmar Teléfono") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(capacidad, { if (it.all { c -> c.isDigit() }) capacidad = it }, label = { Text("Capacidad Máxima") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))

            ExposedDropdownMenuBox(expanded, { expanded = !expanded }, Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = tipoSeleccionado,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de Mascota Aceptada") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded, { expanded = false }) {
                    opcionesTipo.forEach { tipo ->
                        DropdownMenuItem(text = { Text(tipo) }, onClick = { tipoSeleccionado = tipo; expanded = false })
                    }
                }
            }

            state.error?.let { Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp)) }

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = { viewModel.saveHome(nombre, direccion, telefono, capacidad, tipoSeleccionado, homeId) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ) {
                if (state.isLoading) CircularProgressIndicator(Modifier.size(24.dp), color = Color.White)
                else Text(if (homeId == 0) "Guardar Hogar" else "Actualizar Hogar")
            }

            TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("Cancelar")
            }
        }
    }
}