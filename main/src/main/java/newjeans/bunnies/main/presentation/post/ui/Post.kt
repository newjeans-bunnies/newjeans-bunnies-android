package newjeans.bunnies.main.presentation.post.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import newjeans.bunnies.main.data.UserData

import newjeans.bunnies.main.presentation.post.data.PostData
import newjeans.bunnies.main.viewmodel.PostViewModel

@Composable
fun Post(postData: PostData, postViewModel: PostViewModel, userData: UserData) {
//    postViewModel.getPostImage(postData.uuid)
//    val postImage by postViewModel.postImage.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    ) {
        Row(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = userData.userImage,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 10.dp))
            Column(
                modifier = Modifier
                    .height(30.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = postData.userId,
                    style = TextStyle.userId,
                )
                Spacer(modifier = Modifier.weight(1F))
                Text(
                    text = postData.createDate,
                    style = TextStyle.createDate
                )
            }
        }
//        Image(images = postImage?: listOf())
    }
}