package com.example.pokedex.data.database

import io.realm.DynamicRealm
import io.realm.RealmMigration

open class Migration : RealmMigration {

    companion object {
        const val DATABASE_VERSION = 1L
    }

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val schema = realm.schema
        if (oldVersion == 0L) {
            //oldVersion++
        }
    }
}