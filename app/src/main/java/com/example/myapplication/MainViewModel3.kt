package com.example.myapplication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.theme.Post
import com.example.myapplication.ui.theme.ProfileState
import com.example.myapplication.ui.theme.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.flow.merge

/*
    With 'combine' we can join different flows in one, so when one of them is triggered,
    the block inside gets executed.
    'Combine' will be called when one of the states changes
    'Zip' will wait until both emissions (emit) are triggered
    'Merge' merges several flows in one flow. The types of the flows has to be the same
*/
class MainViewModel3 : ViewModel() {

    private val isAuthenticated = MutableStateFlow(true)
    private val user = MutableStateFlow<User?>(null)
    private val posts = MutableStateFlow(emptyList<Post>())

    private val _profileState = MutableStateFlow<ProfileState?>(null)
    val profileState = _profileState.asStateFlow()

    private val flow1 = (1..10).asFlow().onEach { delay(1000) }
    private val flow2 = (10..20).asFlow().onEach { delay(300) }

    var numberString by mutableStateOf("")

    init {
        isAuthenticated.combine(user) { isAuthenticated, user ->
            if(isAuthenticated) user else null
        }.combine(posts) { user, posts ->
            user?.let {
                _profileState.value = profileState.value?.copy(
                    profilePicUrl = user?.profilePicUrl,
                    username = user?.username,
                    description = user?.description,
                    posts = posts
                )
            }
        }
            .launchIn(viewModelScope)  // This last function is a collector

        flow1.zip(flow2) { num1, num2 ->
            numberString += "($num1, $num2)\n"
        }.launchIn(viewModelScope)

        merge(flow1, flow2).onEach {
            numberString += "$it\n"
        }.launchIn(viewModelScope)

    }

}


