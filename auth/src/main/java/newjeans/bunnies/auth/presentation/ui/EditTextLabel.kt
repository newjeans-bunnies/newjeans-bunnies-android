package newjeans.bunnies.auth.presentation.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import newjeans.bunnies.designsystem.theme.authText

@Composable
fun EditTextLabel(text: String){
    Text(
        text = text,
        style = authText.bodyMedium,
    )
}