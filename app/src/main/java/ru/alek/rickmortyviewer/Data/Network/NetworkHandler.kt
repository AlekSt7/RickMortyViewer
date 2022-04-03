package ru.alek.rickmortyviewer.Data.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.alek.rickmortyviewer.Data.Network.Interfaces.Api.ApiService
object NetworkHandler {

    private val retrofit by lazy { Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build() }

    val api: ApiService
        get(){
            return retrofit.create(ApiService::class.java)
        }

}