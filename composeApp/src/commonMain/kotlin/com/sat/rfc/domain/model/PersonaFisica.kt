/*package com.sat.rfc.domain.model

data class PersonaFisica(
    val curp: String = "",
    val nombres: String = "",
    val apellidoPaterno: String = "",
    val apellidoMaterno: String = "",
    val fechaNacimiento: String = "",   // DD/MM/AAAA
    val correoElectronico: String = "",
    val telefono: String = "",
    val actividadEconomica: String = "",
    val regimenFiscal: String = "",
    val domicilioFiscal: DomicilioFiscal = DomicilioFiscal()
)*/

package com.sat.rfc.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PersonaFisica(
    val curp: String = "",
    val nombres: String = "",
    val apellidoPaterno: String = "",
    val apellidoMaterno: String = "",
    val fechaNacimiento: String = "",
    val correoElectronico: String = "",
    val telefono: String = "",
    val actividadEconomica: String = "",
    val regimenFiscal: String = "",
    val domicilioFiscal: DomicilioFiscal = DomicilioFiscal()
)