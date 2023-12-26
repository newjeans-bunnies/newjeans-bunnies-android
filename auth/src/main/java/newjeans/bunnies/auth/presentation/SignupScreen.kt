package newjeans.bunnies.auth.presentation


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import newjeans.bunnies.auth.presentation.ui.EditTextEndButton
import newjeans.bunnies.auth.presentation.ui.EditTextLabel
import newjeans.bunnies.auth.presentation.ui.PasswordEditText
import newjeans.bunnies.auth.presentation.ui.SelectCountryRadioButton

import newjeans.bunnies.auth.viewmodel.SignupViewModel
import newjeans.bunnies.designsystem.R
import newjeans.bunnies.designsystem.theme.authText


@Composable
fun SignupScreen(
    signupViewModel: SignupViewModel,
    onNavigateToLogin: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignupAppBar(onNavigateToLogin)
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            EditTextLabel(text = "아이디")
            Spacer(modifier = Modifier.height(10.dp))
            EditTextEndButton(hint = "아이디", event = {}, buttonText = "중복확인", maxValueLength = 10)
            Spacer(modifier = Modifier.height(35.dp))
            EditTextLabel(text = "비밀번호")
            Spacer(modifier = Modifier.height(10.dp))
            PasswordEditText(hint = "비밀번호")
            Spacer(modifier = Modifier.height(10.dp))
            PasswordEditText(hint = "비밀번호 확인")
            Spacer(modifier = Modifier.height(35.dp))
            EditTextLabel(text = "전화번호")
            Spacer(modifier = Modifier.height(10.dp))
            EditTextEndButton(hint = "전화번호", event = {}, buttonText = "인증번호 받기", maxValueLength = 11)
            Spacer(modifier = Modifier.height(10.dp))
            EditTextEndButton(hint = "인증번호", event = {}, buttonText = "확인", maxValueLength = 6)
            Spacer(modifier = Modifier.height(35.dp))
            EditTextLabel(text = "나라")
            Spacer(modifier = Modifier.height(10.dp))
            SelectCountryRadioButton()
            Text(
                text = "아래 약관에 모두 동의합니다.",
                style = authText.labelLarge
            )
        }
    }

}

@Composable
@Preview
fun SignupScreen(
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignupAppBar { }
        Spacer(modifier = Modifier.height(50.dp))
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            EditTextLabel(text = "아이디")
            Spacer(modifier = Modifier.height(10.dp))
            EditTextEndButton(hint = "아이디", event = {}, buttonText = "중복확인", maxValueLength = 10)
            Spacer(modifier = Modifier.height(35.dp))
            EditTextLabel(text = "비밀번호")
            Spacer(modifier = Modifier.height(10.dp))
            PasswordEditText(hint = "비밀번호")
            Spacer(modifier = Modifier.height(10.dp))
            PasswordEditText(hint = "비밀번호 확인")
            Spacer(modifier = Modifier.height(35.dp))
            EditTextLabel(text = "전화번호")
            Spacer(modifier = Modifier.height(10.dp))
            EditTextEndButton(hint = "전화번호", event = {}, buttonText = "인증번호 받기", maxValueLength = 11)
            Spacer(modifier = Modifier.height(10.dp))
            EditTextEndButton(hint = "인증번호", event = {}, buttonText = "확인", maxValueLength = 6)
            Spacer(modifier = Modifier.height(35.dp))
            EditTextLabel(text = "나라")
            Spacer(modifier = Modifier.height(10.dp))
            SelectCountryRadioButton()
            Text(
                text = "아래 약관에 모두 동의합니다.",
                style = authText.labelLarge
            )
        }
    }

}

@Composable
fun SignupAppBar(
    onNavigateToLogin: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(60.dp)
            .fillMaxSize()
    ) {
        IconButton(
            onClick = onNavigateToLogin,
            modifier = Modifier
                .padding(start = 20.dp)
                .height(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = ""
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "회원가입",
                textAlign = TextAlign.Center,
                style = authText.headlineLarge
            )
        }
    }
}