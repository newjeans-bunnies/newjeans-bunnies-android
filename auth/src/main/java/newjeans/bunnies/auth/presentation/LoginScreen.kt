package newjeans.bunnies.auth.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import newjeans.bunnies.designsystem.R
import newjeans.bunnies.designsystem.theme.AuthButtonColor
import newjeans.bunnies.designsystem.theme.AuthEditTextColor
import newjeans.bunnies.designsystem.theme.authText


private var id by mutableStateOf("")
private var password by mutableStateOf("")
private var passwordStatus by mutableStateOf(false)
private var autologinStatus by mutableStateOf(false)

@Composable
fun LoginScreen(
    onNavigateToSignup: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppIconImage()
        IdEditText()
        PasswordEditText()
        AutoLoginCheckBox()
        LoginButton()
        SignupButton(onNavigateToSignup)
    }
}

@Composable
fun AppIconImage() {
    val image: Painter = painterResource(id = R.drawable.main_image)
    Image(
        painter = image,
        contentDescription = "",
        modifier = Modifier.padding(
            start = 100.dp,
            end = 100.dp,
            top = 80.dp
        )
    )
}

@Composable
fun IdIconImage() {
    val image = painterResource(id = R.drawable.ic_id)
    Image(
        painter = image,
        contentDescription = "",
    )
}

@Composable
fun PasswordIconImage() {
    val image = painterResource(id = R.drawable.ic_password)
    Image(
        painter = image,
        contentDescription = ""
    )
}

@Composable
@Preview(name = "IdEditText")
fun IdEditText() {
    BasicTextField(
        value = id,
        onValueChange = { newText -> id = newText },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 30.dp, end = 30.dp)
            .background(AuthEditTextColor, shape = RoundedCornerShape(size = 13.dp)),
        visualTransformation = VisualTransformation.None,
        textStyle = authText.bodyMedium,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IdIconImage()
                Spacer(modifier = Modifier.width(16.dp))
                innerTextField()
            }
        }
    )
}

@Composable
@Preview(name = "PasswordEditText")
fun PasswordEditText() {
    BasicTextField(
        value = password,
        onValueChange = { newText -> id = newText },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 30.dp, end = 30.dp)
            .background(AuthEditTextColor, shape = RoundedCornerShape(size = 13.dp)),
        visualTransformation = VisualTransformation.None,
        textStyle = authText.bodyMedium,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PasswordIconImage()
                Spacer(modifier = Modifier.width(16.dp))
                innerTextField()
                PasswordStatusCheckBox()
            }
        }
    )
}

@Composable
fun AutoLoginCheckBox() {
    CustomCheckbox(
        checked = autologinStatus,
        onPainter = painterResource(id = R.drawable.ic_check_box_true),
        offPainter = painterResource(id = R.drawable.ic_check_box_falue),
        onCheckedChange = { autologinStatus = it },
    )
}

@Composable
fun PasswordStatusCheckBox() {
    CustomCheckbox(
        checked = passwordStatus,
        onPainter = painterResource(id = R.drawable.ic_hide),
        offPainter = painterResource(id = R.drawable.ic_show),
        onCheckedChange = { passwordStatus = it },
    )
}


@Composable
fun LoginButton() {
    Button(
        onClick = {

        },
        colors = ButtonDefaults.buttonColors(AuthButtonColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
            .background(color = AuthButtonColor, shape = RoundedCornerShape(size = 13.dp))

    ) {
        Text(
            text = "로그인",
            style = authText.titleMedium
        )
    }
}

@Composable
fun SignupButton(onNavigateToSignup: () -> Unit){
    Box(
        modifier = Modifier
            .clickable(
                onClick = onNavigateToSignup,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null
            )
    ) {
        Text(
            text = "계정 만들기",
            style = authText.labelMedium
        )
    }
}



@Composable
fun CustomCheckbox(
    checked: Boolean,
    onPainter: Painter,
    offPainter: Painter,
    onCheckedChange: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier
            .clickable(
                onClick = {
                    onCheckedChange(!checked)
                },
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null
            ),

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