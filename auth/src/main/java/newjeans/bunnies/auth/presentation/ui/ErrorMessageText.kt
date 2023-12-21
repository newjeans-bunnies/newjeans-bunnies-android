package newjeans.bunnies.auth.presentation.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import newjeans.bunnies.designsystem.theme.authText

@Composable
fun ErrorMessageText(errorMessage: String){
    Text(text = errorMessage, style = authText.titleSmall)
}