package ru.alek.rickmortyviewer.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.alek.rickmortyviewer.data.network.Interfaces.api.ApiService
import ru.alek.rickmortyviewer.MainUtils

object NetworkHandler {

    private val retrofit by lazy { Retrofit.Builder()
        .baseUrl(MainUtils.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build() }

        val api: ApiService
            get() = retrofit.create(ApiService::class.java)

}