package newjeans.bunnies.auth.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


import newjeans.bunnies.designsystem.R
import newjeans.bunnies.designsystem.theme.AuthButtonColor
import newjeans.bunnies.designsystem.theme.authText


@Composable
fun LoginScreen(
    onNavigateToSignup: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppIconImage()
        SignupButton(onNavigateToSignup)
    }
}

@Composable
fun AppIconImage() {
    val image: Painter = painterResource(id = R.drawable.main_image)
    Image(
        painter = image,
        contentDescription = "",
        modifier = Modifier.padding(
            start = 100.dp,
            end = 100.dp,
            top = 80.dp
        )
    )
}

@Composable
fun SignupButton(onNavigateToSignup: () -> Unit) {
    Button(
        onClick = onNavigateToSignup,
        colors = ButtonDefaults.buttonColors(AuthButtonColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
            .background(color = AuthButtonColor, shape = RoundedCornerShape(size = 13.dp))

    ) {
        Text(
            text = "로그인",
            style = authText.titleMedium
        )
    }
}