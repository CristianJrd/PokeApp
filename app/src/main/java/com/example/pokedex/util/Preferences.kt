package com.example.pokedex.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object Preferences {

    private val MODE = Context.MODE_PRIVATE

    private lateinit var preferences: SharedPreferences

    private val ENCRYPT_KEY_DATABASE = "TEMPORARY_INFO_STRING"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(context.packageName, MODE)
    }

    var encryptKeyDatabase: String
        get() = preferences.getString(ENCRYPT_KEY_DATABASE, "")!!
        set(value) = preferences.edit {
            putString(ENCRYPT_KEY_DATABASE, value)
        }
}