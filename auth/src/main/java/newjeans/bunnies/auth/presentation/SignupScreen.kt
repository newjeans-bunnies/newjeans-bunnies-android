package newjeans.bunnies.auth.presentation


import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp

import newjeans.bunnies.auth.viewmodel.SignupViewModel
import newjeans.bunnies.designsystem.R
import newjeans.bunnies.designsystem.theme.authText


@Composable
fun SignupScreen(
    signupViewModel: SignupViewModel,
    onNavigateToLogin: () -> Unit
) {
    Row {
        SignupAppBar(onNavigateToLogin)
        Spacer(modifier = Modifier.height(50.dp))
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
                .padding(start = 30.dp)
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