package com.example.petmatch.core.navigation

sealed class PetMatchScreens(val route: String) {
    object Login : PetMatchScreens("login")
    object Register : PetMatchScreens("register")
    object Dashboard : PetMatchScreens("dashboard")
    object AddPet : PetMatchScreens("add_pet")
}