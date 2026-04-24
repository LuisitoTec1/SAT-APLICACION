/*package com.sat.rfc.domain.model

data class PersonaMoral(
    val denominacion: String = "",      // Razón Social en MAYÚSCULAS
    val fechaConstitucion: String = "", // DD/MM/AAAA
    val rfcRepresentanteLegal: String = "",  // 13 caracteres
    val rfcSocios: List<String> = emptyList(), // 12 o 13 caracteres c/u
    val numeroEscritura: String = "",
    val regimenCapital: String = "",    // S.A. de C.V., S.C., etc.
    val actividadEconomica: String = "",
    val domicilioFiscal: DomicilioFiscal = DomicilioFiscal()
)*/

package com.sat.rfc.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PersonaMoral(
    val denominacion: String = "",
    val fechaConstitucion: String = "",
    val rfcRepresentanteLegal: String = "",
    val rfcSocios: List<String> = emptyList(),
    val numeroEscritura: String = "",
    val regimenCapital: String = "",
    val actividadEconomica: String = "",
    val regimenFiscal: String = "",
    val domicilioFiscal: DomicilioFiscal = DomicilioFiscal()
)