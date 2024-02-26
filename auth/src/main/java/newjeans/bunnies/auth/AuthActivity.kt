package newjeans.bunnies.auth


import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import newjeans.bunnies.auth.presentation.LoginScreen
import newjeans.bunnies.auth.presentation.SignupScreen

import newjeans.bunnies.auth.presentation.navigation.NavigationRoute
import newjeans.bunnies.auth.state.signup.PhoneNumberCertificationState
import newjeans.bunnies.auth.viewmodel.AuthViewModel
import newjeans.bunnies.auth.viewmodel.SignupViewModel
import newjeans.bunnies.data.PreferenceManager
import newjeans.bunnies.main.MainActivity
import newjeans.bunnies.main.viewmodel.UserViewModel


@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    companion object {
        lateinit var prefs: PreferenceManager
        const val TAG = "AuthActivity"
    }

    private val userViewModel: UserViewModel by viewModels()
    private val signupViewModel: SignupViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var auth: FirebaseAuth


    private var storedVerificationId: String? = ""

    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        auth.setLanguageCode("kr")


        prefs = PreferenceManager(this)

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:$credential")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                CoroutineScope(Dispatchers.IO).launch {
                    signupViewModel.phoneNumberCertificationState.emit(
                        PhoneNumberCertificationState(
                            false,
                            e.message.toString(),
                        )
                    )
                }
                Log.w(TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                    // reCAPTCHA verification attempted with null Activity
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                CoroutineScope(Dispatchers.IO).launch {
                    signupViewModel.phoneNumberCertificationState.emit(
                        PhoneNumberCertificationState(
                            true
                        )
                    )
                }
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:$verificationId")

                signupViewModel.verificationId(verificationId)

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token
            }
        }


        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = NavigationRoute.loginRoute
            ) {
                composable(NavigationRoute.loginRoute) {
                    LoginScreen(
                        onNavigateToSignup = { authViewModel.checkSupport() },
                        toMain = { nextActivity() },
                        prefs = prefs
                    )
                }
                composable(NavigationRoute.signupRoute) {
                    SignupScreen(
                        onNavigateToLogin = { navController.navigate(NavigationRoute.loginRoute) },
                        context = this@AuthActivity,
                        auth = auth,
                        callbacks = callbacks,
                        signupViewModel = signupViewModel,
                        authViewModel = authViewModel
                    )
                }
            }
            LaunchedEffect(authViewModel.checkSupportState) {
                authViewModel.checkSupportState.collect {
                    if(it.isSuccess)
                        navController.navigate(NavigationRoute.signupRoute)
                }
            }
        }
    }


    private fun nextActivity() {
        userViewModel.getUserDetailInformation(prefs.accessToken, prefs)
        lifecycleScope.launch {
            userViewModel.getUserDetailInformationState.collect {
                if (it.isSuccess) {
                    val mainIntent = Intent(this@AuthActivity, MainActivity::class.java)
                    startActivity(mainIntent)
                }
            }
        }
    }


    override fun attachBaseContext(newBase: Context) {
        val override = Configuration(newBase.resources.configuration)
        override.fontScale = 1.0f
        applyOverrideConfiguration(override)
        super.attachBaseContext(newBase)
    }

    override fun onDestroy() {
        if (!MainActivity.prefs.autoLogin) {
            MainActivity.prefs.deleteToken()
            MainActivity.prefs.deleteUserData()
        }
        super.onDestroy()
    }


}