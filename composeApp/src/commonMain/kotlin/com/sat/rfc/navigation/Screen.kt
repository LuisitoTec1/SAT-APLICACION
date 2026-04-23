package com.sat.rfc.navigation

sealed class Screen(val route: String) {
    object Home          : Screen("home")
    object PersonaFisica : Screen("persona_fisica")
    object PersonaMoral  : Screen("persona_moral")
    object DomicilioFisica : Screen("domicilio_fisica")
    object DomicilioMoral  : Screen("domicilio_moral")
    object ResumenFisica : Screen("resumen_fisica")
    object ResumenMoral  : Screen("resumen_moral")
    object Guardado      : Screen("guardado")
}