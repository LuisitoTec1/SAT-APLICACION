package com.sat.rfc

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.sat.rfc.di.appModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun main() {
    // Inicializar Koin con el driver de Desktop
    val driverFactory = DatabaseDriverFactory()
    val platformModule = module {
        single { driverFactory.createDriver() }
    }
    startKoin {
        modules(platformModule, appModule)
    }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Pre-Registro RFC - SAT",
            state = rememberWindowState(width = 720.dp, height = 860.dp)
        ) {
            App()
        }
    }
}