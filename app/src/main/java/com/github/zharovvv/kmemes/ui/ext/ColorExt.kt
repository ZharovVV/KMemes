package com.github.zharovvv.kmemes.ui.ext

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

//NavigationBar в Compose имеет особый оттенок surface из-за tonalElevation
val ColorScheme.navigationBarContainerColor: Color
    get() = surfaceColorAtElevation(3.dp)