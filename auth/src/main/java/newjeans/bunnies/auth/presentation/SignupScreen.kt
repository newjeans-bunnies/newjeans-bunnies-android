package newjeans.bunnies.auth.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import newjeans.bunnies.auth.presentation.ui.image.ArrowBackImage
import newjeans.bunnies.auth.viewmodel.SignupViewModel
import newjeans.bunnies.designsystem.R
import newjeans.bunnies.designsystem.theme.authText
import org.w3c.dom.Text

@Composable
fun SignupScreen(
    signupViewModel: SignupViewModel,
    onNavigateToLogin: () -> Unit
) {
    Row {
        SignupAppBar()
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
fun SignupAppBar(

) {
    Box(
        modifier = Modifier
            .height(60.dp)
            .fillMaxSize(),
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier
                .padding(start = 30.dp)
                .height(60.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_arrow_back), contentDescription = "")
        }
        Text(
            modifier = Modifier.fillMaxSize(),
            text = "회원가입",
            textAlign = TextAlign.Center,
            style = authText.headlineLarge
        )
    }
}