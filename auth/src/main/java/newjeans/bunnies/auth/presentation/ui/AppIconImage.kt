package newjeans.bunnies.auth.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import newjeans.bunnies.designsystem.R

@Composable
fun AppIconImage() {
    val image: Painter = painterResource(id = R.drawable.main_image)
    Image(
        painter = image, contentDescription = "", modifier = Modifier.padding(
            start = 110.dp, end = 110.dp, top = 60.dp, bottom = 60.dp
        )
    )
}