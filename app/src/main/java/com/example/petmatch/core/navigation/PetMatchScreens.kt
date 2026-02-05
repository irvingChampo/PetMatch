package com.example.petmatch.core.navigation

sealed class PetMatchScreens(val route: String) {
    object Login : PetMatchScreens("login")
    object Register : PetMatchScreens("register")
    object Dashboard : PetMatchScreens("dashboard")
    object AddPet : PetMatchScreens("add_pet")
    object AddHome : PetMatchScreens("add_home")
    // Ruta con argumentos para la asignación
    object AssignPet : PetMatchScreens("assign_pet/{petId}/{petName}") {
        fun createRoute(petId: Int, petName: String) = "assign_pet/$petId/$petName"
    }
}