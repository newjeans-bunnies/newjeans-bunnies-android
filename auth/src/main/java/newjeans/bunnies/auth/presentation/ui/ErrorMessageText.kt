package newjeans.bunnies.auth.presentation.ui

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import newjeans.bunnies.designsystem.theme.authText

@Composable
fun ErrorMessageText(errorMessage: String){
    Text(text = errorMessage, style = authText.titleSmall, modifier = Modifier.height(30.dp))
}