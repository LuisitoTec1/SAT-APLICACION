package com.sat.rfc

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.sat.rfc.db.SatDatabase



class DatabaseDriverFactory(private val context: Context) {
    fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = SatDatabase.Schema,
            context = context,
            name = "sat_rfc.db"
        )
    }
}