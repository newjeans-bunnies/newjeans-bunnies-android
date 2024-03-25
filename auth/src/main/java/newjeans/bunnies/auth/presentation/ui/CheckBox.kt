package newjeans.bunnies.auth.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

import newjeans.bunnies.designsystem.R
import newjeans.bunnies.designsystem.ui.CustomCheckbox


@Composable
fun CheckBox(checkStatus: Boolean, checkEvent: (Boolean) -> Unit) {
    CustomCheckbox(checked = checkStatus,
        onPainter = painterResource(id = R.drawable.ic_check_box_true),
        offPainter = painterResource(id = R.drawable.ic_check_box_false),
        onCheckedChange = { checkEvent(it) })
}

//@Composable
//fun PasswordStatusCheckBox(checkStatus: Boolean, checkEvent: (Boolean) -> Unit) {
//    CustomCheckbox(checked = checkStatus,
//        onPainter = painterResource(id = R.drawable.),
//        offPainter = painterResource(id = R.drawable.),
//        onCheckedChange = { checkEvent(it) })
//}