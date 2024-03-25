package newjeans.bunnies.main.presentation.post.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import newjeans.bunnies.main.Constant.postDatePattern
import newjeans.bunnies.designsystem.R
import newjeans.bunnies.designsystem.theme.CustomTextStyle
import newjeans.bunnies.designsystem.theme.CustomTextStyle.Title5
import newjeans.bunnies.designsystem.ui.CustomCheckbox
import newjeans.bunnies.main.MainActivity
import newjeans.bunnies.main.data.PostData
import newjeans.bunnies.main.viewmodel.PostViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Post(
    postData: PostData,
    viewModel: PostViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as MainActivity
    val postImage = postData.postImage
    val pagerState = rememberPagerState(pageCount = { postImage.size }, initialPage = 0)
    var postGoodCounts by remember { mutableLongStateOf(postData.goodCounts) }
    var goodStatus by remember { mutableStateOf(postData.goodStatus ?: false) }

    val icCommentImagePainter = painterResource(R.drawable.ic_comment)

    LaunchedEffect(viewModel.postGoodState) {
        viewModel.postGoodState.collect {
            if (it.isSuccess) {
                goodStatus = it.goodStatus ?: false
                postGoodCounts = it.goodCounts
            } else if (it.error == "HTTP 401 ") {

            } else {
                activity.finish()
            }
        }
    }
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
                    style = CustomTextStyle.Title9,
                )
                Spacer(modifier = Modifier.weight(1F))
                Text(
                    text = postDatePattern(postData.postCreateDate),
                    style = CustomTextStyle.Title10
                )
            }
        }
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth(),
            state = pagerState
        ) { idx ->
            AsyncImage(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxWidth(),
                model = postImage[idx],
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(30.dp)
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                CustomCheckbox(
                    checked = goodStatus,
                    onPainter = painterResource(id = R.drawable.ic_good_true),
                    offPainter = painterResource(id = R.drawable.ic_good_false),
                ) {
//                    viewModel.postGood(
//                        postData.postId,
//                    )
                }
                Spacer(modifier = Modifier.width(13.dp))
                Image(icCommentImagePainter, "")
            }
            Text(
                text = "좋아요 $postGoodCounts",
                modifier = Modifier.padding(bottom = 10.dp),
                style = Title5
            )
            Text(text = postData.postBody, modifier = Modifier.padding(bottom = 20.dp))
        }
    }
}