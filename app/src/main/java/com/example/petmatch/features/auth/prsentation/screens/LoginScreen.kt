package com.example.petmatch.features.auth.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.example.petmatch.features.auth.presentation.viewmodels.AuthViewModel

@Composable
fun LoginScreen(viewModel: AuthViewModel, onNavToReg: () -> Unit, onSuccess: () -> Unit) {
    val state by viewModel.uiState.collectAsState()
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    if (state.isSuccess) { LaunchedEffect(Unit) { onSuccess() } }

    Column(Modifier.fillMaxSize().padding(24.dp), Arrangement.Center, Alignment.CenterHorizontally) {
        Text("PetMatch", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(32.dp))
        OutlinedTextField(email, { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(pass, { pass = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth())

        state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

        Button({ viewModel.login(email, pass) }, Modifier.fillMaxWidth().padding(top = 24.dp)) {
            if (state.isLoading) CircularProgressIndicator(Modifier.size(24.dp)) else Text("Entrar")
        }
        TextButton(onNavToReg) { Text("Registrarse") }
    }
}