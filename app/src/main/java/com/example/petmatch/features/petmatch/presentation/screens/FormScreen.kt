package com.example.petmatch.features.petmatch.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.petmatch.features.petmatch.data.model.MascotaDto

@Composable
fun AddPetScreen(onSave: (MascotaDto) -> Unit, onBack: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var especie by remember { mutableStateOf("Perro") }
    var edad by remember { mutableStateOf("") }

    Column(Modifier.padding(24.dp)) {
        Text("Registrar Mascota", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(24.dp))

        OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(especie, { especie = it }, label = { Text("Especie") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(edad, { edad = it }, label = { Text("Edad") }, modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                onSave(MascotaDto(0, nombre, especie, edad.toIntOrNull() ?: 0, "Saludable", "Sin hogar", null))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Mascota")
        }
        TextButton(onBack, modifier = Modifier.fillMaxWidth()) { Text("Cancelar") }
    }
}