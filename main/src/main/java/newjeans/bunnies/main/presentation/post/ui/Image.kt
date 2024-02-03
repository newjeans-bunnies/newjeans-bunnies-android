package newjeans.bunnies.main.presentation.post.ui


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Image(images: List<String>) {
    val pagerState = rememberPagerState(pageCount = { images.size }, initialPage = 0)

    HorizontalPager(
        modifier = Modifier
            .padding(bottom = 20.dp),
        state = pagerState,
        ) { idx ->
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp)),
            model = ImageRequest.Builder(LocalContext.current).data(images[idx])
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }

}