package newjeans.bunnies.auth.presentation


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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import newjeans.bunnies.auth.presentation.ui.AppIconImage
import newjeans.bunnies.auth.presentation.ui.AutoLoginCheckBox
import newjeans.bunnies.auth.presentation.ui.EditTextLabel
import newjeans.bunnies.auth.presentation.ui.ErrorMessageText
import newjeans.bunnies.auth.presentation.ui.MainButton
import newjeans.bunnies.auth.presentation.ui.PasswordStatusCheckBox
import newjeans.bunnies.auth.presentation.ui.image.IdIconImage
import newjeans.bunnies.auth.presentation.ui.image.PasswordIconImage
import newjeans.bunnies.auth.viewmodel.LoginViewModel

import newjeans.bunnies.designsystem.theme.AuthEditTextColor
import newjeans.bunnies.designsystem.theme.authText


private var userId by mutableStateOf("")
private var password by mutableStateOf("")

const val passwordMaxValueLength = 30
const val idMaxValueLength = 10

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onNavigateToSignup: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppIconImage()
        IdEditText()
        Spacer(modifier = Modifier.height(30.dp))
        PasswordEditText(loginViewModel)
        Spacer(modifier = Modifier.height(6.dp))
        LoginErrorMessage(loginViewModel)
        Spacer(modifier = Modifier.height(3.dp))
        AutoLoginLayout(loginViewModel)
        Spacer(modifier = Modifier.height(40.dp))
        MainButton("로그인"){
            loginViewModel.login(userId, password)
        }
        Spacer(modifier = Modifier.height(24.dp))
        MainButton("계정 만들기", onNavigateToSignup)
    }
}

@Composable
fun AutoLoginLayout(loginViewModel: LoginViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 35.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AutoLoginCheckBox(loginViewModel.autoLogin.value ?: false) {
            loginViewModel.autoLogin(it)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "로그인 유지하기", style = authText.bodyMedium)
    }
}

@Composable
fun LoginErrorMessage(loginViewModel: LoginViewModel) {
    val hidePassword by loginViewModel.loginError.observeAsState()

    AnimatedVisibility(
        visible = hidePassword ?: false,
        enter = fadeIn(animationSpec = tween(durationMillis = 100, easing = LinearEasing)),
        exit = fadeOut(animationSpec = tween(durationMillis = 100, easing = LinearEasing))
    ) {
        ErrorMessageText("비밀번호와 아이디가 일치하지 않습니다")
    }
}

@Composable
@Preview
fun IdEditText() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
    ) {
        EditTextLabel("아이디")
        Spacer(modifier = Modifier.height(6.dp))
        BasicTextField(value = userId,
            onValueChange = {
                if (it.length <= idMaxValueLength) userId = it
            },
            modifier = Modifier
                .height(70.dp)
                .padding(top = 6.dp)
                .background(AuthEditTextColor, shape = RoundedCornerShape(size = 13.dp)),
            visualTransformation = VisualTransformation.None,
            textStyle = authText.bodyMedium,
            maxLines = 1,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IdIconImage()
                    Spacer(modifier = Modifier.width(16.dp))
                    innerTextField()
                }
            })
    }

}

@Composable
fun PasswordEditText(
    loginViewModel: LoginViewModel
) {
    val hidePassword by loginViewModel.hidePassword.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
    ) {
        EditTextLabel(text = "비밀번호")
        Spacer(modifier = Modifier.height(6.dp))
        BasicTextField(value = password,
            onValueChange = {
                if (it.length <= passwordMaxValueLength) password = it
            },
            modifier = Modifier
                .height(70.dp)
                .padding(top = 6.dp)
                .background(AuthEditTextColor, shape = RoundedCornerShape(size = 13.dp)),
            visualTransformation = if (hidePassword == true) VisualTransformation.None
            else PasswordVisualTransformation(),

            textStyle = authText.bodyMedium,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(4.dp))
                    PasswordIconImage()
                    Spacer(modifier = Modifier.width(20.dp))
                    innerTextField()
                    Spacer(modifier = Modifier.weight(1f))
                    PasswordStatusCheckBox(loginViewModel.autoLogin.value ?: false) {
                        loginViewModel.autoLogin(it)
                    }
                }
            })
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