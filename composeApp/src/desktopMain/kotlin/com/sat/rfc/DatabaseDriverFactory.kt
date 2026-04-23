package com.sat.rfc

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.sat.rfc.db.SatDatabase
import java.io.File

class DatabaseDriverFactory {
    fun createDriver(): SqlDriver {
        val dbPath = File(System.getProperty("user.home"), ".sat_rfc/sat_rfc.db")
        dbPath.parentFile?.mkdirs()
        val dbExists = dbPath.exists()
        val driver = JdbcSqliteDriver("jdbc:sqlite:${dbPath.absolutePath}")
        if (!dbExists) {
            SatDatabase.Schema.create(driver)
        }
        return driver
    }
}

