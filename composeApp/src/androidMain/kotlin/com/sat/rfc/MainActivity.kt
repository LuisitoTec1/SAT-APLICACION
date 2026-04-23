package com.sat.rfc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sat.rfc.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar Koin con el driver de Android
        val driverFactory = DatabaseDriverFactory(this)
        val platformModule = module {
            single { driverFactory.createDriver() }
        }
        startKoin {
            androidContext(this@MainActivity)
            modules(platformModule, appModule)
        }

        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}