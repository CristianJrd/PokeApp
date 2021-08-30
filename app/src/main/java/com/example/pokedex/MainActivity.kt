package com.example.pokedex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedex.data.database.Database
import com.example.pokedex.util.Preferences

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ini()
    }

    private fun ini() {
        Preferences.init(applicationContext)
        Database.init(applicationContext)
    }


}