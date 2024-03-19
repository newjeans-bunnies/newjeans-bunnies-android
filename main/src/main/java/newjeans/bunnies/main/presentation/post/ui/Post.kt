package newjeans.bunnies.main.presentation.post.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import newjeans.bunnies.main.Constant.postDatePattern
import newjeans.bunnies.designsystem.R
import newjeans.bunnies.main.data.PostData
import newjeans.bunnies.main.data.UserData


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Post(
    postData: PostData,
    userData: UserData
) {
    val postImage = postData.postImage
    val pagerState = rememberPagerState(pageCount = { postImage.size }, initialPage = 0)

    val icGoodImagePainter = painterResource(R.drawable.ic_good)
    val icCommentImagePainter = painterResource(R.drawable.ic_comment)
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = postData.userImage,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .height(30.dp)
                    .width(30.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.height(30.dp), horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = postData.userId,
//                    style = CustomTextStyle.userId,
                )
                Spacer(modifier = Modifier.weight(1F))
                Text(
                    text = postDatePattern(postData.postCreateDate),
//                    style = CustomTextStyle.createDate
                )
            }
        }
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 17.dp),
            state = pagerState
        ) { idx ->
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp)),
                model = postImage[idx],
                contentDescription = "",
                contentScale = ContentScale.Fit
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 11.dp, )
        ) {
            Row(
                modifier = Modifier
                    .height(30.dp)
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                Image(icGoodImagePainter, "")
                Spacer(modifier = Modifier.width(13.dp))
                Image(icCommentImagePainter, "")
            }
            Text(text = "좋아요 ${postData.goodCount}", modifier = Modifier.padding(bottom = 10.dp))
            Text(text = postData.postBody, modifier = Modifier.padding(bottom = 20.dp))
        }
    }
}