package newjeans.bunnies.auth.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import newjeans.bunnies.designsystem.theme.AuthRadioButtonBackgroundColor
import newjeans.bunnies.designsystem.theme.AuthRadioButtonColor
import newjeans.bunnies.designsystem.theme.authText


@Composable
fun SelectCountryRadioButton() {
    val countryOptions = listOf("KR", "JP", "CN", "US")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(countryOptions[1]) }
    Row(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
            .background(color = AuthRadioButtonBackgroundColor, shape = RoundedCornerShape(size = 13.dp)),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        countryOptions.forEach { country ->
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .height(50.dp)
                    .weight(1F)
                    .selectable(
                        selected = (country == selectedOption),
                        onClick = {
                            onOptionSelected(country)
                        }
                    )
                    .background(color = if (country == selectedOption) {
                        AuthRadioButtonColor // 선택된 경우의 색상
                    } else {
                         // 선택되지 않은 경우의 색상
                        AuthRadioButtonBackgroundColor
                    }, shape = RoundedCornerShape(size = 13.dp)),
                contentAlignment = Alignment.Center
            ) {
                RadioButtonText(country)
            }
        }
    }
}

@Composable
fun RadioButtonText(country: String){
    Text(
        text = country,
        style = authText.headlineMedium
    )
}
