package newjeans.bunnies.auth.presentation.ui


import android.util.Log

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

import newjeans.bunnies.designsystem.theme.AuthEditTextColor
import newjeans.bunnies.designsystem.theme.TextRule.passwordMaxCharacterCount
import newjeans.bunnies.designsystem.theme.authText


private var password by mutableStateOf("")
private var checkPassword by mutableStateOf("")


@Composable
fun PasswordEditText(
    hint: String,
    checkStatus: Boolean,
    checkEvent: (Boolean) -> Unit
) {
    val isFocused = remember { mutableStateOf(false) }


    BasicTextField(value = password,
        onValueChange = {
            if (it.length <= passwordMaxCharacterCount) {
                password = it
            }
        },
        modifier = Modifier
            .height(50.dp)
            .padding(start = 30.dp, end = 30.dp)
            .background(AuthEditTextColor, shape = RoundedCornerShape(size = 13.dp))
            .onFocusChanged {
                isFocused.value = it.isFocused
            },
        visualTransformation = if (checkStatus) VisualTransformation.None
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
                if (password.isEmpty() && !isFocused.value)
                    Text(text = hint, style = authText.bodySmall)
                innerTextField()
                Spacer(modifier = Modifier.weight(1f))
                PasswordStatusCheckBox(checkStatus) {
                    Log.d("클릭", it.toString())
                    checkEvent(it)
                }
            }
        })
}

@Composable
fun CheckPasswordEditText(
    hint: String,
    checkStatus: Boolean,
    checkEvent: (Boolean) -> Unit
) {
    val isFocused = remember { mutableStateOf(false) }


    BasicTextField(value = checkPassword,
        onValueChange = {
            if (it.length <= passwordMaxCharacterCount) {
                checkPassword = it
            }
        },
        modifier = Modifier
            .height(50.dp)
            .padding(start = 30.dp, end = 30.dp)
            .background(AuthEditTextColor, shape = RoundedCornerShape(size = 13.dp))
            .onFocusChanged {
                isFocused.value = it.isFocused
            },
        visualTransformation = if (checkStatus) VisualTransformation.None
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
                if (checkPassword.isEmpty() && !isFocused.value)
                    Text(text = hint, style = authText.bodySmall)
                innerTextField()
                Spacer(modifier = Modifier.weight(1f))
                PasswordStatusCheckBox(checkStatus) {
                    Log.d("클릭", it.toString())
                    checkEvent(it)
                }
            }
        })
}