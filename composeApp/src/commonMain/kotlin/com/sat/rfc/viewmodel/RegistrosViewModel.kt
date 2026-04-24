package com.sat.rfc.viewmodel

import com.sat.rfc.data.local.RegistroGuardado
import com.sat.rfc.data.local.RegistroLocalDataSource
import com.sat.rfc.domain.model.DomicilioFiscal
import com.sat.rfc.domain.model.PersonaFisica
import com.sat.rfc.domain.model.PersonaMoral
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

data class RegistrosUiState(
    val registros: List<RegistroGuardado> = emptyList(),
    val cargando: Boolean = true,
    val error: String? = null
)

class RegistrosViewModel(
    private val dataSource: RegistroLocalDataSource
) {
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(RegistrosUiState())
    val state: StateFlow<RegistrosUiState> = _state.asStateFlow()

    init {
        cargarRegistros()
    }

    fun cargarRegistros() {
        scope.launch {
            _state.value = RegistrosUiState(cargando = true)
            try {
                val lista = dataSource.obtenerTodos()
                _state.value = RegistrosUiState(registros = lista, cargando = false)
            } catch (e: Exception) {
                _state.value = RegistrosUiState(
                    cargando = false,
                    error = "Error al cargar registros: ${e.message}"
                )
            }
        }
    }

    fun eliminarRegistro(id: Long) {
        scope.launch {
            dataSource.eliminar(id)
            cargarRegistros()
        }
    }

    // Deserializa el JSON guardado para mostrar los datos
    fun parsearFisica(json: String): PersonaFisica? = try {
        Json.decodeFromString<PersonaFisica>(json)
    } catch (e: Exception) { null }

    fun parsearMoral(json: String): PersonaMoral? = try {
        Json.decodeFromString<PersonaMoral>(json)
    } catch (e: Exception) { null }
}

