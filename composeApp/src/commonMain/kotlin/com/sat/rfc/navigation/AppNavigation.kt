/*package com.sat.rfc.navigation

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
}*/

package com.sat.rfc.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sat.rfc.data.local.RegistroLocalDataSource
import com.sat.rfc.domain.model.PersonaFisica
import com.sat.rfc.domain.model.PersonaMoral
import com.sat.rfc.ui.screens.DomicilioFiscalScreen
import com.sat.rfc.ui.screens.GuardadoScreen
import com.sat.rfc.ui.screens.HomeScreen
import com.sat.rfc.ui.screens.PersonaFisicaScreen
import com.sat.rfc.ui.screens.PersonaMoralScreen
import com.sat.rfc.ui.screens.RegistrosScreen
import com.sat.rfc.ui.screens.ResumenFisicaScreen
import com.sat.rfc.ui.screens.ResumenMoralScreen
import com.sat.rfc.viewmodel.DomicilioViewModel
import com.sat.rfc.viewmodel.PersonaFisicaViewModel
import com.sat.rfc.viewmodel.PersonaMoralViewModel
import com.sat.rfc.viewmodel.RegistrosViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.compose.koinInject
import androidx.compose.runtime.collectAsState


private val STEPS_FISICA = listOf("Identificación", "Domicilio", "Resumen")
private val STEPS_MORAL  = listOf("Empresa / Socios", "Domicilio", "Resumen")

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val scope         = rememberCoroutineScope()

    val fisicaViewModel   = remember { PersonaFisicaViewModel() }
    val moralViewModel    = remember { PersonaMoralViewModel() }
    val domicilioFisicaVM = remember { DomicilioViewModel() }
    val domicilioMoralVM  = remember { DomicilioViewModel() }

    val registroDataSource: RegistroLocalDataSource = koinInject()

    NavHost(
        navController    = navController,
        startDestination = Screen.Home.route
    ) {

        // ── Home ──────────────────────────────────────────────────────────────
        composable(Screen.Home.route) {
            HomeScreen(
                onPersonaFisicaClick  = { navController.navigate(Screen.PersonaFisica.route) },
                onPersonaMoralClick   = { navController.navigate(Screen.PersonaMoral.route) },
                onVerRegistrosClick   = { navController.navigate(Screen.Registros.route) }
            )
        }

        // ── Registros guardados ───────────────────────────────────────────────
        composable(Screen.Registros.route) {
            val registrosViewModel = remember {
                RegistrosViewModel(registroDataSource)
            }
            RegistrosScreen(
                viewModel = registrosViewModel,
                onBack    = { navController.popBackStack() }
            )
        }

        // ── Flujo Persona Física ──────────────────────────────────────────────
        composable(Screen.PersonaFisica.route) {
            PersonaFisicaScreen(
                viewModel             = fisicaViewModel,
                onNavigateToDomicilio = { navController.navigate(Screen.DomicilioFisica.route) },
                onBack                = { navController.popBackStack() }
            )
        }

        composable(Screen.DomicilioFisica.route) {
            DomicilioFiscalScreen(
                viewModel           = domicilioFisicaVM,
                stepCurrent         = 2,
                stepLabels          = STEPS_FISICA,
                onNavigateToResumen = { navController.navigate(Screen.ResumenFisica.route) },
                onBack              = { navController.popBackStack() }
            )
        }

        composable(Screen.ResumenFisica.route) {
            val persona = fisicaViewModel.state.collectAsState().value.copy(
                domicilioFiscal = domicilioFisicaVM.getDomicilio()
            )
            ResumenFisicaScreen(
                persona   = persona,
                domicilio = domicilioFisicaVM.getDomicilio(),
                onGuardar = {
                    scope.launch {
                        val json = Json.encodeToString<PersonaFisica>(persona)
                        registroDataSource.guardarRegistro(
                            tipoPersona = "FISICA",
                            datosJson   = json
                        )
                        navController.navigate(Screen.Guardado.route) {
                            popUpTo(Screen.PersonaFisica.route) { inclusive = true }
                        }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        // ── Flujo Persona Moral ───────────────────────────────────────────────
        composable(Screen.PersonaMoral.route) {
            PersonaMoralScreen(
                viewModel             = moralViewModel,
                onNavigateToDomicilio = { navController.navigate(Screen.DomicilioMoral.route) },
                onBack                = { navController.popBackStack() }
            )
        }

        composable(Screen.DomicilioMoral.route) {
            DomicilioFiscalScreen(
                viewModel           = domicilioMoralVM,
                stepCurrent         = 2,
                stepLabels          = STEPS_MORAL,
                onNavigateToResumen = { navController.navigate(Screen.ResumenMoral.route) },
                onBack              = { navController.popBackStack() }
            )
        }

        composable(Screen.ResumenMoral.route) {
            val persona = moralViewModel.state.collectAsState().value.copy(
                domicilioFiscal = domicilioMoralVM.getDomicilio()
            )
            ResumenMoralScreen(
                persona   = persona,
                domicilio = domicilioMoralVM.getDomicilio(),
                onGuardar = {
                    scope.launch {
                        val json = Json.encodeToString<PersonaMoral>(persona)
                        registroDataSource.guardarRegistro(
                            tipoPersona = "MORAL",
                            datosJson   = json
                        )
                        navController.navigate(Screen.Guardado.route) {
                            popUpTo(Screen.PersonaMoral.route) { inclusive = true }
                        }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        // ── Confirmación ──────────────────────────────────────────────────────
        composable(Screen.Guardado.route) {
            GuardadoScreen(
                onNuevoRegistro = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}