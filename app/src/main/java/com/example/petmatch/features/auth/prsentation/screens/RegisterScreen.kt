package com.example.petmatch.features.auth.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.petmatch.features.auth.presentation.viewmodels.AuthViewModel

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    if (state.isSuccess) {
        LaunchedEffect(Unit) { onRegisterSuccess() }
    }

    Column(
        Modifier.fillMaxSize().padding(24.dp),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Text("Registro", style = MaterialTheme.typography.headlineLarge)

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre Completo") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(email, { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(password, { password = it }, label = { Text("Contraseña") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(telefono, { telefono = it }, label = { Text("Teléfono") }, modifier = Modifier.fillMaxWidth())

        if (state.error != null) {
            Text(state.error!!, color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = { viewModel.register(nombre, email, password, telefono) },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            enabled = !state.isLoading
        ) {
            Text("Crear Cuenta")
        }

        TextButton(onClick = onNavigateToLogin) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}