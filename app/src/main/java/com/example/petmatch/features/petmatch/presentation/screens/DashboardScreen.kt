package com.example.petmatch.features.petmatch.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.petmatch.features.petmatch.presentation.components.HomeCard
import com.example.petmatch.features.petmatch.presentation.components.PetCard
import com.example.petmatch.features.petmatch.presentation.viewmodels.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onNavigateToAddPet: () -> Unit // <--- AGREGAMOS ESTE PARÁMETRO QUE FALTABA
) {
    val state by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Mascotas", "Hogares")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("PetMatch", color = Color.White) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        // Agregamos un botón flotante para navegar al formulario de mascotas
        floatingActionButton = {
            if (selectedTab == 0) { // Solo mostrar si estamos en la pestaña de Mascotas
                FloatingActionButton(
                    onClick = onNavigateToAddPet, // Usamos el parámetro aquí
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar Mascota")
                }
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
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
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (selectedTab == 0) {
                        items(state.mascotas) { pet ->
                            PetCard(pet = pet)
                        }
                    } else {
                        items(state.hogares) { home ->
                            HomeCard(home = home)
                        }
                    }
                }
            }
        }
    }
}