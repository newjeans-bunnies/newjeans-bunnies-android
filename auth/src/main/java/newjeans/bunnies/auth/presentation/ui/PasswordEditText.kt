package newjeans.bunnies.auth.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import newjeans.bunnies.auth.presentation.passwordMaxValueLength
import newjeans.bunnies.auth.presentation.ui.image.PasswordIconImage
import newjeans.bunnies.designsystem.theme.AuthEditTextColor
import newjeans.bunnies.designsystem.theme.authText

@Composable
fun PasswordEditText(hint: String) {

    var password = ""
    var hidePasswordStatus = false

    BasicTextField(value = password,
        onValueChange = {
            if (it.length <= passwordMaxValueLength) password = it
        },
        modifier = Modifier
            .height(50.dp)
            .padding(start = 30.dp, end = 30.dp)
            .background(AuthEditTextColor, shape = RoundedCornerShape(size = 13.dp)),
        visualTransformation = if (hidePasswordStatus) VisualTransformation.None
        else PasswordVisualTransformation(),

        textStyle = authText.bodyMedium,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (password.isEmpty())
                    Text(text = hint, style = authText.bodySmall)
                innerTextField()
                Spacer(modifier = Modifier.weight(1f))
                PasswordStatusCheckBox(checked = hidePasswordStatus, onCheckedChangeEvent = {
                    hidePasswordStatus = it
                })
            }
        })
}