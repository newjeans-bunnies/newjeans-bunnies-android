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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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

import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import newjeans.bunnies.auth.AuthActivity

import newjeans.bunnies.auth.presentation.ui.CertificationNumberEditTextEndButton
import newjeans.bunnies.auth.presentation.ui.CheckBox
import newjeans.bunnies.auth.presentation.ui.CheckPasswordEditText
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

import java.util.concurrent.TimeUnit

private lateinit var checkId: String

private var certificationStatus by mutableStateOf(false)


@Composable
fun SignupScreen(
    viewModel: SignupViewModel, onNavigateToLogin: () -> Unit, activity: AuthActivity
) {
    val hidePassword by viewModel.hidePassword.observeAsState()
    val hideCheckPassword by viewModel.hideCheckPassword.observeAsState()
    val password by viewModel.password.observeAsState()
    val checkPassword by viewModel.checkPassword.observeAsState()
    val userId by viewModel.userId.observeAsState()
    val checkUserId by viewModel.checkUserId.observeAsState()
    val brith by viewModel.birth.observeAsState()

    val userIdErrorStatus by viewModel.userIdCheckStatus.observeAsState()
    val passwordErrorStatus by viewModel.passwordCheckStatus.observeAsState()

    val auth: FirebaseAuth = Firebase.auth
    auth.setLanguageCode("kr")

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
                if (userId.isNotBlank()) viewModel.checkUser(userId)
            }, buttonText = "중복확인", maxValueLength = 10, chageEvent = {
                viewModel.userId(it)
            })
            if (userIdErrorStatus == false && checkUserId == userId) {
                StatusMessage("이미 존재 하는 아이디 입니다", true)
            } else if (userIdErrorStatus == true && checkUserId == userId) {
                StatusMessage("사용가능한 아이디 입니다", false)
            } else {
                viewModel.userIdCheckStatus(null)
            }
            Spacer(modifier = Modifier.height(35.dp))
            EditTextLabel(text = "비밀번호")
            Spacer(modifier = Modifier.height(10.dp))
            PasswordEditText(hint = "비밀번호", hidePassword ?: false, passwordOnValueChange = {
                viewModel.password(it)
                Log.d("비밀번호", it)
            }, checkEvent = {
                viewModel.hidePassword(it)
            })
            Spacer(modifier = Modifier.height(10.dp))
            CheckPasswordEditText(hint = "비밀번호 확인",
                hideCheckPassword ?: false,
                passwordOnValueChange = {
                    viewModel.checkPassword(it)
                },
                checkEvent = {
                    viewModel.hideCheckPassword(it)
                })

            if (checkPasswordPattern(password ?: "")) {
                if (password != checkPassword && !password.isNullOrEmpty() && !checkPassword.isNullOrEmpty()) {
                    StatusMessage("비밀번호와 비밀번호 확인이 일치하지 않습니다.", true)
                    viewModel.passwordCheckStatus(false)
                } else if (password == checkPassword && !password.isNullOrEmpty() && !checkPassword.isNullOrEmpty()) {
                    StatusMessage("사용가능한 비밀번호입니다.", false)
                    viewModel.passwordCheckStatus(true)
                }
            } else if (!checkPasswordPattern(password ?: "") && !password.isNullOrBlank()) {
                StatusMessage("비밀번호는 대소문자, 특수문자, 숫자 포함 최소 10글자입니다.", true)
            }

            Spacer(modifier = Modifier.height(35.dp))
            EditTextLabel(text = "전화번호")
            Spacer(modifier = Modifier.height(10.dp))
            PhoneNumberEditTextEndButton(
                hint = "전화번호", event = {
                    sendVeriftNumber(activity, countryPhoneNumber(it), auth)
                }, buttonText = "인증번호 받기", maxValueLength = phoneNumberMaxCharacterCount
            )
            Spacer(modifier = Modifier.height(10.dp))
            CertificationNumberEditTextEndButton(
                hint = "인증번호", event = {
                    if (certificationStatus) numberCertification(
                        number = it, verificationId = checkId, auth = auth, activity = activity
                    )
                }, buttonText = "확인", maxValueLength = certificationNumberMaxCharacterCount
            )
            Spacer(modifier = Modifier.height(35.dp))
            EditTextLabel(text = "나라")
            Spacer(modifier = Modifier.height(10.dp))
            SelectCountryRadioButton(listOf("KR", "JP", "CN", "US"))
            Spacer(modifier = Modifier.height(35.dp))
            EditTextLabel(text = "생년월일")
            Spacer(modifier = Modifier.height(10.dp))
            SelectBirth(brith, viewModel)
            Spacer(modifier = Modifier.height(30.dp))
            ConditionsOfUse(viewModel)
            Spacer(modifier = Modifier.height(30.dp))
            MainButton(event = { signup(viewModel) }, message = "계정 만들기")
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}

fun countryPhoneNumber(phoneNumber: String): String {
    var phoneEdit = phoneNumber.substring(3)
    phoneEdit = "+8210$phoneEdit"
    return phoneEdit
}

private fun numberCertification(
    number: String, auth: FirebaseAuth, activity: AuthActivity, verificationId: String
) {
    val credential = PhoneAuthProvider.getCredential(verificationId, number)
    signInWithPhoneAuthCredential(credential, auth, activity)
}

private fun sendVeriftNumber(context: AuthActivity, phoneNumber: String, auth: FirebaseAuth) {
    auth.setLanguageCode("kr")
    val options =
        PhoneAuthOptions.newBuilder(auth).setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(context) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
    PhoneAuthProvider.verifyPhoneNumber(options)
}

val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
        certificationStatus = false

        Log.d("onVerificationCompleted", "onVerificationCompleted:$credential")
    }

    override fun onVerificationFailed(e: FirebaseException) {
        certificationStatus = false

        Log.w("onVerificationFailed", "onVerificationFailed", e)

        if (e is FirebaseAuthInvalidCredentialsException) {
            Log.d("onVerificationFailed", "FirebaseAuthInvalidCredentialsException")
        } else if (e is FirebaseTooManyRequestsException) {
            Log.d("onVerificationFailed", "FirebaseTooManyRequestsException")
        } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
            Log.d("onVerificationFailed", "FirebaseAuthMissingActivityForRecaptchaException")
        }
    }

    override fun onCodeSent(
        verificationId: String,
        token: PhoneAuthProvider.ForceResendingToken,
    ) {
        certificationStatus = true
        checkId = verificationId
        Log.d("onCodeSent", "onCodeSent:$verificationId")
    }
}

private fun signInWithPhoneAuthCredential(
    credential: PhoneAuthCredential, auth: FirebaseAuth, activity: AuthActivity
) {
    auth.signInWithCredential(credential).addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                //인증성공
            } else {
                //인증실패
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
fun StatusMessage(message: String, errorStatus: Boolean) {
    Row(
        modifier = Modifier
            .padding(start = 40.dp, top = 5.dp)
            .fillMaxWidth(),
    ) {
        StatusMessageText(message, errorStatus)
    }
}

@Composable
fun SelectBirth(brith: String?, viewModel: SignupViewModel) {
    val isFocused = remember { mutableStateOf(false) }

    BasicTextField(value = brith ?: "",
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
        visualTransformation = MaskNumberVisualTransformation("0000-00-00", '0'),
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

val passwordPattern =
    Regex("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~․!@#\$%^&*()_\\-+=|\\\\;:‘“<>,.?/]).{10,20}\$")


fun checkPasswordPattern(input: String): Boolean {
    return passwordPattern.matches(input)
}