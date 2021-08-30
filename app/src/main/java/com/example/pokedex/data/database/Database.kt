package com.example.pokedex.data.database

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

object Database {

    private val NAME_DATABASE = "POKEMON_DISK"
    private lateinit var realmConfiguration: RealmConfiguration

    fun init(context: Context) {
        Realm.init(context)
        loadDatabase()
    }

    fun getInstance(): Realm {
        return Realm.getInstance(realmConfiguration)
    }

    private fun loadDatabase() {
        if (!::realmConfiguration.isInitialized) {
            realmConfiguration = RealmConfiguration.Builder().apply {
                name(NAME_DATABASE)
                modules(RealmModule())
                migration(Migration())
                schemaVersion(Migration.DATABASE_VERSION)
            }.build()
        }
    }
}