/*package com.sat.rfc.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.sat.rfc.data.catalogs.CatalogoActividades
import com.sat.rfc.data.catalogs.CatalogoRegimenCapital
import com.sat.rfc.data.catalogs.CatalogoRegimenes
import com.sat.rfc.ui.components.DateField
import com.sat.rfc.ui.components.DropdownField
import com.sat.rfc.ui.components.FormField
import com.sat.rfc.ui.components.StepIndicator
import com.sat.rfc.viewmodel.PersonaMoralViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonaMoralScreen(
    viewModel: PersonaMoralViewModel,
    onNavigateToDomicilio: () -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val errors by viewModel.errors.collectAsState()
    val rfcSociosInput by viewModel.rfcSociosInput.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Persona Moral") },
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
        ) {
            StepIndicator(
                currentStep = 1,
                totalSteps = 3,
                stepLabels = listOf("Empresa", "Socios", "Domicilio")
            )

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SectionTitle("🏢 Datos de la Empresa")

                FormField(
                    label = "Denominación o Razón Social",
                    value = state.denominacion,
                    onValueChange = viewModel::updateDenominacion,
                    error = errors["denominacion"],
                    placeholder = "NOMBRE DE LA EMPRESA S.A. DE C.V.",
                    isRequired = true,
                    capitalization = KeyboardCapitalization.Characters
                )

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Text(
                        "ℹ️ La razón social debe estar previamente validada por la Secretaría de Economía (SE).",
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }

                DateField(
                    label = "Fecha de Constitución",
                    value = state.fechaConstitucion,
                    onValueChange = viewModel::updateFechaConstitucion,
                    error = errors["fechaConstitucion"],
                    isRequired = true
                )

                FormField(
                    label = "Número de Escritura / Póliza",
                    value = state.numeroEscritura,
                    onValueChange = viewModel::updateNumeroEscritura,
                    error = errors["numeroEscritura"],
                    placeholder = "Alfanumérico",
                    isRequired = true
                )

                DropdownField(
                    label = "Régimen de Capital",
                    selectedValue = state.regimenCapital,
                    options = CatalogoRegimenCapital.tipos,
                    onSelectionChanged = viewModel::updateRegimenCapital,
                    error = errors["regimenCapital"],
                    isRequired = true
                )

                Spacer(Modifier.height(8.dp))
                SectionTitle("👤 Representante Legal y Socios")

                FormField(
                    label = "RFC del Representante Legal",
                    value = state.rfcRepresentanteLegal,
                    onValueChange = viewModel::updateRfcRepresentante,
                    error = errors["rfcRepresentante"],
                    placeholder = "13 caracteres alfanuméricos",
                    isRequired = true,
                    capitalization = KeyboardCapitalization.Characters
                )

                // Sección para agregar socios/accionistas
                Text(
                    "RFC de Socios / Accionistas",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FormField(
                        label = "RFC del Socio",
                        value = rfcSociosInput,
                        onValueChange = viewModel::updateRfcSociosInput,
                        error = errors["rfcSocios"],
                        placeholder = "12 o 13 caracteres",
                        capitalization = KeyboardCapitalization.Characters,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(8.dp))
                    FilledTonalIconButton(
                        onClick = viewModel::agregarSocio,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(Icons.Default.Add, "Agregar socio")
                    }
                }

                if (state.rfcSocios.isNotEmpty()) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(state.rfcSocios) { rfc ->
                            InputChip(
                                selected = false,
                                onClick = {},
                                label = { Text(rfc) },
                                trailingIcon = {
                                    IconButton(
                                        onClick = { viewModel.eliminarSocio(rfc) },
                                        modifier = Modifier.size(18.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Close,
                                            contentDescription = "Eliminar",
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))
                SectionTitle("💼 Actividad y Régimen Fiscal")

                DropdownField(
                    label = "Actividad Económica",
                    selectedValue = state.actividadEconomica,
                    options = CatalogoActividades.actividades,
                    onSelectionChanged = viewModel::updateActividadEconomica,
                    error = errors["actividadEconomica"],
                    isRequired = true
                )

                DropdownField(
                    label = "Régimen Fiscal",
                    selectedValue = "",
                    options = CatalogoRegimenes.personaMoral,
                    onSelectionChanged = {},
                    isRequired = true
                )

                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { if (viewModel.validate()) onNavigateToDomicilio() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Continuar → Domicilio Fiscal")
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}*/

