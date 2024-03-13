package newjeans.bunnies.auth.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import newjeans.bunnies.designsystem.theme.CustomColor
import newjeans.bunnies.designsystem.theme.CustomTextStyle

@Composable
fun Explanation(text: String){
    Text(text = text, color = CustomColor.Gray, style = CustomTextStyle.Title1, modifier = Modifier.padding(start = 35.dp, top = 5.dp))
}