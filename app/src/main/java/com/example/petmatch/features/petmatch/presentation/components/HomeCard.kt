package com.example.petmatch.features.petmatch.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.petmatch.features.petmatch.domain.entities.Home

@Composable
fun HomeCard(home: Home) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(home.nombreVoluntario, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            Text(home.direccion)
            Text("Capacidad: ${home.ocupacionActual} / ${home.capacidad} mascotas")
            Text("Acepta: ${home.tipoMascotaAceptada}")
        }
    }
}