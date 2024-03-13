package newjeans.bunnies.auth.presentation


import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration

import newjeans.bunnies.auth.presentation.ui.Explanation
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import newjeans.bunnies.auth.Constant.numberPattern
import newjeans.bunnies.auth.Constant.passwordPattern
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
import newjeans.bunnies.auth.presentation.ui.StatusMessageIcon
import newjeans.bunnies.auth.utils.MaskNumberVisualTransformation
import newjeans.bunnies.auth.viewmodel.AuthViewModel
import newjeans.bunnies.auth.viewmodel.SignupViewModel
import newjeans.bunnies.designsystem.R
import newjeans.bunnies.designsystem.theme.CustomColor
import newjeans.bunnies.designsystem.theme.TextRule.birthMaxCharacterCount
import newjeans.bunnies.designsystem.theme.TextRule.certificationNumberMaxCharacterCount
import newjeans.bunnies.designsystem.theme.TextRule.phoneNumberMaxCharacterCount
import newjeans.bunnies.designsystem.theme.CustomTextStyle

import java.time.LocalDate
import java.time.format.DateTimeFormatter


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
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignupScreen(
    onNavigateToLogin: () -> Unit,
    signupViewModel: SignupViewModel,
    authViewModel: AuthViewModel,
) {
    val countrys by authViewModel.countrys.observeAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignupAppBar(onNavigateToLogin, signupViewModel)
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                item {
                    IdUI(signupViewModel)
                    Spacer(modifier = Modifier.height(30.dp))
                    PasswordUI(signupViewModel)
                    Spacer(modifier = Modifier.height(30.dp))
                    PhoneNumberUI(viewModel = signupViewModel)
                    CountryUI(signupViewModel, countrys)
                    Spacer(modifier = Modifier.height(30.dp))
                    BirthUI(signupViewModel)
                    Spacer(modifier = Modifier.height(30.dp))
                    ConditionsOfUse(signupViewModel)
                    Spacer(modifier = Modifier.height(30.dp))
                    MainButton(event = { signup(signupViewModel) }, message = "계정 만들기")
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }

    LaunchedEffect(signupViewModel.signupState) {
        signupViewModel.signupState.collect {
            if (it.isSuccess) onNavigateToLogin()
        }
    }
}

fun signup(viewModel: SignupViewModel) {
    viewModel.signup()
}


