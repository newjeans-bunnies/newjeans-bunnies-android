package newjeans.bunnies.auth.presentation


import android.util.Log

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import newjeans.bunnies.auth.AuthActivity

import newjeans.bunnies.auth.Constant.numberPattern
import newjeans.bunnies.auth.Constant.passwordPattern
import newjeans.bunnies.auth.presentation.Validator.changeToInternationalPhoneNumber
import newjeans.bunnies.auth.presentation.Validator.dateFormatter
import newjeans.bunnies.auth.presentation.ui.CertificationNumberEditTextEndButton
import newjeans.bunnies.auth.presentation.ui.CheckBox
import newjeans.bunnies.auth.presentation.ui.IdEditTextEndButton
import newjeans.bunnies.auth.presentation.ui.EditTextLabel
import newjeans.bunnies.auth.presentation.ui.StatusMessageText
import newjeans.bunnies.auth.presentation.ui.MainButton
import newjeans.bunnies.auth.presentation.ui.PasswordEditText
import newjeans.bunnies.auth.presentation.ui.PhoneNumberEditTextEndButton
import newjeans.bunnies.auth.presentation.ui.SelectCountryRadioButton
import newjeans.bunnies.auth.utils.MaskNumberVisualTransformation
import newjeans.bunnies.auth.viewmodel.SignupViewModel
import newjeans.bunnies.designsystem.R
import newjeans.bunnies.designsystem.theme.AuthEditTextColor
import newjeans.bunnies.designsystem.theme.TextRule.birthMaxCharacterCount
import newjeans.bunnies.designsystem.theme.TextRule.certificationNumberMaxCharacterCount
import newjeans.bunnies.designsystem.theme.TextRule.phoneNumberMaxCharacterCount
import newjeans.bunnies.designsystem.theme.authText

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit


private const val TAG = "SignupScreen"


object Validator {

    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyMMdd")

    fun isValidDateOfBirth(date: String): Boolean {
        return try {
            val localDate = LocalDate.parse(date, dateFormatter)
            dateFormatter.format(localDate).equals(date)
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            false
        }
    }

    fun changeToInternationalPhoneNumber(phoneNumber: String): String {
        var phoneEdit = phoneNumber.substring(3)
        phoneEdit = "+8210$phoneEdit"
        return phoneEdit
    }
}

@Composable
fun SignupScreen(
    onNavigateToLogin: () -> Unit,
    viewModel: SignupViewModel = hiltViewModel(),
    context: AuthActivity,
    auth: FirebaseAuth,
    storedVerificationId: String?,
    callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignupAppBar(onNavigateToLogin)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            IdUI(viewModel)
            PasswordUI(viewModel)
            PhoneNumberUI(
                phoneNumberEvent = {
                    startPhoneNumberVerification(
                        changeToInternationalPhoneNumber(it),
                        context, auth, callbacks
                    )
                },
                certificationNumberEvent = {
                    verifyPhoneNumberWithCode(storedVerificationId, it)
                }
            )
            CountryUI(viewModel)
            Spacer(modifier = Modifier.height(35.dp))
            BirthUI(viewModel)
            ConditionsOfUse(viewModel)
            Spacer(modifier = Modifier.height(30.dp))
            MainButton(event = { signup(viewModel) }, message = "계정 만들기")
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    LaunchedEffect(viewModel.signupState) {
        viewModel.signupState.collect {
            if (it.isSuccess) onNavigateToLogin()
        }
    }

}

private fun verifyPhoneNumberWithCode(verificationId: String?, code: String): PhoneAuthCredential {
    // [START verify_with_code]
    return PhoneAuthProvider.getCredential(verificationId!!, code)
}

private fun startPhoneNumberVerification(
    phoneNumber: String,
    context: AuthActivity,
    auth: FirebaseAuth,
    callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
) {
    // [START start_phone_auth]
    val options = PhoneAuthOptions.newBuilder(auth)
        .setPhoneNumber(phoneNumber) // Phone number to verify
        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
        .setActivity(context) // Activity (for callback binding)
        .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
        .build()
    PhoneAuthProvider.verifyPhoneNumber(options)
    // [END start_phone_auth]
}


@Composable
fun IdUI(
    viewModel: SignupViewModel
) {
    var userIdState: Boolean? by remember { mutableStateOf(null) }
    var userIdCheckStatus: Boolean? by remember { mutableStateOf(null) }
    var userIdCheck by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }

    EditTextLabel(text = "아이디")
    Spacer(modifier = Modifier.height(10.dp))
    IdEditTextEndButton(hint = "아이디", event = {
        if (it.isNotBlank()) {
            viewModel.checkUser(it)
        }
    }, buttonText = "중복확인", maxValueLength = 10, chageEvent = {
        userId = it
    })
    LaunchedEffect(viewModel.userIdCheckState) {
        viewModel.userIdCheckState.collect {
            userIdCheck = it.userId
            if (it.isSuccess) {
                userIdCheckStatus = true
            }
            if (it.error.isNotEmpty()) {
                userIdCheckStatus = false
            }
        }
    }
    userIdState = if (userIdCheckStatus == false && userIdCheck == userId) {
        StatusMessage("이미 존재 하는 아이디 입니다", true)
        false
    } else if (userIdCheckStatus == true && userIdCheck == userId) {
        StatusMessage("사용가능한 아이디 입니다", false)
        true
    } else {
        StatusMessage("", false)
        false
    }
}

