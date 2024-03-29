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
import newjeans.bunnies.designsystem.theme.CustomColor
import newjeans.bunnies.designsystem.theme.CustomTextStyle


@Composable
fun IdEditTextEndButton(
    hint: String,
    event: (String) -> Unit,
    buttonText: String,
    maxValueLength: Int,
    chageEvent: (String) -> Unit,
) {
    val isFocused = remember { mutableStateOf(false) }
    var id by remember {
        mutableStateOf("")
    }

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
                .background(CustomColor.LightGray, shape = RoundedCornerShape(size = 13.dp))
                .onFocusChanged {
                    isFocused.value = it.isFocused
                },
            visualTransformation = VisualTransformation.None,
            textStyle = CustomTextStyle.Title6,
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
                        Text(text = hint, style = CustomTextStyle.Title6, color = CustomColor.Gray)
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
                .background(CustomColor.Button, shape = RoundedCornerShape(size = 13.dp)),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = buttonText,
                style = CustomTextStyle.buttonText
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
    buttonState: Boolean,
    readOnly: Boolean
) {
    val isFocused = remember { mutableStateOf(false) }
    var phoneNumber by remember { mutableStateOf("") }
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
                .background(CustomColor.LightGray, shape = RoundedCornerShape(size = 13.dp))
                .onFocusChanged {
                    isFocused.value = it.isFocused
                },
            visualTransformation = MaskNumberVisualTransformation("000-0000-0000", '0'),
            textStyle = CustomTextStyle.Title6,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            readOnly = readOnly,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (phoneNumber.isEmpty() && !isFocused.value)
                        Text(text = hint, style = CustomTextStyle.Title6, color = CustomColor.Gray)
                    innerTextField()
                }
            })

        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .clickable(
                    onClick = {
                        if (isValidPhoneNumber(phoneNumber) && buttonState)
                            event(phoneNumber)
                    }, interactionSource = remember {
                        MutableInteractionSource()
                    }, indication = null
                )
                .size(width = 120.dp, height = 50.dp)
                .background(CustomColor.Button, shape = RoundedCornerShape(size = 13.dp)),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = buttonText,
                style = CustomTextStyle.buttonText
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
    buttonState: Boolean,
    readOnly: Boolean
) {
    var isFocused by remember { mutableStateOf(false) }
    var certificationNumber by remember { mutableStateOf("") }

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
                .background(CustomColor.LightGray, shape = RoundedCornerShape(size = 13.dp))
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            visualTransformation = VisualTransformation.None,
            textStyle = CustomTextStyle.Title6,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            readOnly = readOnly,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (certificationNumber.isEmpty() && !isFocused)
                        Text(text = hint, style = CustomTextStyle.Title6, color = CustomColor.Gray)
                    innerTextField()
                }
            })

        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .clickable(
                    onClick = {
                        if (buttonState)
                            event(certificationNumber)
                    }, interactionSource = remember {
                        MutableInteractionSource()
                    }, indication = null
                )
                .size(width = 120.dp, height = 50.dp)
                .background(CustomColor.Button, shape = RoundedCornerShape(size = 13.dp)),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = buttonText,
                style = CustomTextStyle.buttonText
            )
        }
    }
}