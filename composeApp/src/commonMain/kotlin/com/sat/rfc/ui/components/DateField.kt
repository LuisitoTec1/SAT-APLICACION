package com.sat.rfc.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

// Campo de fecha con formato automático DD/MM/AAAA
@Composable
fun DateField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    error: String? = null,
    isRequired: Boolean = false,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { raw ->
            // Permitir solo dígitos y formatear automáticamente con "/"
            val digits = raw.filter { it.isDigit() }.take(8)
            val formatted = buildString {
                digits.forEachIndexed { index, c ->
                    if (index == 2 || index == 4) append('/')
                    append(c)
                }
            }
            onValueChange(formatted)
        },
        label = { Text(if (isRequired) "$label *" else label) },
        placeholder = { Text("DD/MM/AAAA", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)) },
        isError = error != null,
        supportingText = {
            if (error != null) Text(error, color = MaterialTheme.colorScheme.error)
            else Text("Formato: DD/MM/AAAA", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = modifier.fillMaxWidth()
    )
}