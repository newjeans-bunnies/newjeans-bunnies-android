package newjeans.bunnies.main.presentation.post.ui

import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import newjeans.bunnies.designsystem.R


@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
fun PostAppBar() {
    val mainIconImage: Painter = painterResource(id = R.drawable.ic_main)
    val alarmIconImage: Painter = painterResource(id = R.drawable.ic_alarm)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Image(
            painter = mainIconImage,
            contentDescription = null,
            modifier = Modifier
                .height(60.dp)
                .padding(start = 30.dp, top = 13.dp, bottom = 13.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalAlignment = Alignment.End
        ) {
            IconButton(onClick = { /*TODO("알림창으로")*/ }, modifier = Modifier) {
                Icon(
                    painter = alarmIconImage,
                    contentDescription = null,
                    modifier = Modifier
                        .height(60.dp)
                        .padding(end = 30.dp, top = 15.dp, bottom = 15.dp)
                )
            }
        }
    }
}