@Composable
fun PasswordUI(
    viewModel: SignupViewModel
) {
    var passwordState: Boolean? by remember { mutableStateOf(null) }
    var passwordCheck by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }




    EditTextLabel(text = "비밀번호")
    Spacer(modifier = Modifier.height(10.dp))
    PasswordEditText(hint = "비밀번호", passwordOnValueChange = {
        password = it
    })
    Spacer(modifier = Modifier.height(10.dp))
    PasswordEditText(hint = "비밀번호 확인", passwordOnValueChange = {
        passwordCheck = it
    })
    if (passwordPattern(password)) {
        passwordState =
            if (password != passwordCheck && password.isNotEmpty() && passwordCheck.isNotEmpty()) {
                StatusMessage("비밀번호와 비밀번호 확인이 일치하지 않습니다.", true)
                false
            } else if (password == passwordCheck && password.isNotEmpty() && passwordCheck.isNotEmpty()) {
                StatusMessage("사용가능한 비밀번호입니다.", false)
                true
            } else {
                StatusMessage("", true)
                false
            }
    } else if (!passwordPattern(password) && password.isNotBlank()) {
        StatusMessage("비밀번호는 대소문자, 특수문자, 숫자 포함 최소 10글자입니다.", true)
        passwordState = false
    } else {
        StatusMessage("", true)
        passwordState = false
    }
}

@Composable
fun PhoneNumberUI(
    viewModel: SignupViewModel = hiltViewModel(),
    phoneNumberEvent: (String) -> Unit,
    certificationNumberEvent: (String) -> Unit
) {
    var state: Boolean? by remember { mutableStateOf(null) }

    EditTextLabel(text = "전화번호")
    Spacer(modifier = Modifier.height(10.dp))
    PhoneNumberEditTextEndButton(
        hint = "전화번호", event = {
            viewModel.checkPhoneNumber(it)
        }, buttonText = "인증번호 받기", maxValueLength = phoneNumberMaxCharacterCount
    )
    Spacer(modifier = Modifier.height(10.dp))
    CertificationNumberEditTextEndButton(
        hint = "인증번호", event = {
            certificationNumberEvent(it)
        }, buttonText = "확인", maxValueLength = certificationNumberMaxCharacterCount
    )
    if (state == true) {
        StatusMessage(message = "", errorStatus = false)
    } else if (state == false) {
        StatusMessage(message = "사용 중인 전화번호입니다.", errorStatus = true)
    } else {
        StatusMessage(message = "", errorStatus = false)
    }


    LaunchedEffect(viewModel.phoneNumberCheckState) {
        viewModel.phoneNumberCheckState.collect {
            state = it.isSuccess
            if (state == true) phoneNumberEvent(it.value)
        }
    }
}

@Composable
fun BirthUI(
    viewModel: SignupViewModel
) {
    var birth by remember { mutableStateOf("") }
    var birthState: Boolean? by remember { mutableStateOf(null) }

    EditTextLabel(text = "생년월일")
    Spacer(modifier = Modifier.height(10.dp))
    SelectBirth { birth = it }
    if (birth.length == 8) {
        birthState = if (Validator.isValidDateOfBirth(birth)) {
            if (LocalDate.parse(birth, dateFormatter) < LocalDate.now()) {
                StatusMessage("", false)
                true
            } else {
                StatusMessage("다시 적어주세요", true)
                false
            }
        } else {
            StatusMessage("다시 적어주세요", true)
            false
        }
    } else {
        StatusMessage("", false)
    }
}


@Composable
fun CountryUI(
    viewModel: SignupViewModel
) {
    EditTextLabel(text = "나라")
    Spacer(modifier = Modifier.height(10.dp))
    SelectCountryRadioButton(listOf("KR", "JP", "CN", "US"))
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


//상태 메시지
@Composable
fun StatusMessage(message: String, errorStatus: Boolean) {
    Row(
        modifier = Modifier
            .padding(start = 40.dp, top = 5.dp)
            .fillMaxWidth(),
    ) {
        StatusMessageText(message, errorStatus)
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

    var useAgreementStatus by remember { mutableStateOf(false) }
    var informationConsentStatus by remember { mutableStateOf(false) }
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
            CheckBox(useAgreementStatus) {
                useAgreementStatus = it
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
            CheckBox(informationConsentStatus) {
                informationConsentStatus = it
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
fun SelectBirth(
    birthOnValueChange: (String) -> Unit
) {
    var birth by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(value = birth,
        onValueChange = {
            if (it.length <= birthMaxCharacterCount && numberPattern(it)) {
                birth = it
                birthOnValueChange(it)
            }
        },
        modifier = Modifier
            .height(50.dp)
            .padding(start = 30.dp, end = 30.dp)
            .background(AuthEditTextColor, shape = RoundedCornerShape(size = 13.dp))
            .onFocusChanged {
                isFocused = it.isFocused
            },

        textStyle = authText.bodyMedium,
        maxLines = 1,
        visualTransformation = MaskNumberVisualTransformation("0000-00-00", '0'),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 30.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (birth.isEmpty()) Text(text = "YYYY-MM-DD", style = authText.bodySmall)
                innerTextField()
            }
        })
}