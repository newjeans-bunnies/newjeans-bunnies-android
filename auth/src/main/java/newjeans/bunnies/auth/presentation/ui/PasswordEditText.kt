package newjeans.bunnies.auth.presentation.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import newjeans.bunnies.auth.Constant
import newjeans.bunnies.designsystem.theme.CustomColor

import newjeans.bunnies.designsystem.theme.TextRule.passwordMaxCharacterCount
import newjeans.bunnies.designsystem.theme.CustomTextStyle


@Composable
fun PasswordEditText(
    hint: String,
    passwordOnValueChange: (String) -> Unit,
) {
    var isFocused by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }

    var passwordStatus: Boolean? by remember { mutableStateOf(null) }

    if(password.isNotEmpty())
        passwordStatus = Constant.passwordPattern(password)

    SelectionContainer {
        BasicTextField(value = password,
            onValueChange = {
                if (it.length <= passwordMaxCharacterCount) {
                    password = it
                    passwordOnValueChange(it)
                }
            },
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
                .onFocusChanged {
                    isFocused = it.isFocused
                }
                .background(CustomColor.LightGray, shape = RoundedCornerShape(size = 13.dp)),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            textStyle = CustomTextStyle.Title6,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password).copy(),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (password.isEmpty() && !isFocused)
                        Text(text = hint, style = CustomTextStyle.Title6, color = CustomColor.Gray)
                    innerTextField()
                }
            })
    }

}