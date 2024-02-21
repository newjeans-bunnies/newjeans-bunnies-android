package newjeans.bunnies.main.presentation.post


import android.util.Log

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

import newjeans.bunnies.data.PreferenceManager
import newjeans.bunnies.main.MainActivity
import newjeans.bunnies.main.data.UserData
import newjeans.bunnies.main.presentation.post.data.PostData
import newjeans.bunnies.main.presentation.post.ui.Post
import newjeans.bunnies.main.presentation.post.ui.PostAppBar
import newjeans.bunnies.main.viewmodel.PostViewModel


const val TAG = "PostScreen"

@Composable
fun PostScreen(
    postViewModel: PostViewModel = hiltViewModel(),
    content: MainActivity
) {

    val prefs = PreferenceManager(content)
    var postData by remember { mutableStateOf(listOf<PostData>()) }

    val userData = UserData(
        userId = prefs.userId,
        userImage = prefs.userImage,
        userPhoneNumber = prefs.userPhoneNumber
    )

    Log.d(TAG, "userData: $userData")


    if (prefs.accessToken.isNotBlank())
        postViewModel.listPostDetail(userData.userId, prefs)
    else
        postViewModel.listPostBasicInfo()


    LaunchedEffect(postViewModel.postInfoState) {
        postViewModel.postInfoState.collect {
            if (it.isSuccess) {
                postData = postViewModel.postData.value ?: listOf()
                Log.d(TAG, postViewModel.postData.value.toString())
                Log.d(TAG, "마지막 시간" + postViewModel.postData.value?.last()?.createDate)
            }
            if(it.error.isNotEmpty())
                content.finish()
        }
    }

    LaunchedEffect(postViewModel.reissueTokenState){
        postViewModel.reissueTokenState.collect {
            if(it.error.isNotEmpty()){
                content.finish()
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PostAppBar()
        LazyColumn {
            items(postData.size) { index ->
                Post(postData[index], postViewModel, userData)
            }
        }
    }
}

@Composable
fun Posts(postViewModel: PostViewModel, userData: UserData, postData: List<PostData>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        postData.map {
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

