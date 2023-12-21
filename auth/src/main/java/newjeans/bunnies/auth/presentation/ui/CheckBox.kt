package newjeans.bunnies.auth.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import newjeans.bunnies.designsystem.R

@Composable
fun AutoLoginCheckBox(checkStatus: Boolean, checkEvent: (Boolean) -> Unit) {
    newjeans.bunnies.auth.presentation.CustomCheckbox(checked = checkStatus,
        onPainter = painterResource(id = R.drawable.ic_check_box_true),
        offPainter = painterResource(id = R.drawable.ic_check_box_falue),
        onCheckedChange = { checkEvent(it) })
}

@Composable
fun PasswordStatusCheckBox(checkStatus: Boolean, checkEvent: (Boolean) -> Unit) {
    newjeans.bunnies.auth.presentation.CustomCheckbox(checked = checkStatus,
        onPainter = painterResource(id = R.drawable.ic_hide),
        offPainter = painterResource(id = R.drawable.ic_show),
        onCheckedChange = { checkEvent(it) })
}