package com.sat.rfc.viewmodel

import com.sat.rfc.domain.model.DomicilioFiscal
import com.sat.rfc.domain.model.PersonaFisica
import com.sat.rfc.domain.usecase.ValidarCurpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PersonaFisicaViewModel(
    private val validarCurp: ValidarCurpUseCase = ValidarCurpUseCase()
) {
    private val _state = MutableStateFlow(PersonaFisica())
    val state: StateFlow<PersonaFisica> = _state.asStateFlow()

    private val _errors = MutableStateFlow<Map<String, String>>(emptyMap())
    val errors: StateFlow<Map<String, String>> = _errors.asStateFlow()

    fun updateCurp(value: String) {
        val upper = value.uppercase().take(18)
        _state.update { it.copy(curp = upper) }
        if (upper.length == 18) {
            val result = validarCurp(upper)
            setError("curp", result.errorMessage)
        } else {
            clearError("curp")
        }
    }

    fun updateNombres(value: String) =
        _state.update { it.copy(nombres = value.uppercase()) }

    fun updateApellidoPaterno(value: String) =
        _state.update { it.copy(apellidoPaterno = value.uppercase()) }

    fun updateApellidoMaterno(value: String) =
        _state.update { it.copy(apellidoMaterno = value.uppercase()) }

    fun updateFechaNacimiento(value: String) =
        _state.update { it.copy(fechaNacimiento = value) }

    fun updateCorreo(value: String) {
        _state.update { it.copy(correoElectronico = value) }
        if (value.isNotEmpty()) {
            val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
            setError("correo", if (!emailRegex.matches(value)) "Correo electrónico inválido" else null)
        } else {
            clearError("correo")
        }
    }

    fun updateTelefono(value: String) {
        if (value.length <= 10 && value.all { it.isDigit() })
            _state.update { it.copy(telefono = value) }
    }

    fun updateActividadEconomica(value: String) =
        _state.update { it.copy(actividadEconomica = value) }

    fun updateRegimenFiscal(value: String) =
        _state.update { it.copy(regimenFiscal = value) }

    fun updateDomicilio(domicilio: DomicilioFiscal) =
        _state.update { it.copy(domicilioFiscal = domicilio) }

    fun validate(): Boolean {
        val s = _state.value
        val newErrors = mutableMapOf<String, String>()

        val curpResult = validarCurp(s.curp)
        if (!curpResult.isValid) newErrors["curp"] = curpResult.errorMessage ?: "CURP inválida"
        if (s.nombres.isBlank()) newErrors["nombres"] = "El nombre es requerido"
        if (s.apellidoPaterno.isBlank()) newErrors["apellidoPaterno"] = "El apellido paterno es requerido"
        if (s.fechaNacimiento.isBlank()) newErrors["fechaNacimiento"] = "La fecha de nacimiento es requerida"
        if (s.actividadEconomica.isBlank()) newErrors["actividadEconomica"] = "Selecciona una actividad económica"
        if (s.regimenFiscal.isBlank()) newErrors["regimenFiscal"] = "Selecciona un régimen fiscal"

        _errors.value = newErrors
        return newErrors.isEmpty()
    }

    private fun setError(key: String, message: String?) {
        _errors.update { if (message == null) it - key else it + (key to message) }
    }

    private fun clearError(key: String) {
        _errors.update { it - key }
    }
}