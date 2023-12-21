package newjeans.bunnies.auth.presentation.ui.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import newjeans.bunnies.designsystem.R

@Composable
fun PasswordIconImage() {
    val image = painterResource(id = R.drawable.ic_password)
    Image(
        painter = image, contentDescription = ""
    )
}