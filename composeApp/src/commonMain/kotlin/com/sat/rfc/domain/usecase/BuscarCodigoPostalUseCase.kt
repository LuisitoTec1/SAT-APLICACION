package com.sat.rfc.domain.usecase

import com.sat.rfc.data.repository.SepomexRepository
import com.sat.rfc.data.repository.SepomexResponse

class BuscarCodigoPostalUseCase(
    private val repository: SepomexRepository
) {
    suspend operator fun invoke(cp: String): Result<SepomexResponse> {
        if (cp.length != 5 || !cp.all { it.isDigit() }) {
            return Result.failure(IllegalArgumentException("El Código Postal debe tener 5 dígitos"))
        }
        return repository.buscarCodigoPostal(cp)
    }
}