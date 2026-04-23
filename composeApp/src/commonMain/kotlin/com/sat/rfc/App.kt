package com.sat.rfc

import androidx.compose.runtime.Composable
import com.sat.rfc.navigation.AppNavigation
import com.sat.rfc.ui.theme.SatRfcTheme

@Composable
fun App() {
    SatRfcTheme {
        AppNavigation()
    }
}