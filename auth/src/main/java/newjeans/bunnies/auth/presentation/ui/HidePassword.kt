package newjeans.bunnies.auth.presentation.ui


import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

import newjeans.bunnies.designsystem.R


@Composable
fun PasswordStatusCheckBox(onCheckedChangeEvent: (Boolean) -> Unit, checked: Boolean) {
    CustomCheckbox(checked = checked,
        onPainter = painterResource(id = R.drawable.ic_hide),
        offPainter = painterResource(id = R.drawable.ic_show),
        onCheckedChange = { onCheckedChangeEvent(it) })
}