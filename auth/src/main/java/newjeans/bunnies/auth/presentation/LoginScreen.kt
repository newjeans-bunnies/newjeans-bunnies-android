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
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import newjeans.bunnies.auth.presentation.ui.AppIconImage
import newjeans.bunnies.auth.presentation.ui.CheckBox
import newjeans.bunnies.auth.presentation.ui.StatusMessageText
import newjeans.bunnies.auth.presentation.ui.MainButton
import newjeans.bunnies.auth.presentation.ui.TextButton
import newjeans.bunnies.auth.viewmodel.LoginViewModel
import newjeans.bunnies.data.PreferenceManager
import newjeans.bunnies.designsystem.theme.CustomColor
import newjeans.bunnies.designsystem.theme.CustomTextStyle


private const val TAG = "LoginScreen"

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToSignup: () -> Unit,
    prefs: PreferenceManager,
    toMain: () -> Unit
) {

    var userId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var autologinStatus by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppIconImage()
        LoginEditText("아이디", VisualTransformation.None) {
            userId = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        LoginEditText("비밀번호", PasswordVisualTransformation()) {
            password = it
        }
        Spacer(modifier = Modifier.height(5.dp))
        StatusMessage(viewModel)
        Spacer(modifier = Modifier.height(10.dp))
        AutoLoginLayout{
            autologinStatus = it
        }
        Spacer(modifier = Modifier.height(25.dp))
        MainButton("로그인") {
            if (userId.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(
                    autoLogin = viewModel.autoLoginStatus.value ?: false,
                    userId = userId,
                    password = password,
                    prefs = prefs
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        TextButton("계정 만들기") {
            onNavigateToSignup()
        }
    }

    LaunchedEffect(viewModel.loginState) {
        viewModel.loginState.collect {
            if (it.isSuccess) viewModel.getUserDetailInformation(prefs.accessToken, prefs)

            viewModel.userId("")
            viewModel.password("")
        }
    }

    LaunchedEffect(viewModel.getUserDetailInformationState) {
        viewModel.getUserDetailInformationState.collect {
            if (it.isSuccess) {
                toMain()
            }
            if (it.error.isNotEmpty()) {
                prefs.deleteToken()
                prefs.deleteUserData()
            }
        }
    }
}

@Composable
fun AutoLoginLayout(checkEvent: (Boolean) -> Unit) {

    var autoLoginStatus by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(start = 30.dp)
            .height(18.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically

    ) {
        CheckBox(autoLoginStatus) {
            autoLoginStatus = it
            checkEvent(it)
        }
        Spacer(modifier = Modifier.width(7.dp))
        Text(text = "로그인 상태 유지", style = CustomTextStyle.Title5, color = CustomColor.LightBlack)
    }
}

@Composable
fun StatusMessage(loginViewModel: LoginViewModel) {
    var passwordErrorStatus: Boolean? by remember { mutableStateOf(null) }

    LaunchedEffect(loginViewModel.loginState) {
        loginViewModel.loginState.collect {
            passwordErrorStatus = it.isSuccess
        }
    }

    Row(
        modifier = Modifier
            .padding(start = 45.dp)
            .fillMaxWidth(),
    ) {
        AnimatedVisibility(
            visible = passwordErrorStatus == false,
            enter = fadeIn(animationSpec = tween(durationMillis = 100, easing = LinearEasing)),
            exit = fadeOut(animationSpec = tween(durationMillis = 100, easing = LinearEasing))
        ) {
            StatusMessageText("아이디나 비밀번호가 일치하지 않습니다", true)
        }
        AnimatedVisibility(
            visible = passwordErrorStatus != false,
            enter = fadeIn(animationSpec = tween(durationMillis = 100, easing = LinearEasing)),
            exit = fadeOut(animationSpec = tween(durationMillis = 100, easing = LinearEasing))
        ) {
            StatusMessageText("", false)
        }
    }
}

@Composable
fun LoginEditText(
    hint: String,
    visualTransformation: VisualTransformation,
    inputText: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
    ) {
        BasicTextField(value = text,
            onValueChange = { input ->
                inputText(input)
                text = input
            },
            modifier = Modifier
                .height(50.dp)
                .background(CustomColor.LightGray, shape = RoundedCornerShape(size = 13.dp)),

            visualTransformation = visualTransformation,
            textStyle = CustomTextStyle.Title6,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(15.dp))
                    Box {
                        innerTextField()
                        if (text.isEmpty()) {
                            Text(hint, style = CustomTextStyle.Title6, color = CustomColor.Gray)
                        }
                    }
                }
            })
    }
}