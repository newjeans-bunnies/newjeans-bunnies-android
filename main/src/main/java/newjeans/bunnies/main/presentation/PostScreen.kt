package newjeans.bunnies.main.presentation


import android.util.Log
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material.Divider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import newjeans.bunnies.designsystem.theme.CustomColor

import newjeans.bunnies.main.MainActivity.Companion.prefs
import newjeans.bunnies.main.data.PostData
import newjeans.bunnies.main.data.UserData
import newjeans.bunnies.main.presentation.post.ui.Post
import newjeans.bunnies.main.viewmodel.PostViewModel


private const val TAG = "PostScreen"

@Composable
fun PostScreen(
    postViewModel: PostViewModel = hiltViewModel(),
    finish: () -> Unit,
) {
    var postData by remember { mutableStateOf(listOf<PostData>()) }

    val userData = UserData(
        userId = prefs.userId, userImage = prefs.userImage, userPhoneNumber = prefs.userPhoneNumber
    )

    if (prefs.accessToken.isNotBlank()) postViewModel.listPostDetail(userData.userId, prefs)
    else postViewModel.listPostBasicInfo()


    LaunchedEffect(postViewModel.postInfoState) {
        postViewModel.postInfoState.collect {
            if (it.isSuccess) {
                postData = postViewModel.postData.value ?: listOf()
                if (postData.isNotEmpty()) {
                    Log.d(TAG, postData.toString())
                }
            }
            if (it.error.isNotEmpty()) finish()
        }
    }

    LaunchedEffect(postViewModel.reissueTokenState) {
        postViewModel.reissueTokenState.collect {
            if (it.error.isNotEmpty()) finish()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
        ) {
            items(postData) {
                Column {
                    Post(postData = it, userData = userData)
                    Divider(color = CustomColor.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 20.dp))
                }
            }
        }
    }
}