package com.sat.rfc.data.local

import com.sat.rfc.db.SatDatabase
import com.sat.rfc.db.RegistroRfc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RegistroLocalDataSource(private val database: SatDatabase) {

    private val queries = database.registroRfcQueries

    suspend fun guardarRegistro(tipoPersona: String, datosJson: String): Long {
        return withContext(Dispatchers.Default) {
            val timestamp = getCurrentTimestamp()
            queries.insertRegistro(
                tipo_persona = tipoPersona,
                datos_json = datosJson,
                fecha_creacion = timestamp,
                completado = 0L
            )
            // Retorna el último ID insertado
            queries.selectAll().executeAsList().firstOrNull()?.id ?: -1L
        }
    }

    suspend fun obtenerTodos(): List<RegistroRfc> {
        return withContext(Dispatchers.Default) {
            queries.selectAll().executeAsList()
        }
    }

    suspend fun obtenerPorId(id: Long): RegistroRfc? {
        return withContext(Dispatchers.Default) {
            queries.selectById(id).executeAsOneOrNull()
        }
    }

    suspend fun marcarComoCompletado(id: Long, datosJson: String) {
        withContext(Dispatchers.Default) {
            queries.updateRegistro(datos_json = datosJson, completado = 1L, id = id)
        }
    }

    suspend fun eliminar(id: Long) {
        withContext(Dispatchers.Default) {
            queries.deleteById(id)
        }
    }

    // expect/actual para timestamp multiplataforma
    private fun getCurrentTimestamp(): Long = System.currentTimeMillis()
}