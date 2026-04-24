/*package com.sat.rfc.domain.model

data class DomicilioFiscal(
    val codigoPostal: String = "",      // 5 dígitos — autocompleta Estado/Municipio
    val estado: String = "",            // Autocompletado por CP
    val municipio: String = "",         // Autocompletado por CP
    val localidad: String = "",         // Autocompletado por CP
    val colonia: String = "",           // Lista de opciones según CP (SEPOMEX)
    val tipoVialidad: String = "",      // Catálogo SAT: Calle, Avenida, etc.
    val nombreVialidad: String = "",    // Nombre de la calle (alfanumérico)
    val numeroExterior: String = "",    // Ej: "12", "Mz 1", "S/N"
    val numeroInterior: String = "",    // Opcional: Depto, Oficina, Local
    val entreCalle1: String = "",       // Referencia vial
    val entreCalle2: String = "",       // Referencia vial
    val referenciaAdicional: String = "", // Ej: "Frente al parque"
    val caracteristicasDomicilio: String = "" // Descripción física del edificio
)*/

package com.sat.rfc.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DomicilioFiscal(
    val codigoPostal: String = "",
    val estado: String = "",
    val municipio: String = "",
    val localidad: String = "",
    val colonia: String = "",
    val tipoVialidad: String = "",
    val nombreVialidad: String = "",
    val numeroExterior: String = "",
    val numeroInterior: String = "",
    val entreCalle1: String = "",
    val entreCalle2: String = "",
    val referenciaAdicional: String = "",
    val caracteristicasDomicilio: String = ""
)