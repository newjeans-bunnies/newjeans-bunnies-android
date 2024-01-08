package newjeans.bunnies.auth.presentation


import android.os.Build
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
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
import newjeans.bunnies.designsystem.theme.AuthEditTextColor
import newjeans.bunnies.designsystem.theme.TextRule.birthMaxCharacterCount
import newjeans.bunnies.designsystem.theme.authText
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun SignupScreen(
    signupViewModel: SignupViewModel, onNavigateToLogin: () -> Unit
) {
    val hidePassword by signupViewModel.hidePassword.observeAsState()
    val hideCheckPassword by signupViewModel.hideCheckPassword.observeAsState()
    val password by signupViewModel.password.observeAsState()
    val checkPassword by signupViewModel.checkPassword.observeAsState()
    val userId by signupViewModel.userId.observeAsState()
    val brith by signupViewModel.birth.observeAsState()

    val userIdErrorStatus by signupViewModel.userCheckStatus.observeAsState()

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
                if (userId.isNotBlank()) signupViewModel.checkUser(userId)
            }, buttonText = "중복확인", maxValueLength = 10, chageEvent = {
                signupViewModel.userId(it)
            })
            ErrorMessage(userIdErrorStatus, "이미 존재 하는 아이디 입니다")
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
            CertificationNumberEditTextEndButton(
                hint = "인증번호", event = {}, buttonText = "확인", maxValueLength = 6
            )
            Spacer(modifier = Modifier.height(35.dp))
            EditTextLabel(text = "나라")
            Spacer(modifier = Modifier.height(10.dp))
            SelectCountryRadioButton(listOf("KR", "JP", "CN", "US"))
            Spacer(modifier = Modifier.height(35.dp))
            EditTextLabel(text = "생년월일")
            Spacer(modifier = Modifier.height(10.dp))
            SelectBirth(brith, signupViewModel)
            Spacer(modifier = Modifier.height(30.dp))
            ConditionsOfUse(signupViewModel)
            Spacer(modifier = Modifier.height(30.dp))
            MainButton(event = { signup(signupViewModel) }, message = "계정 만들기")
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
            .fillMaxSize(),
    ) {
        IconButton(
            onClick = onNavigateToLogin, modifier = Modifier
                .padding(start = 10.dp)
                .height(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "",
                modifier = Modifier.height(18.dp)
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

fun signup(signupViewModel: SignupViewModel) {
    signupViewModel.signup()
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
                text = "이용약관 동의", style = authText.displayMedium, textAlign = TextAlign.Center
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
                text = "개인정보 취급방침 동의", style = authText.displayMedium, textAlign = TextAlign.Center
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
fun ErrorMessage(errorStatus: Boolean?, message: String) {

    Row(
        modifier = Modifier
            .padding(start = 40.dp)
            .fillMaxWidth(),
    ) {
        AnimatedVisibility(
            visible = (errorStatus == false),
            enter = fadeIn(animationSpec = tween(durationMillis = 100, easing = LinearEasing)),
            exit = fadeOut(animationSpec = tween(durationMillis = 100, easing = LinearEasing))
        ) {
            ErrorMessageText(message)
        }
    }
}

@Composable
fun SelectBirth(brith: String?, viewModel: SignupViewModel) {
    val isFocused = remember { mutableStateOf(false) }

    BasicTextField(value = formatStringDate(brith ?: ""),
        onValueChange = {
            if (it.length <= birthMaxCharacterCount) {
                viewModel.birth(it)
            }
        },
        modifier = Modifier
            .height(50.dp)
            .padding(start = 30.dp, end = 30.dp)
            .background(AuthEditTextColor, shape = RoundedCornerShape(size = 13.dp))
            .onFocusChanged {
                isFocused.value = it.isFocused
            },

        textStyle = authText.bodyMedium,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 30.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if ((brith ?: "").isEmpty()) Text(text = "YYYY-MM-DD", style = authText.bodySmall)
                innerTextField()
            }
        })
}

fun formatStringDate(inputDate: String): String {
    return try {
        if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
            // 입력된 날짜 문자열을 LocalDate 객체로 파싱
            val date = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyyMMdd"))

            // 출력할 형식으로 포맷팅하여 문자열 반환
            Log.d("날짜", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        } else {
            val date = SimpleDateFormat("yyyyMMdd").parse(inputDate)
            Log.d("날짜1", SimpleDateFormat("yyyy-MM-dd").format(date))

            SimpleDateFormat("yyyy-MM-dd").format(date)
        }
    } catch (e: Exception){
        Log.d("애러", e.message.toString())
        inputDate
    }
}