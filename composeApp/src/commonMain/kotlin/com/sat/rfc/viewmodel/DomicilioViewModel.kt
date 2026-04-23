package com.sat.rfc.viewmodel

import com.sat.rfc.data.repository.SepomexMock
import com.sat.rfc.domain.model.DomicilioFiscal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DomicilioUiState(
    val domicilio: DomicilioFiscal = DomicilioFiscal(),
    val coloniasDisponibles: List<String> = emptyList(),
    val cargandoCP: Boolean = false,
    val errorCP: String? = null
)

class DomicilioViewModel {
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(DomicilioUiState())
    val state: StateFlow<DomicilioUiState> = _state.asStateFlow()

    fun updateCodigoPostal(cp: String) {
        if (cp.length > 5 || !cp.all { it.isDigit() }) return
        _state.update { it.copy(domicilio = it.domicilio.copy(codigoPostal = cp), errorCP = null) }

        if (cp.length == 5) {
            buscarCP(cp)
        } else {
            _state.update {
                it.copy(
                    domicilio = it.domicilio.copy(estado = "", municipio = "", localidad = "", colonia = ""),
                    coloniasDisponibles = emptyList()
                )
            }
        }
    }

    private fun buscarCP(cp: String) {
        scope.launch {
            _state.update { it.copy(cargandoCP = true, errorCP = null) }
            val resultado = SepomexMock.buscarCP(cp)
            if (resultado != null) {
                _state.update { state ->
                    state.copy(
                        domicilio = state.domicilio.copy(
                            estado = resultado.estado,
                            municipio = resultado.municipio,
                            localidad = resultado.localidad,
                            colonia = resultado.colonias.firstOrNull() ?: ""
                        ),
                        coloniasDisponibles = resultado.colonias,
                        cargandoCP = false,
                        errorCP = null
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        cargandoCP = false,
                        errorCP = "CP no encontrado. Verifica en correosdemexico.gob.mx"
                    )
                }
            }
        }
    }

    fun updateColonia(value: String) =
        _state.update { it.copy(domicilio = it.domicilio.copy(colonia = value)) }

    fun updateTipoVialidad(value: String) =
        _state.update { it.copy(domicilio = it.domicilio.copy(tipoVialidad = value)) }

    fun updateNombreVialidad(value: String) =
        _state.update { it.copy(domicilio = it.domicilio.copy(nombreVialidad = value)) }

    fun updateNumeroExterior(value: String) =
        _state.update { it.copy(domicilio = it.domicilio.copy(numeroExterior = value)) }

    fun updateNumeroInterior(value: String) =
        _state.update { it.copy(domicilio = it.domicilio.copy(numeroInterior = value)) }

    fun updateEntreCalle1(value: String) =
        _state.update { it.copy(domicilio = it.domicilio.copy(entreCalle1 = value)) }

    fun updateEntreCalle2(value: String) =
        _state.update { it.copy(domicilio = it.domicilio.copy(entreCalle2 = value)) }

    fun updateReferencia(value: String) =
        _state.update { it.copy(domicilio = it.domicilio.copy(referenciaAdicional = value)) }

    fun updateCaracteristicas(value: String) =
        _state.update { it.copy(domicilio = it.domicilio.copy(caracteristicasDomicilio = value)) }

    fun getDomicilio(): DomicilioFiscal = _state.value.domicilio

    fun validate(): Boolean {
        val d = _state.value.domicilio
        return d.codigoPostal.length == 5
                && d.colonia.isNotBlank()
                && d.tipoVialidad.isNotBlank()
                && d.nombreVialidad.isNotBlank()
                && d.numeroExterior.isNotBlank()
    }
}