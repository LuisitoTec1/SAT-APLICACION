/*package com.sat.rfc.viewmodel

import com.sat.rfc.domain.model.DomicilioFiscal
import com.sat.rfc.domain.model.PersonaMoral
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PersonaMoralViewModel {
    private val _state = MutableStateFlow(PersonaMoral())
    val state: StateFlow<PersonaMoral> = _state.asStateFlow()

    private val _errors = MutableStateFlow<Map<String, String>>(emptyMap())
    val errors: StateFlow<Map<String, String>> = _errors.asStateFlow()

    // El campo de RFC de socios es una lista editable
    private val _rfcSociosInput = MutableStateFlow("")
    val rfcSociosInput: StateFlow<String> = _rfcSociosInput.asStateFlow()

    fun updateDenominacion(value: String) =
        _state.update { it.copy(denominacion = value.uppercase()) }

    fun updateFechaConstitucion(value: String) =
        _state.update { it.copy(fechaConstitucion = value) }

    fun updateRfcRepresentante(value: String) {
        val upper = value.uppercase().take(13)
        _state.update { it.copy(rfcRepresentanteLegal = upper) }
        if (upper.length == 13) {
            val rfcRegex = Regex("^[A-Z&Ñ]{3,4}\\d{6}[A-Z0-9]{3}$")
            setError("rfcRepresentante", if (!rfcRegex.matches(upper)) "RFC inválido (13 caracteres)" else null)
        } else {
            clearError("rfcRepresentante")
        }
    }

    fun updateRfcSociosInput(value: String) {
        _rfcSociosInput.value = value.uppercase()
    }

    fun agregarSocio() {
        val rfc = _rfcSociosInput.value.trim()
        val rfcRegex = Regex("^[A-Z&Ñ]{3,4}\\d{6}[A-Z0-9]{3}$")
        if (rfc.length in 12..13 && rfcRegex.matches(rfc)) {
            val lista = _state.value.rfcSocios.toMutableList()
            if (!lista.contains(rfc)) {
                lista.add(rfc)
                _state.update { it.copy(rfcSocios = lista) }
            }
            _rfcSociosInput.value = ""
            clearError("rfcSocios")
        } else {
            setError("rfcSocios", "RFC inválido. Debe tener 12 o 13 caracteres alfanuméricos")
        }
    }

    fun eliminarSocio(rfc: String) {
        _state.update { it.copy(rfcSocios = it.rfcSocios.filter { s -> s != rfc }) }
    }

    fun updateNumeroEscritura(value: String) =
        _state.update { it.copy(numeroEscritura = value) }

    fun updateRegimenCapital(value: String) =
        _state.update { it.copy(regimenCapital = value) }

    fun updateActividadEconomica(value: String) =
        _state.update { it.copy(actividadEconomica = value) }

    fun updateDomicilio(domicilio: DomicilioFiscal) =
        _state.update { it.copy(domicilioFiscal = domicilio) }

    fun validate(): Boolean {
        val s = _state.value
        val newErrors = mutableMapOf<String, String>()

        if (s.denominacion.isBlank()) newErrors["denominacion"] = "La razón social es requerida"
        if (s.fechaConstitucion.isBlank()) newErrors["fechaConstitucion"] = "La fecha de constitución es requerida"
        if (s.rfcRepresentanteLegal.length != 13) newErrors["rfcRepresentante"] = "RFC del representante debe tener 13 caracteres"
        if (s.numeroEscritura.isBlank()) newErrors["numeroEscritura"] = "El número de escritura es requerido"
        if (s.regimenCapital.isBlank()) newErrors["regimenCapital"] = "Selecciona el régimen de capital"
        if (s.actividadEconomica.isBlank()) newErrors["actividadEconomica"] = "Selecciona una actividad económica"

        _errors.value = newErrors
        return newErrors.isEmpty()
    }

    private fun setError(key: String, message: String?) {
        _errors.update { if (message == null) it - key else it + (key to message) }
    }

    private fun clearError(key: String) {
        _errors.update { it - key }
    }
}*/
package com.sat.rfc.viewmodel

