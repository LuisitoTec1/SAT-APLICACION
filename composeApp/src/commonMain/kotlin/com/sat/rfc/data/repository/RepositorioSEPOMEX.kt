package com.sat.rfc.data.repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable

@Serializable
data class SepomexResponse(
    val codigoPostal: String,
    val estado: String,
    val municipio: String,
    val localidad: String,
    val colonias: List<String>
)

class SepomexRepository(private val client: HttpClient) {

    // Para producción: registra token gratuito en https://copomex.com
    // y reemplaza "TU_TOKEN_COPOMEX" con tu token real.
    suspend fun buscarCodigoPostal(cp: String): Result<SepomexResponse> {
        return try {
            val url = "https://api.copomex.com/query/info_cp/$cp?type=simplified&token=TU_TOKEN_COPOMEX"
            val response: SepomexResponse = client.get(url).body()
            Result.success(response)
        } catch (e: Exception) {
            // Fallback al mock para desarrollo offline
            val mock = SepomexMock.buscarCP(cp)
            if (mock != null) Result.success(mock)
            else Result.failure(Exception("CP no encontrado: ${e.message}"))
        }
    }
}

// Datos de prueba para desarrollo sin conexión
object SepomexMock {
    private val data = mapOf(
        "58000" to SepomexResponse("58000", "Michoacán", "Morelia", "Morelia",
            listOf("Centro", "Chapultepec Norte", "Felix Ireta", "Las Américas", "Valentin Gómez Farías")),
        "58020" to SepomexResponse("58020", "Michoacán", "Morelia", "Morelia",
            listOf("Chapultepec Sur", "Félix Ireta", "Nueva Chapultepec")),
        "06600" to SepomexResponse("06600", "Ciudad de México", "Cuauhtémoc", "Ciudad de México",
            listOf("Juárez", "Tabacalera", "Santa María la Ribera")),
        "64000" to SepomexResponse("64000", "Nuevo León", "Monterrey", "Monterrey",
            listOf("Centro", "Obispado", "Del Valle")),
        "44100" to SepomexResponse("44100", "Jalisco", "Guadalajara", "Guadalajara",
            listOf("Centro", "Analco", "La Perla"))
    )

    fun buscarCP(cp: String): SepomexResponse? = data[cp]
}
