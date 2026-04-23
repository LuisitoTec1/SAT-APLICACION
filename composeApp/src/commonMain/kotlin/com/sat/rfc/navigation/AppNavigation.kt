package com.sat.rfc.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sat.rfc.ui.screens.DomicilioFiscalScreen
import com.sat.rfc.ui.screens.GuardadoScreen
import com.sat.rfc.ui.screens.HomeScreen
import com.sat.rfc.ui.screens.PersonaFisicaScreen
import com.sat.rfc.ui.screens.PersonaMoralScreen
import com.sat.rfc.ui.screens.ResumenFisicaScreen
import com.sat.rfc.ui.screens.ResumenMoralScreen
import com.sat.rfc.viewmodel.DomicilioViewModel
import com.sat.rfc.viewmodel.PersonaFisicaViewModel
import com.sat.rfc.viewmodel.PersonaMoralViewModel

// Etiquetas de los pasos para cada flujo
private val STEPS_FISICA = listOf("Identificación", "Domicilio", "Resumen")
private val STEPS_MORAL  = listOf("Empresa / Socios", "Domicilio", "Resumen")

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Los ViewModels se instancian con remember{} para que persistan
    // mientras AppNavigation esté en composición (toda la sesión).
    // Si usas Koin puedes reemplazar remember{X()} por koinInject<X>().
    val fisicaViewModel    = remember { PersonaFisicaViewModel() }
    val moralViewModel     = remember { PersonaMoralViewModel() }
    val domicilioFisicaVM  = remember { DomicilioViewModel() }
    val domicilioMoralVM   = remember { DomicilioViewModel() }

    NavHost(
        navController    = navController,
        startDestination = Screen.Home.route
    ) {

        // ── Home ──────────────────────────────────────────────────────────────
        composable(Screen.Home.route) {
            HomeScreen(
                onPersonaFisicaClick = {
                    navController.navigate(Screen.PersonaFisica.route)
                },
                onPersonaMoralClick = {
                    navController.navigate(Screen.PersonaMoral.route)
                }
            )
        }

        // ── Flujo Persona Física ──────────────────────────────────────────────
        composable(Screen.PersonaFisica.route) {
            PersonaFisicaScreen(
                viewModel          = fisicaViewModel,
                // step 1 de 3: Identificación
                stepCurrent        = 1,
                stepLabels         = STEPS_FISICA,
                onNavigateToDomicilio = {
                    navController.navigate(Screen.DomicilioFisica.route)
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.DomicilioFisica.route) {
            DomicilioFiscalScreen(
                viewModel          = domicilioFisicaVM,
                // step 2 de 3: Domicilio
                stepCurrent        = 2,
                stepLabels         = STEPS_FISICA,
                onNavigateToResumen = {
                    navController.navigate(Screen.ResumenFisica.route)
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.ResumenFisica.route) {
            ResumenFisicaScreen(
                persona   = fisicaViewModel.state.value,
                domicilio = domicilioFisicaVM.getDomicilio(),
                // step 3 de 3: Resumen
                stepCurrent = 3,
                stepLabels  = STEPS_FISICA,
                onGuardar = {
                    // TODO: llamar a RegistroLocalDataSource.guardarRegistro(...)
                    navController.navigate(Screen.Guardado.route) {
                        // Limpia todo el flujo de Física del backstack
                        popUpTo(Screen.PersonaFisica.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        // ── Flujo Persona Moral ───────────────────────────────────────────────
        composable(Screen.PersonaMoral.route) {
            PersonaMoralScreen(
                viewModel          = moralViewModel,
                // step 1 de 3: Empresa / Socios
                stepCurrent        = 1,
                stepLabels         = STEPS_MORAL,
                onNavigateToDomicilio = {
                    navController.navigate(Screen.DomicilioMoral.route)
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.DomicilioMoral.route) {
            DomicilioFiscalScreen(
                viewModel          = domicilioMoralVM,
                // step 2 de 3: Domicilio
                stepCurrent        = 2,
                stepLabels         = STEPS_MORAL,
                onNavigateToResumen = {
                    navController.navigate(Screen.ResumenMoral.route)
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.ResumenMoral.route) {
            ResumenMoralScreen(
                persona   = moralViewModel.state.value,
                domicilio = domicilioMoralVM.getDomicilio(),
                // step 3 de 3: Resumen
                stepCurrent = 3,
                stepLabels  = STEPS_MORAL,
                onGuardar = {
                    // TODO: llamar a RegistroLocalDataSource.guardarRegistro(...)
                    navController.navigate(Screen.Guardado.route) {
                        // Limpia todo el flujo de Moral del backstack
                        popUpTo(Screen.PersonaMoral.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        // ── Confirmación final ────────────────────────────────────────────────
        composable(Screen.Guardado.route) {
            GuardadoScreen(
                onNuevoRegistro = {
                    navController.navigate(Screen.Home.route) {
                        // Vacía todo el backstack y deja sólo Home
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}