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

data class PersonaMoral(
    val denominacion: String = "",          // Razón Social en MAYÚSCULAS (validada por SE)
    val fechaConstitucion: String = "",     // DD/MM/AAAA (dato del Acta Constitutiva)
    val rfcRepresentanteLegal: String = "", // 13 caracteres alfanumérico
    val rfcSocios: List<String> = emptyList(), // 12 o 13 caracteres c/u
    val numeroEscritura: String = "",       // Alfanumérico
    val regimenCapital: String = "",        // S.A. de C.V., S.C., A.C., etc.
    val actividadEconomica: String = "",
    val regimenFiscal: String = "",         // ← AGREGADO (faltaba en el modelo original)
    val domicilioFiscal: DomicilioFiscal = DomicilioFiscal()
)