package newjeans.bunnies.main.viewmodel


import androidx.lifecycle.ViewModel

import dagger.hilt.android.lifecycle.HiltViewModel

import newjeans.bunnies.network.post.PostRepository

import javax.inject.Inject


@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository
): ViewModel(){

}