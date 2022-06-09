package ru.alek.rickmortyviewer

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import ru.alek.rickmortyviewer.data.network.NetworkHandler

class App: Application() {

    companion object{

        lateinit var instance: App
            private set

        fun hasConnection() = instance.isNetworkConnected()

        private val api by lazy { NetworkHandler.api }

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    //from StackOverflow
    private fun isNetworkConnected(): Boolean {
        var result = false
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //If SDK higher or equals API 23
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }

    fun getApi() = api

}