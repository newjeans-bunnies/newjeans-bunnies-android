package newjeans.bunnies.auth.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import newjeans.bunnies.designsystem.theme.authText

@Composable
fun MainButton(message: String, event: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable(
                onClick = event, interactionSource = remember {
                    MutableInteractionSource()
                }, indication = null
            )
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
    ) {
        Text(
            text = message, style = authText.labelMedium
        )
    }
}