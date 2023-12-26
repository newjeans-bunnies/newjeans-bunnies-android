package newjeans.bunnies.auth.presentation.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import newjeans.bunnies.designsystem.theme.AuthButtonColor
import newjeans.bunnies.designsystem.theme.AuthEditTextColor
import newjeans.bunnies.designsystem.theme.authText


@Composable
fun EditTextEndButton(
    hint: String,
    event: (String) -> Unit,
    buttonText: String,
    maxValueLength: Int
) {

    var text = ""

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
            .height(50.dp)
    ) {
        BasicTextField(
            value = text,
            onValueChange = {
                if (it.length <= maxValueLength) {
                    text = it
                }
            },
            modifier = Modifier
                .weight(1F)
                .height(50.dp)
                .background(AuthEditTextColor, shape = RoundedCornerShape(size = 13.dp)),
            visualTransformation = VisualTransformation.None,
            textStyle = authText.bodyMedium,
            maxLines = 1,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (text.isEmpty())
                        Text(text = hint, style = authText.bodySmall)
                    innerTextField()
                }
            })

        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .clickable(
                    onClick = {
                        event(text)
                    }, interactionSource = remember {
                        MutableInteractionSource()
                    }, indication = null
                )
                .size(width = 120.dp, height = 50.dp)
                .background(AuthButtonColor, shape = RoundedCornerShape(size = 13.dp)),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = buttonText,
                style = authText.labelMedium
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun Preview() {
    EditTextEndButton(hint = "아이디", event = {}, buttonText = "중복확인", maxValueLength = 10)
}