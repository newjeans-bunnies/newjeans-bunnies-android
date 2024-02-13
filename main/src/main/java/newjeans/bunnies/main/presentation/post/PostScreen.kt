package newjeans.bunnies.main.presentation.post


import android.util.Log

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

import newjeans.bunnies.data.PreferenceManager
import newjeans.bunnies.main.MainActivity
import newjeans.bunnies.main.data.UserData
import newjeans.bunnies.main.presentation.post.data.PostData
import newjeans.bunnies.main.presentation.post.ui.Post
import newjeans.bunnies.main.presentation.post.ui.PostAppBar
import newjeans.bunnies.main.viewmodel.PostViewModel
import newjeans.bunnies.network.post.dto.response.PostBasicInfoResponseDto


const val TAG = "PostScreen"

@Composable
fun PostScreen(
    postViewModel: PostViewModel = hiltViewModel(),
    content: MainActivity,
) {

    val prefs = PreferenceManager(content)

    val userData = UserData(
        userId = prefs.userId,
        userImage = prefs.userImage,
        userPhoneNumber = prefs.userPhoneNumber
    )

    Log.d(TAG, "userData: $userData")

    if(prefs.accessToken.isNotBlank())
        postViewModel.listPostDetail(prefs.accessToken, userData.userId)
    else
        postViewModel.listPostBasicInfo()


    LaunchedEffect(postViewModel.postInfoState) {
        postViewModel.postInfoState.collect {
            if (it.isSuccess) {

            }
            if (it.error.isNotEmpty()) {
                //TODO("게시글 못 불러옴")
            }
        }
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PostAppBar()
    }
}

@Composable
fun Posts(postViewModel: PostViewModel, userData: UserData, postData: List<PostBasicInfoResponseDto>?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        postData?.map {
            Post(
                postViewModel = postViewModel, postData = PostData(
                    uuid = it.uuid,
                    body = it.body,
                    createDate = it.createDate,
                    good = it.good,
                    goodStatus = null,
                    userId = userData.userId,
                ), userData = userData
            )
        }
    }
}

