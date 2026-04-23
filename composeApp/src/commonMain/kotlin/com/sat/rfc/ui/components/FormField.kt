package com.sat.rfc.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    error: String? = null,
    placeholder: String = "",
    isRequired: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    maxLines: Int = 1,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(if (isRequired) "$label *" else label) },
        placeholder = {
            if (placeholder.isNotEmpty())
                Text(placeholder, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f))
        },
        isError = error != null,
        supportingText = {
            if (error != null) Text(error, color = MaterialTheme.colorScheme.error)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = capitalization
        ),
        maxLines = maxLines,
        enabled = enabled,
        modifier = modifier.fillMaxWidth()
    )
}