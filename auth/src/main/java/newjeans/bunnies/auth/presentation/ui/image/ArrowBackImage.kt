package newjeans.bunnies.auth.presentation.ui.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import newjeans.bunnies.designsystem.R

@Composable
fun ArrowBackImage() {
    val image = painterResource(id = R.drawable.ic_arrow_back)
    Image(
        painter = image, contentDescription = ""
    )
}