//아이디 입력
@Composable
fun IdUI(
    viewModel: SignupViewModel
) {
    var userId by remember { mutableStateOf("") }
    var userIdState: Boolean? by remember { mutableStateOf(null) }
    var userIdCheck by remember { mutableStateOf("") }
    var userIdCheckStatus: Boolean? by remember { mutableStateOf(null) }

    EditTextLabel(text = "아이디")
    Spacer(modifier = Modifier.height(10.dp))
    IdEditTextEndButton(hint = "아이디", event = {
        if (it.isNotBlank() && userIdState != null) viewModel.checkUser(it)
    }, buttonText = "중복확인", maxValueLength = 12, chageEvent = {
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

    if (userIdState == true) viewModel.userId(userId)
    else viewModel.userId("")

    userIdState = if (userIdCheckStatus == false && userIdCheck == userId) {
        StatusMessage("이미 존재 하는 아이디 입니다", true)
        null
    } else if (userIdCheckStatus == true && userIdCheck == userId) {
        StatusMessage("사용가능한 아이디 입니다", false)
        true
    } else {
        Explanation("영문 대소문자와 숫자만 사용하여 4~12자의 아이디를 입력해주세요")
        false
    }

}


//비밀번호 입력
@Composable
fun PasswordUI(
    viewModel: SignupViewModel
) {
    var passwordState: Boolean? by remember { mutableStateOf(null) }


    var passwordCheck by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    if (passwordState == true) viewModel.password(password)
    else viewModel.password("")

    EditTextLabel(text = "비밀번호")
    Spacer(modifier = Modifier.height(10.dp))
    PasswordEditText(
        hint = "비밀번호",
        passwordOnValueChange = {
            password = it
        },
    )
    Spacer(modifier = Modifier.height(10.dp))
    PasswordEditText(hint = "비밀번호 확인", passwordOnValueChange = {
        passwordCheck = it
    })
    if (passwordPattern(password)) {
        passwordState =
            if (passwordPattern(password) && password != passwordCheck) {
                StatusMessage("비밀번호와 비밀번호 확인이 일치하지 않습니다.", true)
                false
            } else if (password == passwordCheck && password.isNotEmpty() && passwordCheck.isNotEmpty()) {
                StatusMessage("사용가능한 비밀번호입니다.", false)
                true
            } else {
                Explanation("영문 대소문자와 숫자, 특수문자만 하용하여 8~16자의 비밀번호를 입력해주세요")
                false
            }
    } else if (!passwordPattern(password) && password.isNotBlank()) {
        StatusMessage("영문 대소문자와 숫자, 특수문자만 하용하여 8~16자의 비밀번호를 입력해주세요", true)
    } else {
        Explanation("영문 대소문자와 숫자, 특수문자만 하용하여 8~16자의 비밀번호를 입력해주세요")
    }
}


//전화번호 인증
@Composable
fun PhoneNumberUI(
    viewModel: SignupViewModel
) {
    var state: Boolean? by remember { mutableStateOf(null) }
    var phoneNumber by remember { mutableStateOf("") }

    var certification by remember { mutableStateOf(false) }
    var verify: Boolean? by remember { mutableStateOf(null) }

    var phoneNumberButtonText by remember { mutableStateOf("인증번호 받기") }
    var phoneNumberButtonState by remember { mutableStateOf(true) }
    var phoneNumberTextReadOnlyState by remember { mutableStateOf(false) }

    var certificationNumberButtonState by remember { mutableStateOf(false) }
    var certificationNumberTextReadOnlyState by remember { mutableStateOf(false) }
    var certificationNumberButtonText by remember { mutableStateOf("확인") }

    EditTextLabel(text = "전화번호")
    Spacer(modifier = Modifier.height(10.dp))
    PhoneNumberEditTextEndButton(
        hint = "전화번호",
        event = {
            viewModel.checkPhoneNumber(it)
        },
        buttonText = phoneNumberButtonText,
        maxValueLength = phoneNumberMaxCharacterCount,
        buttonState = phoneNumberButtonState,
        readOnly = phoneNumberTextReadOnlyState
    )
    AnimatedVisibility(!certification) {
        if (state == false) StatusMessage(message = "사용 중인 전화번호입니다.", errorStatus = true)
        else StatusMessage(message = "", errorStatus = false)
    }
    Spacer(modifier = Modifier.height(10.dp))
    AnimatedVisibility(certification) {
        Column {
            CertificationNumberEditTextEndButton(
                hint = "인증번호",
                event = {
                    if (it.length == 6) viewModel.verify(
                        certificationNumber = it, phoneNumber = phoneNumber
                    )
                },
                buttonText = certificationNumberButtonText,
                maxValueLength = certificationNumberMaxCharacterCount,
                buttonState = certificationNumberButtonState,
                readOnly = certificationNumberTextReadOnlyState
            )
            if (verify == false) StatusMessage(message = "인증번호를 다시 입력해주세요", errorStatus = true)
            else StatusMessage(message = "", errorStatus = false)
        }
    }
    Spacer(modifier = Modifier.height(10.dp))

    //사용가능한 핸드폰이면 반응
    LaunchedEffect(viewModel.phoneNumberCheckState) {
        viewModel.phoneNumberCheckState.collect {
            state = it.isSuccess
            if (state == true) {
                phoneNumberTextReadOnlyState = true
                phoneNumber = it.value
                viewModel.certification(it.value)
            }
        }
    }

    //인증번호 전송 되면 반응
    LaunchedEffect(viewModel.phoneNumberCertificationState) {
        viewModel.phoneNumberCertificationState.collect {
            if (it.isSuccess) {
                certification = true
                phoneNumberButtonText = "전송됨"
                phoneNumberButtonState = false
                certificationNumberButtonState = true
            }
            if (it.error.isNotEmpty()) {
                phoneNumberButtonText = "전송 실패"
                certification = false
            }
        }
    }

    //인증번호 성공하면 반응
    LaunchedEffect(viewModel.certificationNumberVerifyState) {
        viewModel.certificationNumberVerifyState.collect {
            if (it.isSuccess) {
                verify = true
                certificationNumberButtonText = "인증됨"
                certificationNumberButtonState = false
                certificationNumberTextReadOnlyState = true
                viewModel.phoneNumber(phoneNumber)
            }

            if (it.error.isNotEmpty()) {
                verify = false
            }
        }
    }
}

@Composable
fun BirthUI(
    viewModel: SignupViewModel
) {
    var birth by remember { mutableStateOf("") }
    var birthState: Boolean? by remember { mutableStateOf(null) }

    if (birthState == true) viewModel.birth(birth)
    else viewModel.birth("")

    EditTextLabel(text = "생년월일")
    Spacer(modifier = Modifier.height(10.dp))
    SelectBirth({ birth = it }, birthState)
    if (birth.length == 8) {
        birthState = if (Validator.isValidDateOfBirth(birth)) {
            LocalDate.parse(birth, dateFormatter) < LocalDate.now()
        } else {
            false
        }
    }
}

//나라 선택
@Composable
fun CountryUI(
    viewModel: SignupViewModel, countrys: List<String>?
) {
    EditTextLabel(text = "나라")
    Spacer(modifier = Modifier.height(10.dp))
    SelectCountryRadioButton(countrys ?: listOf(), viewModel)
}


//Top App bar
@Composable
fun SignupAppBar(
    onNavigateToLogin: () -> Unit,
    viewModel: SignupViewModel
) {
    Box(
        modifier = Modifier
            .height(60.dp)
            .fillMaxSize(),
    ) {
        IconButton(
            onClick = {
                onNavigateToLogin()
                viewModel.deleteData()
            }, modifier = Modifier
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
                style = CustomTextStyle.TitleLarge
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

//약관 동의 UI
@Composable
fun ConditionsOfUse(
    viewModel: SignupViewModel
) {

    var useAgreementStatus by remember { mutableStateOf(false) }
    var informationConsentStatus by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
    ) {
        Text(
            text = "아래 약관에 모두 동의합니다.", style = CustomTextStyle.TitleMedium
        )
        Spacer(Modifier.height(15.dp))
        Row(
            modifier = Modifier.align(Alignment.Start)
        ) {
            CheckBox(useAgreementStatus) {
                useAgreementStatus = it
            }
            Spacer(Modifier.width(17.dp))
            Text(
                text = "이용약관 동의", style = CustomTextStyle.Title2, textAlign = TextAlign.Center
            )
            Text(
                text = "보기 >",
                style = CustomTextStyle.Title3,
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
                text = "개인정보 취급방침 동의", style = CustomTextStyle.Title2, textAlign = TextAlign.Center
            )
            Text(
                text = "보기 >",
                style = CustomTextStyle.Title3,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1F)
            )
        }


    }
}


//생일 입력
@Composable
fun SelectBirth(
    birthOnValueChange: (String) -> Unit,
    errorStatus: Boolean?
) {
    var birth by remember { mutableStateOf("") }

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
            .background(CustomColor.LightGray, shape = RoundedCornerShape(size = 13.dp)),

        textStyle = CustomTextStyle.Title6,
        maxLines = 1,
        visualTransformation = MaskNumberVisualTransformation("0000-00-00", '0'),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
               Box{
                   if (birth.isEmpty()) Text(
                       text = "YYYY-MM-DD",
                       style = CustomTextStyle.Title6,
                       color = CustomColor.Gray
                   )
                   innerTextField()
               }
                Spacer(Modifier.weight(1F))
                StatusMessageIcon(errorStatus)
            }
        })
}