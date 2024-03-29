package newjeans.bunnies.auth.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import newjeans.bunnies.designsystem.theme.CustomColor
import newjeans.bunnies.designsystem.theme.CustomTextStyle

@Composable
fun EditTextLabel(text: String){
    Text(
        modifier = Modifier.padding(start = 30.dp),
        text = text,
        style = CustomTextStyle.TitleMedium,
        color = CustomColor.LightBlack
    )
}