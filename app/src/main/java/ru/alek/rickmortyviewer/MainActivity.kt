package ru.alek.rickmortyviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import ru.alek.rickmortyviewer.Data.Network.NetworkHandler

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_RickMortyViewer)
        setContentView(R.layout.activity_main)
    }

}