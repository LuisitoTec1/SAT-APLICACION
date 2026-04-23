package com.sat.rfc.di

import com.sat.rfc.data.local.RegistroLocalDataSource
import com.sat.rfc.data.repository.SepomexRepository
import com.sat.rfc.db.SatDatabase
import com.sat.rfc.domain.usecase.BuscarCodigoPostalUseCase
import com.sat.rfc.domain.usecase.ValidarCurpUseCase
import com.sat.rfc.viewmodel.DomicilioViewModel
import com.sat.rfc.viewmodel.PersonaFisicaViewModel
import com.sat.rfc.viewmodel.PersonaMoralViewModel
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val appModule = module {
    // HTTP Client
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }

    // Base de datos SQLDelight
    single { SatDatabase(get<app.cash.sqldelight.db.SqlDriver>()) }

    // Repositorios y DataSources
    single { SepomexRepository(get()) }
    single { RegistroLocalDataSource(get()) }

    // Use Cases
    factory { ValidarCurpUseCase() }
    factory { BuscarCodigoPostalUseCase(get()) }

    // ViewModels
    factory { PersonaFisicaViewModel(get()) }
    factory { PersonaMoralViewModel() }
    factory { DomicilioViewModel() }
}