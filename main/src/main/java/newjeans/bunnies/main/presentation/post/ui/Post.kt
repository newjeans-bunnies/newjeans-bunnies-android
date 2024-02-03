package newjeans.bunnies.main.presentation.post.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import coil.compose.rememberAsyncImagePainter
import newjeans.bunnies.designsystem.R

@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
fun Post() {
    val userId = "HamTory"
    val createDate = "02/02 - 4:26"

    val images = listOf("https://newjeans-bunnies-image.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%A2%E1%84%85%E1%85%B5%E1%86%AB.jpg")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .height(40.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.ic_post),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 10.dp))
            Column(
                modifier = Modifier
                    .height(30.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = userId,
                    style = TextStyle.userId,
                )
                Spacer(modifier = Modifier.weight(1F))
                Text(
                    text = createDate,
                    style = TextStyle.createDate
                )
            }

        }
        Image(images = images)
    }
}