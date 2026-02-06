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
import com.example.petmatch.features.petmatch.presentation.viewmodels.FormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    formViewModel: FormViewModel,
    onNavigateToAddPet: () -> Unit,
    onNavigateToAddHome: () -> Unit,
    onNavigateToEditPet: (Int, String, String, Int) -> Unit,
    onNavigateToEditHome: (Int, String, String, Int, String) -> Unit,
    onNavigateToAssign: (Int, String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    // Estados para controlar el diálogo de eliminación
    var showDeleteDialog by remember { mutableStateOf(false) }
    var itemToDeleteId by remember { mutableIntStateOf(-1) }

    // Recargar datos cada vez que la pantalla vuelve a estar activa
    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    // Diálogo de confirmación para eliminar
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar este registro? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (selectedTab == 0) {
                            formViewModel.deletePet(itemToDeleteId)
                        } else {
                            formViewModel.deleteHome(itemToDeleteId)
                        }
                        showDeleteDialog = false
                        // Pequeño retraso para que la API procese y luego recargamos
                        viewModel.loadData()
                    }
                ) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("PetMatch", color = Color.White) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (selectedTab == 0) onNavigateToAddPet() else onNavigateToAddHome()
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                listOf("Mascotas", "Hogares").forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    // CORRECCIÓN DE LOS PARÁMETROS AQUÍ:
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (selectedTab == 0) {
                        items(state.mascotas) { pet ->
                            PetCard(
                                pet = pet,
                                onEdit = {
                                    onNavigateToEditPet(it.id, it.nombre, it.especie, it.edad)
                                },
                                onDelete = { id ->
                                    itemToDeleteId = id
                                    showDeleteDialog = true
                                },
                                onAssignClick = onNavigateToAssign
                            )
                        }
                    } else {
                        items(state.hogares) { home ->
                            HomeCard(
                                home = home,
                                onEdit = {
                                    onNavigateToEditHome(
                                        it.id,
                                        it.nombreVoluntario,
                                        it.direccion,
                                        it.capacidad,
                                        it.tipoMascotaAceptada
                                    )
                                },
                                onDelete = { id ->
                                    itemToDeleteId = id
                                    showDeleteDialog = true
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}