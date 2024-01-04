package newjeans.bunnies.auth.presentation


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import newjeans.bunnies.auth.presentation.ui.CertificationNumberEditTextEndButton
import newjeans.bunnies.auth.presentation.ui.CheckBox
import newjeans.bunnies.auth.presentation.ui.CheckPasswordEditText
import newjeans.bunnies.auth.presentation.ui.IdEditTextEndButton
import newjeans.bunnies.auth.presentation.ui.EditTextLabel
import newjeans.bunnies.auth.presentation.ui.ErrorMessageText
import newjeans.bunnies.auth.presentation.ui.MainButton
import newjeans.bunnies.auth.presentation.ui.PasswordEditText
import newjeans.bunnies.auth.presentation.ui.PhoneNumberEditTextEndButton
import newjeans.bunnies.auth.presentation.ui.SelectCountryRadioButton
import newjeans.bunnies.auth.viewmodel.SignupViewModel
import newjeans.bunnies.designsystem.R
import newjeans.bunnies.designsystem.theme.authText
import newjeans.bunnies.network.auth.dto.reqeust.SignupReqeustDto


@Composable
fun SignupScreen(
    signupViewModel: SignupViewModel, onNavigateToLogin: () -> Unit
) {
    val hidePassword by signupViewModel.hidePassword.observeAsState()
    val hideCheckPassword by signupViewModel.hideCheckPassword.observeAsState()
    val password by signupViewModel.password.observeAsState()
    val checkPassword by signupViewModel.checkPassword.observeAsState()
    val userId by signupViewModel.userId.observeAsState()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignupAppBar(onNavigateToLogin)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            EditTextLabel(text = "아이디")
            Spacer(modifier = Modifier.height(10.dp))
            IdEditTextEndButton(hint = "아이디", event = { userId ->
                if (userId.isNotBlank())
                    signupViewModel.checkUser(userId)
            }, buttonText = "중복확인", maxValueLength = 10, chageEvent = {
                signupViewModel.userId(it)
            })
            LoginErrorMessage(signupViewModel)
            Spacer(modifier = Modifier.height(35.dp))
            EditTextLabel(text = "비밀번호")
            Spacer(modifier = Modifier.height(10.dp))
            PasswordEditText(hint = "비밀번호", hidePassword ?: false) {
                signupViewModel.hidePassword(it)
            }
            Spacer(modifier = Modifier.height(10.dp))
            CheckPasswordEditText(hint = "비밀번호 확인", hideCheckPassword ?: false) {
                signupViewModel.hideCheckPassword(it)
            }
            Spacer(modifier = Modifier.height(35.dp))
            EditTextLabel(text = "전화번호")
            Spacer(modifier = Modifier.height(10.dp))
            PhoneNumberEditTextEndButton(
                hint = "전화번호", event = {}, buttonText = "인증번호 받기", maxValueLength = 11
            )
            Spacer(modifier = Modifier.height(10.dp))
            CertificationNumberEditTextEndButton(hint = "인증번호", event = {}, buttonText = "확인", maxValueLength = 6)
            Spacer(modifier = Modifier.height(35.dp))
            EditTextLabel(text = "나라")
            Spacer(modifier = Modifier.height(10.dp))
            SelectCountryRadioButton(listOf("KR", "JP", "CN", "US"))
            Spacer(modifier = Modifier.height(30.dp))
            ConditionsOfUse(signupViewModel)
            Spacer(modifier = Modifier.height(30.dp))
//            MainButton(event = { signup(signupViewModel, SignupReqeustDto()) }, message = "계정 만들기")
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}


//Top App bar
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
            onClick = onNavigateToLogin, modifier = Modifier
                .padding(start = 10.dp)
                .height(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back), contentDescription = ""
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

fun signup(signupViewModel: SignupViewModel, signupReqeustDto: SignupReqeustDto) {
    signupViewModel.signup(signupReqeustDto)
}


//약관 동의 UI
@Composable
fun ConditionsOfUse(
    signupViewModel: SignupViewModel
) {
    val useAgreementStatus by signupViewModel.useAgreementStatus.observeAsState()
    val informationConsentStatus by signupViewModel.informationConsentStatus.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
    ) {
        Text(
            text = "아래 약관에 모두 동의합니다.", style = authText.labelLarge
        )
        Spacer(Modifier.height(17.dp))
        Row(
            modifier = Modifier.align(Alignment.Start)
        ) {
            CheckBox(useAgreementStatus ?: false) {
                signupViewModel.useAgreementButton(it)
            }
            Spacer(Modifier.width(17.dp))
            Text(
                text = "이용약관 동의",
                style = authText.displayMedium,
                textAlign = TextAlign.Center
            )
            Text(
                text = "보기 >",
                style = authText.displayMedium,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1F)
            )
        }
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.align(Alignment.Start)
        ) {
            CheckBox(informationConsentStatus ?: false) {
                signupViewModel.informationConsentButton(it)
            }
            Spacer(Modifier.width(17.dp))
            Text(
                text = "개인정보 취급방침 동의",
                style = authText.displayMedium,
                textAlign = TextAlign.Center
            )
            Text(
                text = "보기 >",
                style = authText.displayMedium,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1F)
            )
        }


    }
}


@Composable
fun LoginErrorMessage(signupViewModel: SignupViewModel) {
    val userCheckStatus by signupViewModel.userCheckStatus.observeAsState()

    Row(
        modifier = Modifier
            .padding(start = 40.dp)
            .fillMaxWidth(),
    ) {
        AnimatedVisibility(
            visible = (userCheckStatus == false),
            enter = fadeIn(animationSpec = tween(durationMillis = 100, easing = LinearEasing)),
            exit = fadeOut(animationSpec = tween(durationMillis = 100, easing = LinearEasing))
        ) {
            ErrorMessageText("이미 존재하는 아이디 입니다")
        }
    }
}