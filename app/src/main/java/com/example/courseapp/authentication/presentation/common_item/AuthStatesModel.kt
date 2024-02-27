package com.example.courseapp.authentication.presentation.common_item

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class AuthStatesModel : ViewModel() {

     var authState = mutableStateOf(AuthState())
        private set

    fun updateAuthState(newState: AuthState) {
        authState.value = newState
    }
}