package com.example.petmatch.features.petmatch.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.petmatch.features.petmatch.presentation.viewmodels.DashboardViewModel
import com.example.petmatch.features.petmatch.data.model.*
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val state by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Mascota", "Hogar")

    Column(Modifier.fillMaxSize()) {
        // Header con estilo PetMatch
        CenterAlignedTopAppBar(
            title = { Text("PetMatch", color = Color.White) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )

        // Tabs de navegación
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(Modifier.fillMaxSize().padding(8.dp)) {
                if (selectedTab == 0) {
                    items(state.mascotas) { mascota -> PetCard(mascota) }
                } else {
                    items(state.hogares) { hogar -> HomeCard(hogar) }
                }
            }
        }
    }
}

@Composable
fun PetCard(mascota: MascotaDto) {
    Card(Modifier.fillMaxWidth().padding(8.dp)) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            // Aquí iría la imagen con Coil
            Box(Modifier.size(80.dp).background(Color.LightGray))
            Spacer(Modifier.width(16.dp))
            Column {
                Text(mascota.nombre, style = MaterialTheme.typography.titleLarge)
                Text("Especie: ${mascota.especie}")
                Text("Estado: ${mascota.estadoSalud}", color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Composable
fun HomeCard(hogar: HogarDto) {
    Card(Modifier.fillMaxWidth().padding(8.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(Modifier.padding(16.dp)) {
            Text(hogar.nombreVoluntario, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            Text(hogar.direccion)
            Text("Capacidad: ${hogar.ocupacionActual}/${hogar.capacidad} animales")
            Text("Acepta: ${hogar.tipoMascotaAceptada}")
        }
    }
}