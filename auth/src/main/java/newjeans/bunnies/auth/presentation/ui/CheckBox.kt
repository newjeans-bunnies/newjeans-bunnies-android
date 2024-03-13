package newjeans.bunnies.auth.presentation.ui


import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

import newjeans.bunnies.designsystem.R


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

@Composable
fun CustomCheckbox(
    checked: Boolean,
    onPainter: Painter,
    offPainter: Painter,
    onCheckedChange: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier.clickable(onClick = {
            onCheckedChange(!checked)
        }, interactionSource = remember {
            MutableInteractionSource()
        }, indication = null
        )

    ) {
        if (checked) {
            Icon(
                painter = onPainter,
                contentDescription = "on",
            )
        } else {
            Icon(
                painter = offPainter,
                contentDescription = "off",
            )
        }
    }
}