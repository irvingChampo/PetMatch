package com.example.petmatch.features.petmatch.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.petmatch.features.petmatch.domain.entities.Pet

@Composable
fun PetCard(pet: Pet) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(pet.nombre, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text("Especie: ${pet.especie} • ${pet.edad} años")
            Text("Salud: ${pet.estadoSalud}")
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                color = if(pet.estado == "Sin hogar") Color(0xFFE63946) else Color(0xFF2D6A4F),
                shape = MaterialTheme.shapes.small
            ) {
                Text(pet.estado, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), color = Color.White)
            }
        }
    }
}