package com.sat.rfc.domain.usecase

class ValidarCurpUseCase {
    // Regex oficial de la CURP según RENAPO
    private val curpRegex = Regex(
        "^[A-Z]{1}[AEIOU]{1}[A-Z]{2}\\d{2}" +
                "(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])" +
                "[HM]{1}" +
                "(AS|BC|BS|CC|CL|CM|CS|CH|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)" +
                "[B-DF-HJ-NP-TV-Z]{3}" +
                "[0-9A-Z]{1}\\d{1}$"
    )

    operator fun invoke(curp: String): ValidationResult {
        return when {
            curp.isBlank() -> ValidationResult.Error("La CURP es requerida")
            curp.length != 18 -> ValidationResult.Error("La CURP debe tener exactamente 18 caracteres")
            !curpRegex.matches(curp) -> ValidationResult.Error("Formato de CURP inválido")
            else -> ValidationResult.Success
        }
    }
}

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()

    val isValid get() = this is Success
    val errorMessage get() = (this as? Error)?.message
}