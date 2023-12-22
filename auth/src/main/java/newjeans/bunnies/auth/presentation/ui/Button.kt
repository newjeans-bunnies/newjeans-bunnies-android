package newjeans.bunnies.auth.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import newjeans.bunnies.designsystem.theme.AuthButtonColor

import newjeans.bunnies.designsystem.theme.authText

@Composable
fun MainButton(message: String, event: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp)
            .fillMaxWidth()
            .clickable(
                onClick = event, interactionSource = remember {
                    MutableInteractionSource()
                }, indication = null
            )
            .background(AuthButtonColor, shape = RoundedCornerShape(size = 13.dp))
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message, style = authText.titleMedium
        )
    }
}

@Composable
fun TextButton(message: String, event: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable(
                onClick = event, interactionSource = remember {
                    MutableInteractionSource()
                }, indication = null
            ),
        contentAlignment = Alignment.Center

    ) {
        Text(text = message, style = authText.labelMedium)
    }

}

@Composable
@Preview
fun MainButtonPreview() {
    MainButton("로그인", {})
}

@Composable
@Preview
fun TextButtonPreView() {
    TextButton("로그인", {})
}