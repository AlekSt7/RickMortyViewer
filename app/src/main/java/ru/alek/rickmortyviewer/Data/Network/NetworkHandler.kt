package ru.alek.rickmortyviewer.Data.Network

import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.alek.rickmortyviewer.Data.Network.Interfaces.Api.ApiService
import java.io.File
import java.util.concurrent.TimeUnit

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