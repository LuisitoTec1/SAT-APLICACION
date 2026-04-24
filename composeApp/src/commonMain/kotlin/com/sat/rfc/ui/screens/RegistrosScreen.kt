package com.sat.rfc.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sat.rfc.data.local.RegistroGuardado
import com.sat.rfc.domain.model.PersonaFisica
import com.sat.rfc.domain.model.PersonaMoral
import com.sat.rfc.viewmodel.RegistrosViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrosScreen(
    viewModel: RegistrosViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var registroAEliminar by remember { mutableStateOf<Long?>(null) }

    // Diálogo de confirmación para eliminar
    registroAEliminar?.let { id ->
        AlertDialog(
            onDismissRequest = { registroAEliminar = null },
            title = { Text("Eliminar registro") },
            text = { Text("¿Estás seguro de que deseas eliminar este registro? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.eliminarRegistro(id)
                        registroAEliminar = null
                    }
                ) { Text("Eliminar", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { registroAEliminar = null }) { Text("Cancelar") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registros Guardados") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Regresar")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.cargarRegistros() }) {
                        Text("↻", fontSize = 20.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.cargando -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.error != null -> {
                    Text(
                        text = state.error!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(24.dp)
                    )
                }

                state.registros.isEmpty() -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📋", fontSize = 56.sp)
                        Spacer(Modifier.height(16.dp))
                        Text(
                            "No hay registros guardados",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            "Los registros aparecerán aquí\ndespués de guardarlos",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Text(
                                "${state.registros.size} registro(s) guardado(s)",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                        items(state.registros, key = { it.id }) { registro ->
                            RegistroCard(
                                registro = registro,
                                personaFisica = if (registro.tipoPersona == "FISICA")
                                    viewModel.parsearFisica(registro.datosJson) else null,
                                personaMoral = if (registro.tipoPersona == "MORAL")
                                    viewModel.parsearMoral(registro.datosJson) else null,
                                onEliminar = { registroAEliminar = registro.id }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RegistroCard(
    registro: RegistroGuardado,
    personaFisica: PersonaFisica?,
    personaMoral: PersonaMoral?,
    onEliminar: () -> Unit
) {
    var expandido by remember { mutableStateOf(false) }

    val fecha = remember(registro.fechaCreacion) {
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            .format(Date(registro.fechaCreacion))
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // ── Encabezado ────────────────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    if (registro.tipoPersona == "FISICA") "👤" else "🏢",
                    fontSize = 28.sp
                )
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = when {
                            personaFisica != null ->
                                "${personaFisica.nombres} ${personaFisica.apellidoPaterno}".trim()
                                    .ifBlank { "Sin nombre" }
                            personaMoral != null ->
                                personaMoral.denominacion.ifBlank { "Sin denominación" }
                            else -> "Registro #${registro.id}"
                        },
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                    Text(
                        text = if (registro.tipoPersona == "FISICA") "Persona Física" else "Persona Moral",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Guardado: $fecha",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
                // Botón expandir/colapsar
                IconButton(onClick = { expandido = !expandido }) {
                    Icon(
                        if (expandido) Icons.Default.KeyboardArrowUp
                        else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (expandido) "Colapsar" else "Expandir"
                    )
                }
                // Botón eliminar
                IconButton(onClick = onEliminar) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            // ── Detalle expandible ────────────────────────────────────────────
            AnimatedVisibility(visible = expandido) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider()
                    Spacer(Modifier.height(12.dp))

                    if (personaFisica != null) {
                        DetallePersonaFisica(personaFisica)
                    } else if (personaMoral != null) {
                        DetallePersonaMoral(personaMoral)
                    }
                }
            }
        }
    }
}

@Composable
private fun DetallePersonaFisica(p: PersonaFisica) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        FilaDetalle("CURP", p.curp)
        FilaDetalle("Nombre completo", "${p.nombres} ${p.apellidoPaterno} ${p.apellidoMaterno}".trim())
        FilaDetalle("Fecha nacimiento", p.fechaNacimiento)
        if (p.correoElectronico.isNotBlank()) FilaDetalle("Correo", p.correoElectronico)
        if (p.telefono.isNotBlank()) FilaDetalle("Teléfono", p.telefono)
        FilaDetalle("Actividad económica", p.actividadEconomica)
        FilaDetalle("Régimen fiscal", p.regimenFiscal)
        Spacer(Modifier.height(8.dp))
        DetalleDomicilio(p.domicilioFiscal)
    }
}

@Composable
private fun DetallePersonaMoral(p: PersonaMoral) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        FilaDetalle("Razón social", p.denominacion)
        FilaDetalle("Fecha constitución", p.fechaConstitucion)
        FilaDetalle("RFC Representante", p.rfcRepresentanteLegal)
        if (p.rfcSocios.isNotEmpty())
            FilaDetalle("Socios", p.rfcSocios.joinToString(", "))
        FilaDetalle("No. Escritura", p.numeroEscritura)
        FilaDetalle("Régimen capital", p.regimenCapital)
        FilaDetalle("Actividad económica", p.actividadEconomica)
        FilaDetalle("Régimen fiscal", p.regimenFiscal)
        Spacer(Modifier.height(8.dp))
        DetalleDomicilio(p.domicilioFiscal)
    }
}

@Composable
private fun DetalleDomicilio(d: com.sat.rfc.domain.model.DomicilioFiscal) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                "📍 Domicilio Fiscal",
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.primary
            )
            FilaDetalle("CP", d.codigoPostal)
            FilaDetalle("Estado / Municipio", "${d.estado}, ${d.municipio}")
            if (d.localidad.isNotBlank()) FilaDetalle("Localidad", d.localidad)
            FilaDetalle("Colonia", d.colonia)
            FilaDetalle("Vialidad", "${d.tipoVialidad} ${d.nombreVialidad}".trim())
            FilaDetalle("Núm. Exterior", d.numeroExterior)
            if (d.numeroInterior.isNotBlank()) FilaDetalle("Núm. Interior", d.numeroInterior)
            if (d.entreCalle1.isNotBlank())
                FilaDetalle("Entre calles", "${d.entreCalle1} y ${d.entreCalle2}")
            if (d.referenciaAdicional.isNotBlank())
                FilaDetalle("Referencia", d.referenciaAdicional)
        }
    }
}

@Composable
private fun FilaDetalle(label: String, value: String) {
    if (value.isBlank()) return
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.weight(0.4f)
        )
        Text(
            value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(0.6f)
        )
    }
}

