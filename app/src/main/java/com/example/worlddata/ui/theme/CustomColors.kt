package com.example.worlddata.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val ColorScheme.correctGreen: Color
    @Composable
    get() = if (isSystemInDarkTheme()) secondaryDark else secondary

val ColorScheme.wrongRed: Color
    @Composable
    get() = if (isSystemInDarkTheme()) errorDark else error

