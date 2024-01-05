package newjeans.bunnies.auth.presentation


import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import newjeans.bunnies.auth.presentation.ui.AppIconImage
import newjeans.bunnies.auth.presentation.ui.CheckBox
import newjeans.bunnies.auth.presentation.ui.ErrorMessageText
import newjeans.bunnies.auth.presentation.ui.MainButton
import newjeans.bunnies.auth.presentation.ui.PasswordStatusCheckBox
import newjeans.bunnies.auth.presentation.ui.TextButton
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
    onNavigateToSignup: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppIconImage()
        LoginIdEditText()
        LoginPasswordEditText(loginViewModel)
        Spacer(modifier = Modifier.height(5.dp))
        LoginErrorMessage(loginViewModel)
        Spacer(modifier = Modifier.height(20.dp))
        AutoLoginLayout(loginViewModel)
        Spacer(modifier = Modifier.height(15.dp))
        MainButton("로그인") {
            loginViewModel.login(
                autoLogin = loginViewModel.autoLoginStatus.value ?: false
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        TextButton("계정 만들기", onNavigateToSignup)
    }
}

@Composable
fun AutoLoginLayout(loginViewModel: LoginViewModel) {

    val autoLoginStatus by loginViewModel.autoLoginStatus.observeAsState()

    Row(
        modifier = Modifier
            .padding(start = 40.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically

    ) {
        CheckBox(autoLoginStatus ?: false) {
            loginViewModel.autoLogin(it)
        }
        Spacer(modifier = Modifier.width(17.dp))
        Text(text = "로그인 유지하기", style = authText.bodyMedium)
    }
}

@Composable
fun LoginErrorMessage(loginViewModel: LoginViewModel) {
    val hidePassword by loginViewModel.loginErrorStatus.observeAsState()

    Row(
        modifier = Modifier
            .padding(start = 40.dp)
            .fillMaxWidth(),
    ) {
        AnimatedVisibility(
            visible = hidePassword ?: false,
            enter = fadeIn(animationSpec = tween(durationMillis = 100, easing = LinearEasing)),
            exit = fadeOut(animationSpec = tween(durationMillis = 100, easing = LinearEasing))
        ) {
            ErrorMessageText("아이디와 비밀번호가 일치하지 않습니다")
        }

        AnimatedVisibility(
            visible = !(hidePassword ?: false),
            enter = fadeIn(animationSpec = tween(durationMillis = 100, easing = LinearEasing)),
            exit = fadeOut(animationSpec = tween(durationMillis = 100, easing = LinearEasing))
        ) {
            Spacer(modifier = Modifier.height(30.dp))
        }

    }
}

@Composable
@Preview
fun LoginIdEditText() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        BasicTextField(value = userId,
            onValueChange = {
                if (it.length <= idMaxValueLength){
                    userId = it
                }
            },
            modifier = Modifier
                .height(65.dp)
                .padding(top = 6.dp, start = 24.dp, end = 24.dp)
                .background(AuthEditTextColor, shape = RoundedCornerShape(size = 13.dp)),
            singleLine = false,
            visualTransformation = VisualTransformation.None,
            textStyle = authText.bodyMedium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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
                    Box {
                        innerTextField()
                        if (userId.isEmpty()) {
                            Text("아이디", style = authText.bodySmall)
                        }
                    }

                }
            })
    }

}

@Composable
fun LoginPasswordEditText(
    loginViewModel: LoginViewModel
) {
    val hidePassword by loginViewModel.hidePassword.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        BasicTextField(value = password,
            onValueChange = {
                if (it.length <= passwordMaxValueLength) password = it
            },
            modifier = Modifier
                .height(65.dp)
                .padding(top = 6.dp, start = 24.dp, end = 24.dp)
                .background(AuthEditTextColor, shape = RoundedCornerShape(size = 13.dp))
                .border(0.dp, Color.Transparent), // 테두리 제거
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
                    Spacer(modifier = Modifier.width(17.dp))
                    Box {
                        innerTextField()
                        if (password.isEmpty()) {
                            Text("비밀번호", style = authText.bodySmall)
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    PasswordStatusCheckBox(loginViewModel.hidePassword.value ?: false) {
                        loginViewModel.hidePassword(it)
                    }
                }
            })
    }
}