package com.sat.rfc.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.sat.rfc.data.catalogs.CatalogoActividades
import com.sat.rfc.data.catalogs.CatalogoRegimenCapital
import com.sat.rfc.data.catalogs.CatalogoRegimenes
import com.sat.rfc.ui.components.DateField
import com.sat.rfc.ui.components.DropdownField
import com.sat.rfc.ui.components.FormField
import com.sat.rfc.ui.components.StepIndicator
import com.sat.rfc.viewmodel.PersonaMoralViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonaMoralScreen(
    viewModel: PersonaMoralViewModel,
    stepCurrent: Int = 1,                          // ← AGREGADO
    stepLabels: List<String> = emptyList(),         // ← AGREGADO
    onNavigateToDomicilio: () -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val errors by viewModel.errors.collectAsState()
    val rfcSociosInput by viewModel.rfcSociosInput.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Persona Moral") },
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
        ) {
            // ← CORREGIDO: usa los parámetros recibidos en lugar de valores hardcodeados
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
                SectionTitle("🏢 Datos de la Empresa")

                FormField(
                    label = "Denominación o Razón Social",
                    value = state.denominacion,
                    onValueChange = viewModel::updateDenominacion,
                    error = errors["denominacion"],
                    placeholder = "NOMBRE DE LA EMPRESA S.A. DE C.V.",
                    isRequired = true,
                    capitalization = KeyboardCapitalization.Characters
                )

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Text(
                        "ℹ️ La razón social debe estar previamente validada por la Secretaría de Economía (SE).",
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }

                DateField(
                    label = "Fecha de Constitución",
                    value = state.fechaConstitucion,
                    onValueChange = viewModel::updateFechaConstitucion,
                    error = errors["fechaConstitucion"],
                    isRequired = true
                )

                FormField(
                    label = "Número de Escritura / Póliza",
                    value = state.numeroEscritura,
                    onValueChange = viewModel::updateNumeroEscritura,
                    error = errors["numeroEscritura"],
                    placeholder = "Alfanumérico",
                    isRequired = true
                )

                DropdownField(
                    label = "Régimen de Capital",
                    selectedValue = state.regimenCapital,
                    options = CatalogoRegimenCapital.tipos,
                    onSelectionChanged = viewModel::updateRegimenCapital,
                    error = errors["regimenCapital"],
                    isRequired = true
                )

                Spacer(Modifier.height(8.dp))
                SectionTitle("👤 Representante Legal y Socios")

                FormField(
                    label = "RFC del Representante Legal",
                    value = state.rfcRepresentanteLegal,
                    onValueChange = viewModel::updateRfcRepresentante,
                    error = errors["rfcRepresentante"],
                    placeholder = "13 caracteres alfanuméricos",
                    isRequired = true,
                    capitalization = KeyboardCapitalization.Characters
                )

                Text(
                    "RFC de Socios / Accionistas",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FormField(
                        label = "RFC del Socio",
                        value = rfcSociosInput,
                        onValueChange = viewModel::updateRfcSociosInput,
                        error = errors["rfcSocios"],
                        placeholder = "12 o 13 caracteres",
                        capitalization = KeyboardCapitalization.Characters,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(8.dp))
                    FilledTonalIconButton(
                        onClick = viewModel::agregarSocio,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(Icons.Default.Add, "Agregar socio")
                    }
                }

                if (state.rfcSocios.isNotEmpty()) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(state.rfcSocios) { rfc ->
                            InputChip(
                                selected = false,
                                onClick = {},
                                label = { Text(rfc) },
                                trailingIcon = {
                                    IconButton(
                                        onClick = { viewModel.eliminarSocio(rfc) },
                                        modifier = Modifier.size(18.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Close,
                                            contentDescription = "Eliminar",
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))
                SectionTitle("💼 Actividad y Régimen Fiscal")

                DropdownField(
                    label = "Actividad Económica",
                    selectedValue = state.actividadEconomica,
                    options = CatalogoActividades.actividades,
                    onSelectionChanged = viewModel::updateActividadEconomica,
                    error = errors["actividadEconomica"],
                    isRequired = true
                )

                // ← CORREGIDO: vinculado al estado del ViewModel, no hardcodeado a ""
                DropdownField(
                    label = "Régimen Fiscal",
                    selectedValue = state.regimenFiscal,
                    options = CatalogoRegimenes.personaMoral,
                    onSelectionChanged = viewModel::updateRegimenFiscal,
                    error = errors["regimenFiscal"],
                    isRequired = true
                )

                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { if (viewModel.validate()) onNavigateToDomicilio() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Continuar → Domicilio Fiscal")
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}


