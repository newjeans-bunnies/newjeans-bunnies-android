package newjeans.bunnies.main.presentation


import android.util.Log

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.items

import newjeans.bunnies.main.MainActivity.Companion.prefs
import newjeans.bunnies.main.data.UserData
import newjeans.bunnies.main.presentation.post.data.PostData
import newjeans.bunnies.main.presentation.post.ui.Post
import newjeans.bunnies.main.viewmodel.PostViewModel


private const val TAG = "PostScreen"

@Composable
fun PostScreen(
    postViewModel: PostViewModel = hiltViewModel(),
    finish: () -> Unit,
) {
    Log.d(TAG, "PostScreen")
    var postData by remember { mutableStateOf(listOf<PostData>()) }

    val userData = UserData(
        userId = prefs.userId, userImage = prefs.userImage, userPhoneNumber = prefs.userPhoneNumber
    )

    Log.d(TAG, "userData: $userData")

    if (prefs.accessToken.isNotBlank()) postViewModel.listPostDetail(userData.userId, prefs)
    else postViewModel.listPostBasicInfo()


    LaunchedEffect(postViewModel.postInfoState) {
        postViewModel.postInfoState.collect {
            if (it.isSuccess) {
                postData = postViewModel.postData.value ?: listOf()
                Log.d(TAG, postData.toString())
                Log.d(TAG, "마지막 시간" + postViewModel.postData.value?.last()?.createDate)
                Log.d(TAG, "첫번째 시간" + postViewModel.postData.value?.first()?.createDate)
            }
            if (it.error.isNotEmpty()) finish()
        }
    }

    LaunchedEffect(postViewModel.reissueTokenState) {
        postViewModel.reissueTokenState.collect {
            if (it.error.isNotEmpty()) finish()
            if (it.isSuccess) postViewModel.listPostDetail(userData.userId, prefs)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(postData) {
                Post(postData = it, postViewModel = postViewModel, userData = userData)
            }
        }
    }
}