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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import newjeans.bunnies.auth.Constant.numberPattern
import newjeans.bunnies.auth.utils.MaskNumberVisualTransformation

import newjeans.bunnies.designsystem.theme.AuthButtonColor
import newjeans.bunnies.designsystem.theme.AuthEditTextColor
import newjeans.bunnies.designsystem.theme.authText


private var id by mutableStateOf("")
private var phoneNumber by mutableStateOf("")
private var certificationNumber by mutableStateOf("")


@Composable
fun IdEditTextEndButton(
    hint: String,
    event: (String) -> Unit,
    buttonText: String,
    maxValueLength: Int,
    chageEvent: (String) -> Unit
) {
    val isFocused = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
            .height(50.dp)
    ) {
        BasicTextField(
            value = id,
            onValueChange = {
                if (it.length <= maxValueLength) {
                    id = it
                    chageEvent(it)
                }
            },
            modifier = Modifier
                .weight(1F)
                .height(50.dp)
                .background(AuthEditTextColor, shape = RoundedCornerShape(size = 13.dp))
                .onFocusChanged {
                    isFocused.value = it.isFocused
                },
            visualTransformation = VisualTransformation.None,
            textStyle = authText.bodyMedium,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (id.isEmpty() && !isFocused.value)
                        Text(text = hint, style = authText.bodySmall)
                    innerTextField()
                }
            })

        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .clickable(
                    onClick = {
                        event(id)
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
fun PhoneNumberEditTextEndButton(
    hint: String,
    event: (String) -> Unit,
    buttonText: String,
    maxValueLength: Int,
) {
    val isFocused = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
            .height(50.dp)
    ) {
        BasicTextField(
            value = phoneNumber,
            onValueChange = {
                if (it.length <= maxValueLength && numberPattern(it)) phoneNumber = it
            },
            modifier = Modifier
                .weight(1F)
                .height(50.dp)
                .background(AuthEditTextColor, shape = RoundedCornerShape(size = 13.dp))
                .onFocusChanged {
                    isFocused.value = it.isFocused
                },
            visualTransformation = MaskNumberVisualTransformation("000-0000-0000", '0'),
            textStyle = authText.bodyMedium,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (phoneNumber.isEmpty() && !isFocused.value)
                        Text(text = hint, style = authText.bodySmall)
                    innerTextField()
                }
            })

        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .clickable(
                    onClick = {
                        if(isValidPhoneNumber(phoneNumber)){
                            event(phoneNumber)
                        }
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

fun isValidPhoneNumber(input: String): Boolean {
    val pattern = Regex("^010\\d{8}$")
    return pattern.matches(input)
}

@Composable
fun CertificationNumberEditTextEndButton(
    hint: String,
    event: (String) -> Unit,
    buttonText: String,
    maxValueLength: Int,
) {
    val isFocused = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
            .height(50.dp)
    ) {
        BasicTextField(
            value = certificationNumber,
            onValueChange = {
                if (it.length <= maxValueLength && numberPattern(it)) certificationNumber = it
            },
            modifier = Modifier
                .weight(1F)
                .height(50.dp)
                .background(AuthEditTextColor, shape = RoundedCornerShape(size = 13.dp))
                .onFocusChanged {
                    isFocused.value = it.isFocused
                },
            visualTransformation = VisualTransformation.None,
            textStyle = authText.bodyMedium,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (certificationNumber.isEmpty() && !isFocused.value)
                        Text(text = hint, style = authText.bodySmall)
                    innerTextField()
                }
            })

        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .clickable(
                    onClick = {
                        event(certificationNumber)
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