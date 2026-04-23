package com.sat.rfc.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sat.rfc.domain.model.DomicilioFiscal
import com.sat.rfc.domain.model.PersonaFisica
import com.sat.rfc.domain.model.PersonaMoral
import com.sat.rfc.ui.components.StepIndicator

// Pantalla de resumen para Persona Física
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumenFisicaScreen(
    persona: PersonaFisica,
    domicilio: DomicilioFiscal,
    stepCurrent: Int = 3,                      //
    stepLabels: List<String> = emptyList(),
    onGuardar: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resumen de Registro") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            if (stepLabels.isNotEmpty()) {
                StepIndicator(
                    currentStep = stepCurrent,
                    totalSteps  = stepLabels.size,
                    stepLabels  = stepLabels
                )
            }

            ResumenCard(title = "👤 Persona Física") {
                ResumenRow("CURP", persona.curp)
                ResumenRow("Nombre(s)", persona.nombres)
                ResumenRow("Apellido Paterno", persona.apellidoPaterno)
                ResumenRow("Apellido Materno", persona.apellidoMaterno.ifBlank { "—" })
                ResumenRow("Fecha de Nacimiento", persona.fechaNacimiento)
                ResumenRow("Correo Electrónico", persona.correoElectronico.ifBlank { "—" })
                ResumenRow("Teléfono", persona.telefono.ifBlank { "—" })
                ResumenRow("Actividad Económica", persona.actividadEconomica)
                ResumenRow("Régimen Fiscal", persona.regimenFiscal)
            }

            ResumenDomicilioCard(domicilio)

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "✅ Verificación Final",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Revisa que todos los datos sean correctos antes de guardar. " +
                                "Este pre-registro deberá ser completado en el portal oficial del SAT.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
            }

            Button(
                onClick = onGuardar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("💾 Guardar Pre-Registro")
            }

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("✏️ Editar Domicilio")
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

// Pantalla de resumen para Persona Moral
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumenMoralScreen(
    persona: PersonaMoral,
    domicilio: DomicilioFiscal,
    stepCurrent: Int = 3,
    stepLabels: List<String> = emptyList(),
    onGuardar: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resumen de Registro") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            if (stepLabels.isNotEmpty()) {
                StepIndicator(
                    currentStep = stepCurrent,
                    totalSteps  = stepLabels.size,
                    stepLabels  = stepLabels
                )
            }

            ResumenCard(title = "🏢 Persona Moral") {
                ResumenRow("Razón Social", persona.denominacion)
                ResumenRow("Fecha de Constitución", persona.fechaConstitucion)
                ResumenRow("RFC Representante Legal", persona.rfcRepresentanteLegal)
                ResumenRow("Socios/Accionistas", persona.rfcSocios.joinToString(", ").ifBlank { "—" })
                ResumenRow("No. Escritura/Póliza", persona.numeroEscritura)
                ResumenRow("Régimen de Capital", persona.regimenCapital)
                ResumenRow("Actividad Económica", persona.actividadEconomica)
            }

            ResumenDomicilioCard(domicilio)

            Button(
                onClick = onGuardar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("💾 Guardar Pre-Registro")
            }

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("✏️ Editar Domicilio")
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

// ─── Composables auxiliares del Resumen ───────────────────────────────────────

@Composable
private fun ResumenCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            content()
        }
    }
}

@Composable
private fun ResumenDomicilioCard(domicilio: DomicilioFiscal) {
    ResumenCard(title = "📍 Domicilio Fiscal") {
        ResumenRow("CP", domicilio.codigoPostal)
        ResumenRow("Estado", domicilio.estado)
        ResumenRow("Municipio", domicilio.municipio)
        if (domicilio.localidad.isNotBlank()) ResumenRow("Localidad", domicilio.localidad)
        ResumenRow("Colonia", domicilio.colonia)
        ResumenRow("Tipo de Vialidad", domicilio.tipoVialidad)
        ResumenRow("Vialidad", "${domicilio.tipoVialidad} ${domicilio.nombreVialidad}")
        ResumenRow("Núm. Exterior", domicilio.numeroExterior)
        if (domicilio.numeroInterior.isNotBlank())
            ResumenRow("Núm. Interior", domicilio.numeroInterior)
        if (domicilio.entreCalle1.isNotBlank())
            ResumenRow("Entre Calles", "${domicilio.entreCalle1} y ${domicilio.entreCalle2}")
        if (domicilio.referenciaAdicional.isNotBlank())
            ResumenRow("Referencia", domicilio.referenciaAdicional)
        if (domicilio.caracteristicasDomicilio.isNotBlank())
            ResumenRow("Características", domicilio.caracteristicasDomicilio)
    }
}

@Composable
private fun ResumenRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.weight(0.45f)
        )
        Text(
            value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(0.55f)
        )
    }
}

// Función auxiliar compartida por las pantallas de formulario
@Composable
fun SectionTitle(title: String) {
    Spacer(Modifier.height(4.dp))
    Text(
        title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.SemiBold
    )
    HorizontalDivider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
}