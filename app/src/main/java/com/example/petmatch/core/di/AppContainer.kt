package com.example.petmatch.core.di

import android.content.Context
import com.example.petmatch.core.network.PetMatchApi
import com.example.petmatch.features.petmatch.data.repositories.PetMatchRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {
    private val baseUrl = "https://backend-petmatch-api.onrender.com/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val petMatchApi: PetMatchApi by lazy {
        retrofit.create(PetMatchApi::class.java)
    }

    val petMatchRepository: PetMatchRepositoryImpl by lazy {
        PetMatchRepositoryImpl(petMatchApi)
    }
}