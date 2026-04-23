package com.sat.rfc.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SATRed = Color(0xFFB71C1C)
private val SATGold = Color(0xFFF9A825)
private val SATDarkGrey = Color(0xFF37474F)
private val SATLightRed = Color(0xFFFFCDD2)

val LightColorScheme = lightColorScheme(
    primary = SATRed,
    onPrimary = Color.White,
    primaryContainer = SATLightRed,
    onPrimaryContainer = Color(0xFF4A0000),
    secondary = SATGold,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFFFF9C4),
    onSecondaryContainer = Color(0xFF1A1200),
    tertiary = SATDarkGrey,
    background = Color(0xFFF5F5F5),
    surface = Color.White,
    error = Color(0xFFB00020)
)

@Composable
fun SatRfcTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}