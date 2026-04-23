/*package com.sat.rfc.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sat.rfc.data.catalogs.CatalogoVialidades
import com.sat.rfc.ui.components.DropdownField
import com.sat.rfc.ui.components.FormField
import com.sat.rfc.viewmodel.DomicilioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DomicilioFiscalScreen(
    viewModel: DomicilioViewModel,
    onNavigateToResumen: () -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val d = state.domicilio

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Domicilio Fiscal") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Regresar")
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Info sobre el CP
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Text(
                    "ℹ️ Al ingresar el Código Postal, el sistema autocompletará Estado, Municipio y listará las colonias disponibles.",
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            FormField(
                label = "Código Postal",
                value = d.codigoPostal,
                onValueChange = viewModel::updateCodigoPostal,
                placeholder = "5 dígitos",
                isRequired = true,
                keyboardType = KeyboardType.Number
            )

            if (state.cargandoCP) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            state.errorCP?.let {
                Text(it, color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall)
            }

            // Campos autocompletados (solo lectura)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = d.estado,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Estado") },
                    modifier = Modifier.weight(1f),
                    enabled = false
                )
                OutlinedTextField(
                    value = d.municipio,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Municipio") },
                    modifier = Modifier.weight(1f),
                    enabled = false
                )
            }

            // Colonia — dropdown si hay catálogo del CP
            if (state.coloniasDisponibles.isNotEmpty()) {
                DropdownField(
                    label = "Colonia",
                    selectedValue = d.colonia,
                    options = state.coloniasDisponibles,
                    onSelectionChanged = viewModel::updateColonia,
                    isRequired = true
                )
            } else {
                FormField(
                    label = "Colonia",
                    value = d.colonia,
                    onValueChange = viewModel::updateColonia,
                    placeholder = "Ingresa el CP primero",
                    isRequired = true
                )
            }

            DropdownField(
                label = "Tipo de Vialidad",
                selectedValue = d.tipoVialidad,
                options = CatalogoVialidades.tipos,
                onSelectionChanged = viewModel::updateTipoVialidad,
                isRequired = true
            )

            FormField(
                label = "Nombre de Vialidad",
                value = d.nombreVialidad,
                onValueChange = viewModel::updateNombreVialidad,
                placeholder = "Nombre de la calle",
                isRequired = true
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FormField(
                    label = "Núm. Exterior",
                    value = d.numeroExterior,
                    onValueChange = viewModel::updateNumeroExterior,
                    placeholder = "Ej: 12, S/N, Mz 1",
                    isRequired = true,
                    modifier = Modifier.weight(1f)
                )
                FormField(
                    label = "Núm. Interior",
                    value = d.numeroInterior,
                    onValueChange = viewModel::updateNumeroInterior,
                    placeholder = "Depto, Oficina...",
                    modifier = Modifier.weight(1f)
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FormField(
                    label = "Entre Calle 1",
                    value = d.entreCalle1,
                    onValueChange = viewModel::updateEntreCalle1,
                    modifier = Modifier.weight(1f)
                )
                FormField(
                    label = "Entre Calle 2",
                    value = d.entreCalle2,
                    onValueChange = viewModel::updateEntreCalle2,
                    modifier = Modifier.weight(1f)
                )
            }

            FormField(
                label = "Referencia Adicional",
                value = d.referenciaAdicional,
                onValueChange = viewModel::updateReferencia,
                placeholder = "Ej: Frente al parque, fachada roja"
            )

            FormField(
                label = "Características del Domicilio",
                value = d.caracteristicasDomicilio,
                onValueChange = viewModel::updateCaracteristicas,
                placeholder = "Descripción física del edificio o casa",
                maxLines = 3
            )

            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onNavigateToResumen,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Resumen")
            }
        }
    }
}*/
package com.sat.rfc.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sat.rfc.data.catalogs.CatalogoVialidades
import com.sat.rfc.ui.components.DropdownField
import com.sat.rfc.ui.components.FormField
import com.sat.rfc.ui.components.StepIndicator
import com.sat.rfc.viewmodel.DomicilioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DomicilioFiscalScreen(
    viewModel: DomicilioViewModel,
    stepCurrent: Int = 2,                                              // ← AGREGADO
    stepLabels: List<String> = listOf("Identificación", "Domicilio", "Resumen"), // ← AGREGADO
    onNavigateToResumen: () -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val d = state.domicilio

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Domicilio Fiscal") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Regresar") // ← CORREGIDO (ArrowBack deprecated)
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
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // ← AGREGADO: indicador de pasos
            if (stepLabels.isNotEmpty()) {
                StepIndicator(
                    currentStep = stepCurrent,
                    totalSteps  = stepLabels.size,
                    stepLabels  = stepLabels
                )
            }

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Info sobre el CP
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Text(
                        "ℹ️ Al ingresar el Código Postal, el sistema autocompletará Estado, Municipio y listará las colonias disponibles.",
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }

                FormField(
                    label = "Código Postal",
                    value = d.codigoPostal,
                    onValueChange = viewModel::updateCodigoPostal,
                    placeholder = "5 dígitos",
                    isRequired = true,
                    keyboardType = KeyboardType.Number
                )

                if (state.cargandoCP) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }

                state.errorCP?.let {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            it,
                            modifier = Modifier.padding(12.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                // Campos autocompletados (solo lectura)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = d.estado,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Estado") },
                        modifier = Modifier.weight(1f),
                        enabled = false
                    )
                    OutlinedTextField(
                        value = d.municipio,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Municipio") },
                        modifier = Modifier.weight(1f),
                        enabled = false
                    )
                }

                if (d.localidad.isNotBlank()) {
                    OutlinedTextField(
                        value = d.localidad,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Localidad") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
                    )
                }

                // Colonia — dropdown si hay catálogo del CP, texto libre si no
                if (state.coloniasDisponibles.isNotEmpty()) {
                    DropdownField(
                        label = "Colonia",
                        selectedValue = d.colonia,
                        options = state.coloniasDisponibles + listOf("Otra"),
                        onSelectionChanged = viewModel::updateColonia,
                        isRequired = true
                    )
                } else {
                    FormField(
                        label = "Colonia",
                        value = d.colonia,
                        onValueChange = viewModel::updateColonia,
                        placeholder = "Ingresa el CP primero",
                        isRequired = true,
                        enabled = d.codigoPostal.length == 5
                    )
                }

                SectionTitle("🛣️ Vialidad")

                DropdownField(
                    label = "Tipo de Vialidad",
                    selectedValue = d.tipoVialidad,
                    options = CatalogoVialidades.tipos,
                    onSelectionChanged = viewModel::updateTipoVialidad,
                    isRequired = true
                )

                FormField(
                    label = "Nombre de Vialidad",
                    value = d.nombreVialidad,
                    onValueChange = viewModel::updateNombreVialidad,
                    placeholder = "Nombre de la calle",
                    isRequired = true
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FormField(
                        label = "Núm. Exterior",
                        value = d.numeroExterior,
                        onValueChange = viewModel::updateNumeroExterior,
                        placeholder = "Ej: 12, S/N, Mz 1",
                        isRequired = true,
                        modifier = Modifier.weight(1f)
                    )
                    FormField(
                        label = "Núm. Interior",
                        value = d.numeroInterior,
                        onValueChange = viewModel::updateNumeroInterior,
                        placeholder = "Depto, Oficina...",
                        modifier = Modifier.weight(1f)
                    )
                }

                SectionTitle("📍 Referencias")

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FormField(
                        label = "Entre Calle 1",
                        value = d.entreCalle1,
                        onValueChange = viewModel::updateEntreCalle1,
                        modifier = Modifier.weight(1f)
                    )
                    FormField(
                        label = "Entre Calle 2",
                        value = d.entreCalle2,
                        onValueChange = viewModel::updateEntreCalle2,
                        modifier = Modifier.weight(1f)
                    )
                }

                FormField(
                    label = "Referencia Adicional",
                    value = d.referenciaAdicional,
                    onValueChange = viewModel::updateReferencia,
                    placeholder = "Ej: Frente al parque, fachada roja"
                )

                FormField(
                    label = "Características del Domicilio",
                    value = d.caracteristicasDomicilio,
                    onValueChange = viewModel::updateCaracteristicas,
                    placeholder = "Descripción física del edificio o casa",
                    maxLines = 3
                )

                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = onNavigateToResumen,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = viewModel.validate()
                ) {
                    Text("Ver Resumen del Registro")
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}