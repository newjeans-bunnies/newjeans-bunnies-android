package newjeans.bunnies.main.presentation.post


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import newjeans.bunnies.main.presentation.post.ui.Post
import newjeans.bunnies.main.presentation.post.ui.PostAppBar
import newjeans.bunnies.main.viewmodel.PostViewModel


@Composable
fun PostScreen(viewModel: PostViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PostAppBar()
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            items(
                count = 2
            ){
                Post()
            }
        }
    }
}