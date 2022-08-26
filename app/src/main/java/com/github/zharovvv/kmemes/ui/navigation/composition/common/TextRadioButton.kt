package com.github.zharovvv.kmemes.ui.navigation.composition.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.zharovvv.kmemes.ui.theme.ThemedPreview

@Composable
fun TextRadioButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val color = if (selected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.secondary
    }
    val textColor = if (selected) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSecondary
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color)
            .padding(8.dp)
            .clickable(role = Role.RadioButton, onClick = onClick)
    ) {
        Text(text, fontSize = 10.sp, color = textColor)
    }
}

@Preview
@Composable
fun TextRadioButtonPreview() {
    ThemedPreview {
        TextRadioButton(text = "Selected", onClick = {}, selected = true)
        Spacer(modifier = Modifier.height(16.dp))
        TextRadioButton(text = "Non Selected", onClick = {}, selected = false)
    }
}