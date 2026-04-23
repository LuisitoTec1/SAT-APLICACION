package com.sat.rfc.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sat.rfc.data.catalogs.CatalogoActividades
import com.sat.rfc.data.catalogs.CatalogoRegimenes
import com.sat.rfc.ui.components.DateField
import com.sat.rfc.ui.components.DropdownField
import com.sat.rfc.ui.components.FormField
import com.sat.rfc.ui.components.StepIndicator
import com.sat.rfc.viewmodel.PersonaFisicaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonaFisicaScreen(
    viewModel: PersonaFisicaViewModel,
    stepCurrent: Int = 1,
    stepLabels: List<String> = emptyList(),
    onNavigateToDomicilio: () -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val errors by viewModel.errors.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Persona Física") },
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
                /*currentStep = 1,
                totalSteps = 3,
                stepLabels = listOf("Identificación", "Actividad", "Domicilio")*/
                currentStep = stepCurrent,
                totalSteps  = stepLabels.size,
                stepLabels  = stepLabels
            )

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SectionTitle("📋 Datos de Identificación Personal")

                FormField(
                    label = "CURP",
                    value = state.curp,
                    onValueChange = viewModel::updateCurp,
                    error = errors["curp"],
                    placeholder = "AAAA000000HAAAAA00",
                    isRequired = true,
                    capitalization = KeyboardCapitalization.Characters
                )

                FormField(
                    label = "Nombre(s)",
                    value = state.nombres,
                    onValueChange = viewModel::updateNombres,
                    error = errors["nombres"],
                    isRequired = true,
                    capitalization = KeyboardCapitalization.Characters
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FormField(
                        label = "Apellido Paterno",
                        value = state.apellidoPaterno,
                        onValueChange = viewModel::updateApellidoPaterno,
                        error = errors["apellidoPaterno"],
                        isRequired = true,
                        capitalization = KeyboardCapitalization.Characters,
                        modifier = Modifier.weight(1f)
                    )
                    FormField(
                        label = "Apellido Materno",
                        value = state.apellidoMaterno,
                        onValueChange = viewModel::updateApellidoMaterno,
                        capitalization = KeyboardCapitalization.Characters,
                        modifier = Modifier.weight(1f)
                    )
                }

                DateField(
                    label = "Fecha de Nacimiento",
                    value = state.fechaNacimiento,
                    onValueChange = viewModel::updateFechaNacimiento,
                    error = errors["fechaNacimiento"],
                    isRequired = true
                )

                Spacer(Modifier.height(8.dp))
                SectionTitle("📞 Datos de Contacto")

                FormField(
                    label = "Correo Electrónico",
                    value = state.correoElectronico,
                    onValueChange = viewModel::updateCorreo,
                    error = errors["correo"],
                    placeholder = "correo@ejemplo.com",
                    keyboardType = KeyboardType.Email
                )

                FormField(
                    label = "Teléfono",
                    value = state.telefono,
                    onValueChange = viewModel::updateTelefono,
                    placeholder = "10 dígitos",
                    keyboardType = KeyboardType.Phone
                )

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
                    selectedValue = state.regimenFiscal,
                    options = CatalogoRegimenes.personaFisica,
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