import com.sat.rfc.domain.model.DomicilioFiscal
import com.sat.rfc.domain.model.PersonaMoral
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PersonaMoralViewModel {
    private val _state = MutableStateFlow(PersonaMoral())
    val state: StateFlow<PersonaMoral> = _state.asStateFlow()

    private val _errors = MutableStateFlow<Map<String, String>>(emptyMap())
    val errors: StateFlow<Map<String, String>> = _errors.asStateFlow()

    private val _rfcSociosInput = MutableStateFlow("")
    val rfcSociosInput: StateFlow<String> = _rfcSociosInput.asStateFlow()

    fun updateDenominacion(value: String) =
        _state.update { it.copy(denominacion = value.uppercase()) }

    fun updateFechaConstitucion(value: String) =
        _state.update { it.copy(fechaConstitucion = value) }

    fun updateRfcRepresentante(value: String) {
        val upper = value.uppercase().take(13)
        _state.update { it.copy(rfcRepresentanteLegal = upper) }
        if (upper.length == 13) {
            val rfcRegex = Regex("^[A-Z&Ñ]{3,4}\\d{6}[A-Z0-9]{3}$")
            setError("rfcRepresentante",
                if (!rfcRegex.matches(upper)) "RFC inválido (13 caracteres)" else null)
        } else {
            clearError("rfcRepresentante")
        }
    }

    fun updateRfcSociosInput(value: String) {
        _rfcSociosInput.value = value.uppercase()
    }

    fun agregarSocio() {
        val rfc = _rfcSociosInput.value.trim()
        val rfcRegex = Regex("^[A-Z&Ñ]{3,4}\\d{6}[A-Z0-9]{3}$")
        if (rfc.length in 12..13 && rfcRegex.matches(rfc)) {
            val lista = _state.value.rfcSocios.toMutableList()
            if (!lista.contains(rfc)) lista.add(rfc)
            _state.update { it.copy(rfcSocios = lista) }
            _rfcSociosInput.value = ""
            clearError("rfcSocios")
        } else {
            setError("rfcSocios", "RFC inválido. Debe tener 12 o 13 caracteres alfanuméricos")
        }
    }

    fun eliminarSocio(rfc: String) =
        _state.update { it.copy(rfcSocios = it.rfcSocios.filter { s -> s != rfc }) }

    fun updateNumeroEscritura(value: String) =
        _state.update { it.copy(numeroEscritura = value) }

    fun updateRegimenCapital(value: String) =
        _state.update { it.copy(regimenCapital = value) }

    fun updateActividadEconomica(value: String) =
        _state.update { it.copy(actividadEconomica = value) }

    // ← MÉTODO AGREGADO — faltaba completamente
    fun updateRegimenFiscal(value: String) =
        _state.update { it.copy(regimenFiscal = value) }

    fun updateDomicilio(domicilio: DomicilioFiscal) =
        _state.update { it.copy(domicilioFiscal = domicilio) }

    fun validate(): Boolean {
        val s = _state.value
        val newErrors = mutableMapOf<String, String>()

        if (s.denominacion.isBlank())
            newErrors["denominacion"] = "La razón social es requerida"
        if (s.fechaConstitucion.isBlank())
            newErrors["fechaConstitucion"] = "La fecha de constitución es requerida"
        if (s.rfcRepresentanteLegal.length != 13)
            newErrors["rfcRepresentante"] = "RFC del representante debe tener 13 caracteres"
        if (s.numeroEscritura.isBlank())
            newErrors["numeroEscritura"] = "El número de escritura es requerido"
        if (s.regimenCapital.isBlank())
            newErrors["regimenCapital"] = "Selecciona el régimen de capital"
        if (s.actividadEconomica.isBlank())
            newErrors["actividadEconomica"] = "Selecciona una actividad económica"
        if (s.regimenFiscal.isBlank())                     // ← AGREGADO a la validación
            newErrors["regimenFiscal"] = "Selecciona un régimen fiscal"

        _errors.value = newErrors
        return newErrors.isEmpty()
    }

    private fun setError(key: String, message: String?) =
        _errors.update { if (message == null) it - key else it + (key to message) }

    private fun clearError(key: String) =
        _errors.update { it - key }
}