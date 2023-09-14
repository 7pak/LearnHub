package com.example.courseapp.presentation.teacher.states.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ProfileStateModel:ViewModel() {

    var profileState = mutableStateOf(ProfileState())
    private set

    fun updateProfileState(newState: ProfileState){
        profileState.value = newState
    }
}